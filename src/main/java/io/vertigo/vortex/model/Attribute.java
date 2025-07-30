package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;

public final class Attribute {
	public final String name;
	public final DomainType domainType;
	public final Role role;
	public final Cardinality cardinality;

	public Attribute(String name, DomainType domainType, Role role, Cardinality cardinality) {
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
