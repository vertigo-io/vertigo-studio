package io.vertigo.vortex.plugins.notebook.json;

import java.util.List;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXInfo;
import io.vertigo.vortex.notebook.VXKey;
import io.vertigo.vortex.notebook.VXNotebook;
import io.vertigo.vortex.notebook.library.VXLibrary;
import io.vertigo.vortex.notebook.library.types.VXDataType;
import io.vertigo.vortex.notebook.library.types.VXType;
import io.vertigo.vortex.notebook.module.VXAttribute;
import io.vertigo.vortex.notebook.module.VXEntity;
import io.vertigo.vortex.notebook.module.VXId;
import io.vertigo.vortex.notebook.module.VXImports;
import io.vertigo.vortex.notebook.module.VXLink;
import io.vertigo.vortex.notebook.module.VXLinkStereotype;
import io.vertigo.vortex.notebook.module.VXModule;
import io.vertigo.vortex.notebook.module.VXValueObject;
import io.vertigo.vortex.plugins.notebook.json.raw.RawInfo;
import io.vertigo.vortex.plugins.notebook.json.raw.RawNotebook;
import io.vertigo.vortex.plugins.notebook.json.raw.library.RawDomainType;
import io.vertigo.vortex.plugins.notebook.json.raw.library.RawLibrary;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawAttribute;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawEntity;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawId;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawImports;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawLink;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawModule;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawValueObject;

/**
 * Transforms a raw model into a VXModel.
 * This class is responsible for converting the raw data read from the JSON file
 * into the final, structured VXModel.
 * It builds a catalog of domain types and entities.
 * @synthetic
 */
final class RawToNotebook {
	private final RawNotebook rawNotebook;
	private final Catalog<VXLibrary> libraryCatalog = new Catalog<>();
	private final Catalog<VXModule> moduleCatalog = new Catalog<>();

	RawToNotebook(final RawNotebook rawNotebook) {
		Assertion.check().isNotNull(rawNotebook);
		//---
		this.rawNotebook = rawNotebook;
	}

	private static VXKey createKeyForType(String name) {
		//!!!!!!
		//!!!!!! TODO
		//!!!!!!
		//!!!!!!
		final VXKey libraryKey = new VXKey(null, VXElementType.LIBRARY, "core");
		return new VXKey(libraryKey, VXElementType.TYPE, name);
	}

	// Updated to use key
	private static VXKey createKeyForEntity(VXKey owner, String key) {
		//si key ne contient pas de "." alors on prends l'owner ( on reste dans le même module)
		//Sinon on cherche me module
		int i = key.indexOf(".");
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

		return new VXKey(_owner, VXElementType.ENTITY, key);
	}

	private static VXKey createKeyForValueObject(VXKey owner, String key) {
		//si key ne contient pas de "." alors on prends l'owner ( on reste dans le même module)
		//Sinon on cherche me module
		int i = key.indexOf(".");
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
		return new VXKey(_owner, VXElementType.VALUE, key);
	}

	/**
	 * Transforms the raw model into a VXNotebook.
	 */
	VXNotebook toNotebook() {
		final List<VXLibrary> libraries = rawNotebook.rawLibraries().stream()
				.map(rawLibrary -> transform(rawLibrary))
				.peek(library -> libraryCatalog.put(library.info().key(), library))
				.toList();

		final List<VXModule> modules = rawNotebook.rawModules().stream()
				.map(rawModule -> transform(rawModule))
				.peek(module -> moduleCatalog.put(module.info().key(), module))
				.toList();

		return new VXNotebook(libraries, modules);
	}

	private static VXKey createKeyForModule(String name) {
		return new VXKey(null, VXElementType.MODULE, name);
	}

	private static VXKey createKeyForLibrary(String name) {
		return new VXKey(null, VXElementType.LIBRARY, name);
	}

	private VXImports transform(final RawImports imports) {
		final List<VXLibrary> libraries = imports.libraries() == null
				? List.of()
				: imports.libraries().stream()
						.map(RawToNotebook::createKeyForLibrary)
						.map(libraryCatalog::get)
						.toList();

		final List<VXModule> modules = imports.modules() == null
				? List.of()
				: imports.modules().stream()
						.map(RawToNotebook::createKeyForModule)
						.map(moduleCatalog::get)
						.toList();
		return new VXImports(libraries, modules);
	}

	private static VXInfo transform(final RawInfo rawIdentification, Function<String, VXKey> createKey) {
		final VXKey key = createKey.apply(rawIdentification.key());
		return new VXInfo(
				key,
				rawIdentification.comment(),
				rawIdentification.tags());

	}

