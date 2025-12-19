package io.vertigo.vortex.notebook;

import static io.vertigo.vortex.notebook.VXElementType.ENTITY;
import static io.vertigo.vortex.notebook.VXElementType.LIBRARY;
import static io.vertigo.vortex.notebook.VXElementType.MODULE;
import static io.vertigo.vortex.notebook.VXElementType.VALUE;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;

/**
 * An UID is defined by 
 *  - Its owner
 *  - a name and a Type
 * 
 * Keys are organized in a tree 
 * 
 * root
 * │
 * ├── module
 * │   └── entity
 * │       ├── id 
 * │       ├── attribute 
 * │       └── link ( link or partOf)
 * │
 * └── library
 *     └── domainType
 */
public record VXKey(VXKey owner, VXElementType type, String name) {

	public VXKey {
		Assertion.check()
				.isNotNull(type)
				.isNotBlank(name);

		switch (type) {
			case MODULE, LIBRARY -> Assertion.check().isNull(owner, "Owner of a '{0}' must be null", type);
			case VALUE, ENTITY -> Assertion.check().isTrue(owner != null && owner.type() == MODULE, "Owner of an '{0}' must be a '{1}'", type, MODULE);
			case TYPE -> Assertion.check().isTrue(owner != null && owner.type() == LIBRARY, "Owner of a '{0}' must be a '{1}'", type, LIBRARY);
			case ATTRIBUTE -> Assertion.check().isTrue(owner != null && (owner.type() == ENTITY || owner.type() == VXElementType.VALUE), "Owner of an '{0}' must be an '{1}' or '{2}'", type, ENTITY, VALUE);
			case ID, LINK -> Assertion.check().isTrue(owner != null && owner.type() == ENTITY, "Owner of an '{0}' must be an '{1}'", type, ENTITY);
			default -> throw new VSystemException("This element type '{0}' is not supported", type);
		}
	}
}
