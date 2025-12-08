package io.vertigo.vortex.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXNotebook;
import io.vertigo.vortex.gold.library.VXLibrary;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.types.VXDomainType;
import io.vertigo.vortex.gold.module.VXAttribute;
import io.vertigo.vortex.gold.module.VXCardinality;
import io.vertigo.vortex.gold.module.VXEntity;
import io.vertigo.vortex.gold.module.VXLink;
import io.vertigo.vortex.gold.module.VXModule;
import io.vertigo.vortex.gold.module.VXRole;
import io.vertigo.vortex.silver.RawNotebook;
import io.vertigo.vortex.silver.library.RawDomainType;
import io.vertigo.vortex.silver.library.RawLibrary;
import io.vertigo.vortex.silver.module.RawAttribute;
import io.vertigo.vortex.silver.module.RawEntity;
import io.vertigo.vortex.silver.module.RawLink;
import io.vertigo.vortex.silver.module.RawModule;

/**
 * Transforms a raw model into a VXModel.
 * This class is responsible for converting the raw data read from the JSON file
 * into the final, structured VXModel.
 * It builds a catalog of domain types and entities.
 * @synthetic
 */
public final class Silver {
	private final RawNotebook rawNotebook;
	private final Map<String, VXDomainType> domainTypeCatalog = new HashMap<>();
	private final Map<String, VXEntity> entityCatalog = new HashMap<>();

	Silver(final RawNotebook rawNotebook) {
		Assertion.check().isNotNull(rawNotebook);
		//---
		this.rawNotebook = rawNotebook;
	}

	/**
	 * Transforms the raw model into a VXNotebook.
	 */
	public VXNotebook toGold() {
		for (final RawLibrary rawLibrarySource : rawNotebook.libraries()) {
			for (final RawDomainType rawDomainType : rawLibrarySource.domainTypes()) {
				final var domainType = transform(rawDomainType);
				domainTypeCatalog.put(domainType.name(), domainType);
			}
		}

		for (final RawModule rawModuleSource : rawNotebook.rawModules()) {
			for (final RawEntity rawEntity : rawModuleSource.entities()) {
				final var name = "do-" + rawEntity.name();
				domainTypeCatalog.put(name, new VXDomainType(name, rawEntity.description(), VXDataType.Entity, List.of(), List.of()));
			}

			for (final RawEntity rawEntity : rawModuleSource.entities()) {
				final var entity = transform(rawEntity);
				entityCatalog.put(entity.name(), entity);
			}
		}
		final var rawModules = rawNotebook.rawModules();
		final var rawLibraries = rawNotebook.libraries();

		// we merge all modules into one.
		// we merge all libraries into one.
		final VXLibrary library = new VXLibrary(
				!rawLibraries.isEmpty()
						? rawLibraries.get(0).library()
						: "library",
				!rawLibraries.isEmpty()
						? rawLibraries.get(0).description()
						: "Default Library",
				new ArrayList(domainTypeCatalog.values()));

		final List<String> imports = rawModules.stream()
				.filter(rawModule -> rawModule.imports() != null)
				.flatMap(rawModule -> rawModule.imports().stream())
				.distinct()
				.collect(Collectors.toList());

		final VXModule module = new VXModule(
				!rawModules.isEmpty() ? rawModules.get(0).module() : "module",
				!rawModules.isEmpty() ? rawModules.get(0).description() : "Default Module",
				imports,
				new ArrayList(entityCatalog.values()));

		return new VXNotebook(List.of(module), List.of(library));
	}

	private VXAttribute transform(final RawAttribute rawAttribute) {
		//default values
		final VXRole role = rawAttribute.role() == null
				? VXRole.DATA
				: VXRole.valueOf(rawAttribute.role().toUpperCase());

		return new VXAttribute(
				rawAttribute.name(),
				rawAttribute.description() != null
						? rawAttribute.description()
						: rawAttribute.name(),
				domainTypeCatalog.get(rawAttribute.domainType()),
				role,
				VXCardinality.fromSymbol(rawAttribute.cardinality()));
	}

	private VXLink transform(final RawLink rawLink) {
		final VXEntity entity = entityCatalog.get(rawLink.targetEntityName());
		Assertion.check().isNotNull(entity, "no entity found for " + rawLink.targetEntityName());
		return new VXLink(
				rawLink.name(),
				rawLink.description() != null
						? rawLink.description()
						: rawLink.name(),
				entity,
				VXCardinality.fromSymbol(rawLink.cardinality()));
	}

	private VXEntity transform(final RawEntity rawEntity) {
		final List<VXAttribute> attributes = rawEntity.attributes() != null
				? rawEntity.attributes()
						.stream()
						.map(rawAttribute -> transform(rawAttribute))
						.collect(Collectors.toList())
				: List.of();

		final List<VXLink> links = rawEntity.links() != null
				? rawEntity.links()
						.stream()
						.map(rawLink -> transform(rawLink))
						.collect(Collectors.toList())
				: List.of();

		return new VXEntity(
				rawEntity.name(),
				rawEntity.description(),
				attributes,
				links);
	}

	private static VXDomainType transform(final RawDomainType rawDomainType) {
		return new VXDomainType(
				rawDomainType.name(),
				rawDomainType.description() != null
						? rawDomainType.description()
						: rawDomainType.name(),
				VXDataType.valueOf(rawDomainType.dataType()),
				List.of(),
				List.of());
	}

}
