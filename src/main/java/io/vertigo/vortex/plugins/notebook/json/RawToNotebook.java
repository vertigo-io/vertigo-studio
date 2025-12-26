package io.vertigo.vortex.plugins.notebook.json;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXIdentification;
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
import io.vertigo.vortex.plugins.notebook.json.raw.RawNotebook;
import io.vertigo.vortex.plugins.notebook.json.raw.library.RawLibrary;
import io.vertigo.vortex.plugins.notebook.json.raw.library.RawType;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawAttribute;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawEntity;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawId;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawImports;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawLink;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawModule;
import io.vertigo.vortex.plugins.notebook.json.raw.module.RawValue;

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

	private static VXKey createKeyForModule(String name) {
		return new VXKey(null, VXElementType.MODULE, name);
	}

	private static VXKey createKeyForLibrary(String name) {
		return new VXKey(null, VXElementType.LIBRARY, name);
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
				.peek(library -> libraryCatalog.put(library.identification().key(), library))
				.toList();

		final List<VXModule> modules = rawNotebook.rawModules().stream()
				.map(rawModule -> transform(rawModule))
				.peek(module -> moduleCatalog.put(module.identification().key(), module))
				.toList();

		return new VXNotebook(libraries, modules);
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

	private VXModule transform(final RawModule rawModule) {
		final VXKey moduleKey = createKeyForModule(rawModule.module().key());
		final VXIdentification identification = new VXIdentification(
				moduleKey,
				rawModule.module().comment(),
				rawModule.module().tags());

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
				.map(e -> transform(e, moduleKey, typeCatalog, entityCatalog))
				.toList();

		final List<VXValueObject> valueObjects = rawModule.values().stream()
				.map(vo -> transform(vo, moduleKey, typeCatalog))
				.toList();

		return new VXModule(
				identification,
				imports,
				entities,
				valueObjects);
	}

	private static VXValueObject transform(final RawValue rawValue, final VXKey owner, final Catalog<VXType> typeCatalog) {
		final VXKey voKey = createKeyForValueObject(owner, rawValue.key());
		final List<VXAttribute> attributes = rawValue.attributes() != null
				? rawValue.attributes().stream()
						.map(rawAttribute -> transform(rawAttribute, voKey, typeCatalog))
						.toList()
				: List.of();

		return new VXValueObject(
				voKey,
				rawValue.comment(),
				attributes);
	}

	private static VXEntity transform(
			final RawEntity rawEntity,
			final VXKey owner, //the module UKey
			Catalog<VXType> domainTypeCatalog,
			Catalog<VXEntity> entityCatalog) {
		final VXKey entityKey = createKeyForEntity(owner, rawEntity.key());
		final List<VXAttribute> attributes = rawEntity.attributes() != null
				? rawEntity.attributes().stream()
						.map(rawAttribute -> transform(rawAttribute, entityKey, domainTypeCatalog))
						.toList()
				: List.of();

		final List<VXLink> links = rawEntity.links() != null
				? rawEntity.links().stream()
						.map(rawLink -> transform(rawLink, entityKey, owner, entityCatalog, rawLink.required(), VXLinkStereotype.valueOf(rawLink.stereotype())))
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
				domainTypeCatalog.get(createKeyForType(id.type())));
	}

	private static VXLibrary transform(final RawLibrary rawLibrary) {
		final VXKey libraryKey = createKeyForLibrary(rawLibrary.library().key());
		final VXIdentification identification = new VXIdentification(
				libraryKey,
				rawLibrary.library().comment(),
				rawLibrary.library().tags());
		final List<VXType> domainTypes = rawLibrary.types().stream()
				.map(dt -> transform(dt, libraryKey))
				.toList();

		return new VXLibrary(
				identification,
				domainTypes);
	}

	private static VXAttribute transform(
			final RawAttribute rawAttribute,
			final VXKey entityKey,
			final Catalog<VXType> typeCatalog) {
		final VXKey attributeKey = new VXKey(entityKey, VXElementType.ATTRIBUTE, rawAttribute.key());

		int found = rawAttribute.type().indexOf(':');
		final VXType type = switch (rawAttribute.type().substring(0, found)) {
			case "do" -> typeCatalog.get(createKeyForType(rawAttribute.type()));
			case "json" -> typeCatalog.get(createKeyForType("do:json"));
			default -> throw new VSystemException("type '{0}' must be do:xxx or value:xxx", rawAttribute.type());
		};
		return new VXAttribute(
				attributeKey,
				rawAttribute.label(),
				rawAttribute.comment(),
				type,
				rawAttribute.required());

	}

	private static VXLink transform(
			final RawLink rawLink,
			final VXKey entityKey,
			final VXKey owner,
			final Catalog<VXEntity> entityCatalog,
			final boolean required,
			final VXLinkStereotype stereotype) {
		final VXKey linkKey = new VXKey(entityKey, VXElementType.LINK, rawLink.key());
		return new VXLink(
				linkKey,
				rawLink.label(),
				rawLink.comment(),
				createKeyForEntity(owner, rawLink.targetEntityKey()),
				required,
				stereotype);
	}

	private static VXType transform(
			final RawType rawType,
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
