package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class Model {
	public Header header;
	public List<DomainType> domainTypes;
	public List<Entity> entities;

	public Model(Header header, List<DomainType> domainTypes, List<Entity> entities) {
		Assertion.check()
				.isNotNull(header)
				.isNotNull(domainTypes)
				.isNotNull(entities);
		//---
		this.header = header;
		this.domainTypes = domainTypes;
		this.entities = entities;
	}

	@Override
	public String toString() {
		return "Model{header='%s', domainTypes='%s', entities='%s'}".formatted(header, domainTypes, entities);
	}
}
