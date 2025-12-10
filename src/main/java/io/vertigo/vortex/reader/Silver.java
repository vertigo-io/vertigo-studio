package io.vertigo.vortex.reader;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXNotebook;
import io.vertigo.vortex.gold.VXUID;
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
	private final Catalog<String, VXLibrary> libraryCatalog = new Catalog<>();
	private final Catalog<VXUID, VXModule> moduleCatalog = new Catalog<>();

	Silver(final RawNotebook rawNotebook) {
		Assertion.check().isNotNull(rawNotebook);
		//---
		this.rawNotebook = rawNotebook;
	}

	private static VXUID createKeyForModule(String name) {
		return new VXUID(null, "module", name);
	}

	private static VXUID createKeyForEntity(VXUID owner, String name) {
		//si name ne contient pas de "." alors on prends l'owner ( on reste dans le même module) 
		//Sinon on cherche me module
		int i = name.indexOf(".");
		VXUID _owner;
		if (i < 0) {
			_owner = owner;
		} else {
			//on recherche le module qui correspond 
			//TODO
			//TODO
			//TODO
			//TODO
			_owner = owner;
		}

		return new VXUID(_owner, "entity", name);
	}

	/**
	 * Transforms the raw model into a VXNotebook.
	 */
	public VXNotebook toGold() {
		final List<VXLibrary> libraries = rawNotebook.rawLibraries().stream()
				.map(lib -> transform(lib))
				.peek(lib -> libraryCatalog.put(lib.name(), lib))
				.toList();

		final List<VXModule> modules = rawNotebook.rawModules().stream()
				.map(mod -> transform(mod))
				.peek(mod -> moduleCatalog.put(mod.uid(), mod))
				.toList();

		return new VXNotebook(libraries, modules);
	}

	private VXUses transform(final RawUses uses) {
		final List<VXLibrary> libraries = uses.libraries() == null
				? List.of()
				: uses.libraries().stream()
						.map(libraryCatalog::get)
						.toList();

		final List<VXModule> modules = uses.modules() == null
				? List.of()
				: uses.modules().stream()
						.map(name -> createKeyForModule(name))
						.map(moduleCatalog::get)
						.toList();
		return new VXUses(libraries, modules);
	}

	private VXModule transform(final RawModule rawModule) {
		final VXUID moduleUKey = createKeyForModule(rawModule.module());

		final VXUses uses = transform(rawModule.uses());

		final Catalog<String, VXDomainType> domainTypeCatalog = new Catalog<>();
		final Catalog<VXUID, VXEntity> entityCatalog = new Catalog<>();
		//---
		for (var library : uses.libraries()) {
			for (var dt : library.domainTypes()) {
				domainTypeCatalog.put(dt.name(), dt);
			}
		}
		for (var module : uses.modules()) {
			for (var entity : module.entities()) {
				entityCatalog.put(entity.uid(), entity);
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
			final VXUID owner, //the module UKey
			Catalog<String, VXDomainType> domainTypeCatalog,
			Catalog<VXUID, VXEntity> entityCatalog) {
		final List<VXAttribute> attributes = rawEntity.attributes() != null
				? rawEntity.attributes()
						.stream()
						.map(rawAttribute -> transform(rawAttribute, domainTypeCatalog))
						.toList()
				: List.of();

		final List<VXLink> links = rawEntity.links() != null
				? rawEntity.links()
						.stream()
						.map(rawLink -> transform(rawLink, owner, entityCatalog))
						.toList()
				: List.of();

		return new VXEntity(
				createKeyForEntity(owner, rawEntity.name()),
				rawEntity.description(),
				transform(rawEntity.id(), domainTypeCatalog),
				attributes,
				links);
	}

	private static VXId transform(RawId id,
			final Catalog<String, VXDomainType> domainTypeCatalog) {
		return new VXId(
				id.name(),
				id.description(),
				domainTypeCatalog.get(id.domainType()));
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
			final Catalog<String, VXDomainType> domainTypeCatalog) {
		return new VXAttribute(
				rawAttribute.name(),
				rawAttribute.description() != null
						? rawAttribute.description()
						: rawAttribute.name(),
				domainTypeCatalog.get(rawAttribute.domainType()),
				VXCardinality.fromSymbol(rawAttribute.cardinality()));
	}

	private static VXLink transform(
			final RawLink rawLink,
			final VXUID owner,
			final Catalog<VXUID, VXEntity> entityCatalog) {
		return new VXLink(
				rawLink.name(),
				rawLink.description() != null
						? rawLink.description()
						: rawLink.name(),
				createKeyForEntity(owner, rawLink.targetEntityName()),
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
