package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXElementType;
import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDomainType;

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
