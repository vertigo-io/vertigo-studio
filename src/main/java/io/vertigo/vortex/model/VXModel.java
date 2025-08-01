package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class VXModel {
	public VXHeader header;
	public List<VXDomainType> domainTypes;
	public List<VXEntity> entities;

	public VXModel(VXHeader header, List<VXDomainType> domainTypes, List<VXEntity> entities) {
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