	private VXModule transform(final RawModule rawModule) {
		final VXInfo info = transform(rawModule.moduleInfo(), RawToNotebook::createKeyForModule);

		final VXImports imports = transform(rawModule.imports());

		final Catalog<VXType> typeCatalog = new Catalog<>();
		final Catalog<VXEntity> entityCatalog = new Catalog<>();
		//---
		for (var library : imports.libraries()) {
			for (var dt : library.domainTypes()) {
				typeCatalog.put(dt.key(), dt);
			}
		}
		for (var module : imports.modules()) {
			for (var entity : module.entities()) {
				entityCatalog.put(entity.key(), entity);
			}
		}

		final List<VXEntity> entities = rawModule.entities().stream()
				.map(e -> transform(e, info.key(), typeCatalog, entityCatalog))
				.toList();

		final List<VXValueObject> valueObjects = rawModule.valueObjects().stream()
				.map(vo -> transform(vo, info.key(), typeCatalog))
				.toList();

		return new VXModule(
				info,
				imports,
				entities,
				valueObjects);
	}

	private static VXValueObject transform(
			final RawValueObject rawValueObject,
			final VXKey owner,
			final Catalog<VXType> typeCatalog) {
		final VXKey voKey = createKeyForValueObject(owner, rawValueObject.key());
		final List<VXAttribute> attributes = rawValueObject.attributes() != null
				? rawValueObject.attributes().entrySet().stream()
						.map(entry -> transform(entry.getKey(), entry.getValue(), voKey, typeCatalog))
						.toList()
				: List.of();

		return new VXValueObject(
				voKey,
				rawValueObject.comment(),
				attributes);
	}

	private static VXEntity transform(
			final RawEntity rawEntity,
			final VXKey owner, //the module UKey
			Catalog<VXType> domainTypeCatalog,
			Catalog<VXEntity> entityCatalog) {
		final VXKey entityKey = createKeyForEntity(owner, rawEntity.key());
		final List<VXAttribute> attributes = rawEntity.attributes() != null
				? rawEntity.attributes().entrySet().stream()
						.map(entry -> transform(entry.getKey(), entry.getValue(), entityKey, domainTypeCatalog))
						.toList()
				: List.of();

		final List<VXLink> links = rawEntity.links() != null
				? rawEntity.links().entrySet().stream()
						.map(entry -> transform(entry.getKey(), entry.getValue(), entityKey, owner, entityCatalog, entry.getValue().required(), VXLinkStereotype.valueOf(entry.getValue().stereotype())))
						.toList()
				: List.of();

		return new VXEntity(
				entityKey,
				rawEntity.comment(),
				transform(rawEntity.id(), entityKey, domainTypeCatalog),
				attributes,
				links);
	}

	private static VXId transform(RawId id,
			final VXKey entityKey,
			final Catalog<VXType> domainTypeCatalog) {
		final VXKey idKey = new VXKey(entityKey, VXElementType.ID, id.key());
		return new VXId(
				idKey,
				id.label(),
				id.comment(),
				domainTypeCatalog.get(createKeyForType(id.domainType())));
	}

	private static VXLibrary transform(final RawLibrary rawLibrary) {
		final VXInfo info = transform(rawLibrary.libraryInfo(), RawToNotebook::createKeyForLibrary);

		final List<VXType> domainTypes = rawLibrary.domainTypes().stream()
				.map(dt -> transform(dt, info.key()))
				.toList();

		return new VXLibrary(
				info,
				domainTypes);
	}

	private static VXAttribute transform(
			final String attributeKey,
			final RawAttribute rawAttribute,
			final VXKey entityKey,
			final Catalog<VXType> typeCatalog) {
		final VXKey attributeVXKey = new VXKey(entityKey, VXElementType.ATTRIBUTE, attributeKey);

		int found = rawAttribute.domainType().indexOf(':');
		final VXType type = switch (rawAttribute.domainType().substring(0, found)) {
			case "do" -> typeCatalog.get(createKeyForType(rawAttribute.domainType()));
			case "json" -> typeCatalog.get(createKeyForType("do:json"));
			default -> throw new VSystemException("type '{0}' must be do:xxx or value:xxx", rawAttribute.domainType());
		};
		return new VXAttribute(
				attributeVXKey,
				rawAttribute.label(),
				rawAttribute.comment(),
				type,
				rawAttribute.required());

	}

	private static VXLink transform(
			final String linkKey,
			final RawLink rawLink,
			final VXKey entityKey,
			final VXKey owner,
			final Catalog<VXEntity> entityCatalog,
			final boolean required,
			final VXLinkStereotype stereotype) {
		final VXKey linkVXKey = new VXKey(entityKey, VXElementType.LINK, linkKey);
		return new VXLink(
				linkVXKey,
				rawLink.label(),
				rawLink.comment(),
				createKeyForEntity(owner, rawLink.targetEntityKey()),
				required,
				stereotype);
	}

	private static VXType transform(
			final RawDomainType rawType,
			final VXKey libraryKey) {
		final VXKey typeKey = new VXKey(libraryKey, VXElementType.TYPE, rawType.key());
		return new VXType(
				typeKey,
				rawType.comment(),
				VXDataType.valueOf(rawType.dataType()),
				//!!!!!!!!!!!!!!!!!!!!!!!
				List.of(),
				//!!!!!!!!!!!!!!!!!!!!!!!
				List.of());
	}
}
