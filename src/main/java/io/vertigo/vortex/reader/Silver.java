package io.vertigo.vortex.reader;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXElementType;
import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.VXNotebook;
import io.vertigo.vortex.gold.library.VXLibrary;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.types.VXDomainType;
import io.vertigo.vortex.gold.module.VXAttribute;
import io.vertigo.vortex.gold.module.VXCardinality;
import io.vertigo.vortex.gold.module.VXEntity;
import io.vertigo.vortex.gold.module.VXId;
import io.vertigo.vortex.gold.module.VXLink;
import io.vertigo.vortex.gold.module.VXModule;
import io.vertigo.vortex.gold.module.VXUses;
import io.vertigo.vortex.silver.RawNotebook;
import io.vertigo.vortex.silver.library.RawDomainType;
import io.vertigo.vortex.silver.library.RawLibrary;
import io.vertigo.vortex.silver.module.RawAttribute;
import io.vertigo.vortex.silver.module.RawEntity;
import io.vertigo.vortex.silver.module.RawId;
import io.vertigo.vortex.silver.module.RawLink;
import io.vertigo.vortex.silver.module.RawModule;
import io.vertigo.vortex.silver.module.RawUses;

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

	private static VXKey createKeyForModule(String name) {
		return new VXKey(null, VXElementType.MODULE, name);
	}

	private static VXKey createKeyForLibrary(String name) {
		return new VXKey(null, VXElementType.LIBRARY, name);
	}

	private static VXKey createKeyForDomainType(String name) {
		//!!!!!!
		//!!!!!! TODO
		//!!!!!!
		//!!!!!!
		final VXKey libraryKey = new VXKey(null, VXElementType.LIBRARY, "core");
		return new VXKey(libraryKey, VXElementType.DOMAIN_TYPE, name);
	}

	private static VXKey createKeyForEntity(VXKey owner, String name) {
		//si name ne contient pas de "." alors on prends l'owner ( on reste dans le même module) 
		//Sinon on cherche me module
		int i = name.indexOf(".");
		VXKey _owner;
		if (i < 0) {
			_owner = owner;
		} else {
			//on recherche le module qui correspond 
			//TODO
			//TODO
			//TODO
			_owner = owner;
		}

		return new VXKey(_owner, VXElementType.ENTITY, name);
	}

	/**
	 * Transforms the raw model into a VXNotebook.
	 */
	public VXNotebook toGold() {
		final List<VXLibrary> libraries = rawNotebook.rawLibraries().stream()
				.map(lib -> transform(lib))
				.peek(lib -> libraryCatalog.put(lib.key(), lib))
				.toList();

		final List<VXModule> modules = rawNotebook.rawModules().stream()
				.map(mod -> transform(mod))
				.peek(mod -> moduleCatalog.put(mod.key(), mod))
				.toList();

		return new VXNotebook(libraries, modules);
	}

	private VXUses transform(final RawUses uses) {
		final List<VXLibrary> libraries = uses.libraries() == null
				? List.of()
				: uses.libraries().stream()
						.map(Silver::createKeyForLibrary)
						.map(libraryCatalog::get)
						.toList();

		final List<VXModule> modules = uses.modules() == null
				? List.of()
				: uses.modules().stream()
						.map(Silver::createKeyForModule)
						.map(moduleCatalog::get)
						.toList();
		return new VXUses(libraries, modules);
	}

	private VXModule transform(final RawModule rawModule) {
		final VXKey moduleUKey = createKeyForModule(rawModule.module());

		final VXUses uses = transform(rawModule.uses());

		final Catalog<VXDomainType> domainTypeCatalog = new Catalog<>();
		final Catalog<VXEntity> entityCatalog = new Catalog<>();
		//---
		for (var library : uses.libraries()) {
			for (var dt : library.domainTypes()) {
				domainTypeCatalog.put(dt.key(), dt);
			}
		}
		for (var module : uses.modules()) {
			for (var entity : module.entities()) {
				entityCatalog.put(entity.key(), entity);
			}
		}

		final List<VXEntity> entities = rawModule.entities().stream()
				.map(e -> transform(e, moduleUKey, domainTypeCatalog, entityCatalog))
				.toList();
		return new VXModule(
				moduleUKey,
				rawModule.description() != null
						? rawModule.description()
						: rawModule.module(),
				uses,
				entities);
	}

	private static VXEntity transform(
			final RawEntity rawEntity,
			final VXKey owner, //the module UKey
			Catalog<VXDomainType> domainTypeCatalog,
			Catalog<VXEntity> entityCatalog) {
		final VXKey entityKey = createKeyForEntity(owner, rawEntity.name());
		final List<VXAttribute> attributes = rawEntity.attributes() != null
				? rawEntity.attributes()
						.stream()
						.map(rawAttribute -> transform(rawAttribute, entityKey, domainTypeCatalog))
						.toList()
				: List.of();

		final List<VXLink> links = rawEntity.links() != null
				? rawEntity.links()
						.stream()
						.map(rawLink -> transform(rawLink, entityKey, owner, entityCatalog))
						.toList()
				: List.of();

		return new VXEntity(
				entityKey,
				rawEntity.description(),
				transform(rawEntity.id(), domainTypeCatalog),
				attributes,
				links);
	}

	private static VXId transform(RawId id,
			final Catalog<VXDomainType> domainTypeCatalog) {
		return new VXId(
				id.name(),
				id.description(),
				domainTypeCatalog.get(createKeyForDomainType(id.domainType())));
	}

	private static VXLibrary transform(final RawLibrary rawLibrary) {
		final VXKey libraryKey = createKeyForLibrary(rawLibrary.library());
		final List<VXDomainType> domainTypes = rawLibrary.domainTypes().stream()
				.map(dt -> transform(dt, libraryKey))
				.toList();

		return new VXLibrary(
				libraryKey,
				rawLibrary.description() != null
						? rawLibrary.description()
						: rawLibrary.library(),
				domainTypes);
	}

	private static VXAttribute transform(
			final RawAttribute rawAttribute,
			final VXKey entityKey,
			final Catalog<VXDomainType> domainTypeCatalog) {
		final VXKey attributeKey = new VXKey(entityKey, VXElementType.ATTRIBUTE, rawAttribute.name());
		return new VXAttribute(
				attributeKey,
				rawAttribute.description() != null
						? rawAttribute.description()
						: rawAttribute.name(),
				domainTypeCatalog.get(createKeyForDomainType(rawAttribute.domainType())),
				VXCardinality.fromSymbol(rawAttribute.cardinality()));
	}

	private static VXLink transform(
			final RawLink rawLink,
			final VXKey entityKey,
			final VXKey owner,
			final Catalog<VXEntity> entityCatalog) {
		final VXKey linkKey = new VXKey(entityKey, VXElementType.LINK, rawLink.name());
		return new VXLink(
				linkKey,
				rawLink.description() != null
						? rawLink.description()
						: rawLink.name(),
				createKeyForEntity(owner, rawLink.targetEntityName()),
				VXCardinality.fromSymbol(rawLink.cardinality()));
	}

	private static VXDomainType transform(
			final RawDomainType rawDomainType,
			final VXKey libraryKey) {
		final VXKey domainTypeKey = new VXKey(libraryKey, VXElementType.DOMAIN_TYPE, rawDomainType.name());
		return new VXDomainType(
				domainTypeKey,
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
