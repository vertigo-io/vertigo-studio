package io.vertigo.vortex.reader;

import java.util.List;

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

	Silver(final RawNotebook rawNotebook) {
		Assertion.check().isNotNull(rawNotebook);
		//---
		this.rawNotebook = rawNotebook;
	}

	/**
	 * Transforms the raw model into a VXNotebook.
	 */
	public VXNotebook toGold() {
		final List<VXLibrary> libraries = rawNotebook.libraries().stream()
				.map(lib -> transform(lib))
				.toList();

		final List<VXModule> modules = rawNotebook.rawModules().stream()
				.map(mod -> transform(mod))
				.toList();
		//	
		//		for (final RawEntity rawEntity : rawModuleSource.entities()) {
		//			final var name = "do-" + rawEntity.name();
		//			domainTypeCatalog.put(name, new VXDomainType(name, rawEntity.description(), VXDataType.Entity, List.of(), List.of()));
		//		}
		//
		//		for (final RawEntity rawEntity : rawModuleSource.entities()) {
		//			final var entity = transform(rawEntity);
		//			entityCatalog.put(entity.name(), entity);
		//		}
		//	}
		//
		//	final var rawModules = rawNotebook.rawModules();
		//	final var rawLibraries = rawNotebook.libraries();
		//
		//	// we merge all modules into one.
		//	// we merge all libraries into one.
		//
		//	libraryCatalog.put(library.name(),library);
		//
		//	final List<String> uses = rawModules.stream()
		//			.filter(rawModule -> rawModule.uses() != null)
		//			.flatMap(rawModule -> rawModule.uses().stream())
		//			.distinct()
		//			.collect(Collectors.toList());
		//
		//	final List<String> imports = rawModules.stream()
		//			.filter(rawModule -> rawModule.imports() != null)
		//			.flatMap(rawModule -> rawModule.imports().stream())
		//			.distinct()
		//			.collect(Collectors.toList());
		//
		//	final VXModule module = new VXModule(
		//			!rawModules.isEmpty() ? rawModules.get(0).module() : "module",
		//			!rawModules.isEmpty() ? rawModules.get(0).description() : "Default Module",
		//			uses,
		//			imports,
		//			new ArrayList(entityCatalog.values()));moduleCatalog.put(module.name(),module);
		//
		return new VXNotebook(libraries, modules);
	}

	private static VXEntity transform(final RawEntity rawEntity) {
		final List<VXAttribute> attributes = rawEntity.attributes() != null
				? rawEntity.attributes()
						.stream()
						.map(rawAttribute -> transform(rawAttribute))
						.toList()
				: List.of();

		final List<VXLink> links = rawEntity.links() != null
				? rawEntity.links()
						.stream()
						.map(rawLink -> transform(rawLink))
						.toList()
				: List.of();

		return new VXEntity(
				rawEntity.name(),
				rawEntity.description(),
				attributes,
				links);
	}

	private static VXModule transform(final RawModule rawModule) {
		final List<VXEntity> entities = rawModule.entities().stream()
				.map(e -> transform(e))
				.toList();
		return new VXModule(
				rawModule.name(),
				rawModule.description() != null
						? rawModule.description()
						: rawModule.name(),
				List.of(),
				List.of(),
				entities);
	}

	private static VXLibrary transform(final RawLibrary rawLibrary) {
		final List<VXDomainType> domainTypes = rawLibrary.domainTypes().stream()
				.map(dt -> transform(dt))
				.toList();

		return new VXLibrary(
				rawLibrary.name(),
				rawLibrary.description() != null
						? rawLibrary.description()
						: rawLibrary.name(),
				domainTypes);
	}

	private static VXAttribute transform(final RawAttribute rawAttribute) {
		//default values
		final VXRole role = rawAttribute.role() == null
				? VXRole.DATA
				: VXRole.valueOf(rawAttribute.role().toUpperCase());

		return new VXAttribute(
				rawAttribute.name(),
				rawAttribute.description() != null
						? rawAttribute.description()
						: rawAttribute.name(),
				//!!!!!!!!!!!!!!!!!!!!!!!!!!
				null, //				domainTypeCatalog.get(rawAttribute.domainType()),
				role,
				VXCardinality.fromSymbol(rawAttribute.cardinality()));
	}

	private static VXLink transform(final RawLink rawLink) {
		//!!!!!!!!!!!!!!!!
		final VXEntity entity = null; //entityCatalog.get(rawLink.targetEntityName());
		Assertion.check().isNotNull(entity, "no entity found for " + rawLink.targetEntityName());
		return new VXLink(
				rawLink.name(),
				rawLink.description() != null
						? rawLink.description()
						: rawLink.name(),
				entity,
				VXCardinality.fromSymbol(rawLink.cardinality()));
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
