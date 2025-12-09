package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.library.types.VXDomainType;

/**
 * Represents the id of an entity.
 * @synthetic
 */
public record VXId(
		String name,
		String description,
		VXDomainType domainType) {

	public VXId {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(domainType);
	}
}
