package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXElementType;
import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDomainType;

/**
 * Represents an attribute of an entity.
 * An attribute has a name, a domain type, a role, and a cardinality.
 * For example, a 'title' attribute for a 'Book' entity.
 * @synthetic
 */
public record VXAttribute(
		VXKey key,
		String description,
		VXDomainType domainType,
		VXCardinality cardinality) {

	public VXAttribute {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.ATTRIBUTE, "An attribute's key must be of type ATTRIBUTE")
				.isNotNull(domainType)
				.isNotNull(cardinality);
	}
}
