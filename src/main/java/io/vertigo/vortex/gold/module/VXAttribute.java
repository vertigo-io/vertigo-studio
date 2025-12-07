package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.library.types.VXDomainType;

/**
 * Represents an attribute of an entity.
 * An attribute has a name, a domain type, a role, and a cardinality.
 * For example, a 'title' attribute for a 'Book' entity.
 * @synthetic
 */
public record VXAttribute(
		String name,
		String description,
		VXDomainType domainType,
		VXRole role,
		VXCardinality cardinality) {

	public VXAttribute {
		Assertion.check()
				.isNotBlank(name)
				//				.isTrue(name.matches("[a-z](1"))
				.isNotNull(domainType)
				.isNotNull(role)
				.isNotNull(cardinality);
	}
}
