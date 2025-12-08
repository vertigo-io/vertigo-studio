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
	private final Catalog<VXLibrary> libraryCatalog = new Catalog<>();
	private final Catalog<VXModule> moduleCatalog = new Catalog<>();

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
				.peek(lib -> libraryCatalog.put(lib.name(), lib))
				.toList();

		final List<VXModule> modules = rawNotebook.rawModules().stream()
				.map(mod -> transform(mod))
				.peek(mod -> moduleCatalog.put(mod.name(), mod))
				.toList();

		return new VXNotebook(libraries, modules);
	}

	private VXModule transform(final RawModule rawModule) {
		final Catalog<VXDomainType> domainTypeCatalog = new Catalog<>();
		final Catalog<VXEntity> entityCatalog = new Catalog<>();

		//---
		if (rawModule.uses().libraries() != null) {
			for (String lib : rawModule.uses().libraries()) {
				VXLibrary library = libraryCatalog.get(lib);
				library.domainTypes()
						.forEach(dt -> domainTypeCatalog.put(dt.name(), dt));
			}
		}
		if (rawModule.uses().modules() != null) {
			for (String lib : rawModule.uses().modules()) {
				VXModule module = moduleCatalog.get(lib);
				module.entities()
						.forEach(e -> entityCatalog.put(e.name(), e));
			}
		}

		final List<VXEntity> entities = rawModule.entities().stream()
				.map(e -> transform(e, domainTypeCatalog, entityCatalog))
				.toList();
		return new VXModule(
				rawModule.module(),
				rawModule.description() != null
						? rawModule.description()
						: rawModule.module(),
				List.of(),
				List.of(),
				entities);
	}

	private static VXEntity transform(
			final RawEntity rawEntity,
			Catalog<VXDomainType> domainTypeCatalog,
			Catalog<VXEntity> entityCatalog) {
		final List<VXAttribute> attributes = rawEntity.attributes() != null
				? rawEntity.attributes()
						.stream()
						.map(rawAttribute -> transform(rawAttribute, domainTypeCatalog))
						.toList()
				: List.of();

		final List<VXLink> links = rawEntity.links() != null
				? rawEntity.links()
						.stream()
						.map(rawLink -> transform(rawLink, entityCatalog))
						.toList()
				: List.of();

		return new VXEntity(
				rawEntity.name(),
				rawEntity.description(),
				attributes,
				links);
	}

	private static VXLibrary transform(final RawLibrary rawLibrary) {
		final List<VXDomainType> domainTypes = rawLibrary.domainTypes().stream()
				.map(dt -> transform(dt))
				.toList();

		return new VXLibrary(
				rawLibrary.library(),
				rawLibrary.description() != null
						? rawLibrary.description()
						: rawLibrary.library(),
				domainTypes);
	}

	private static VXAttribute transform(
			final RawAttribute rawAttribute,
			final Catalog<VXDomainType> domainTypeCatalog) {
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

	private static VXLink transform(
			final RawLink rawLink,
			final Catalog<VXEntity> entityCatalog) {
		return new VXLink(
				rawLink.name(),
				rawLink.description() != null
						? rawLink.description()
						: rawLink.name(),
				entityCatalog.get(rawLink.targetEntityName()),
				VXCardinality.fromSymbol(rawLink.cardinality()));
	}

	private static VXDomainType transform(final RawDomainType rawDomainType) {
		return new VXDomainType(
				rawDomainType.name(),
				rawDomainType.description() != null
						? rawDomainType.description()
						: rawDomainType.name(),
				VXDataType.valueOf(rawDomainType.dataType()),
				//!!!!!!!!!!!!!!!!!!!!!!!
				List.of(),
				//!!!!!!!!!!!!!!!!!!!!!!!
				List.of());
	}
}
