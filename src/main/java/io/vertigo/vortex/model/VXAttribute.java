package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;

public final class VXAttribute {
	public final String name;
	public final VXDomainType domainType;
	public final VXRole role;
	public final VXCardinality cardinality;

	public VXAttribute(final String name, final VXDomainType domainType, final VXRole role, final VXCardinality cardinality) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(domainType)
				.isNotNull(role)
				.isNotNull(cardinality);
		//---
		this.name = name;
		this.domainType = domainType;
		this.role = role;
		this.cardinality = cardinality;
	}

	@Override
	public String toString() {
		return "Attribute{name='%s', domainType='%s', cardinality='%s'}".formatted(name, domainType.name, cardinality);
	}
}
