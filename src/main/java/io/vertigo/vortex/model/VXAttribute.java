package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;

public record VXAttribute(
		String name,
		VXDomainType domainType,
		VXRole role,
		VXCardinality cardinality) {

	public VXAttribute {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(domainType)
				.isNotNull(role)
				.isNotNull(cardinality);
	}

	@Override
	public String toString() {
		return "Attribute{name='%s', domainType='%s', cardinality='%s'}".formatted(name, domainType.name(), cardinality);
	}
}
