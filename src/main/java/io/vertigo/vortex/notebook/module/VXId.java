package io.vertigo.vortex.notebook.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;
import io.vertigo.vortex.notebook.library.types.VXDomainType;

/**
 * Represents the id of an entity.
 * @synthetic
 */
public record VXId(
		VXKey key,
		String description,
		VXDomainType domainType) {

	public VXId {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.ID, "An id's key must be of type ID")
				.isNotNull(domainType);
	}
}
