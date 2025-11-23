package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record VXModel(
		VXHeader header,
		List<VXDomainType> domainTypes,
		List<VXEntity> entities) {

	public VXModel {
		Assertion.check()
				.isNotNull(header)
				.isNotNull(domainTypes)
				.isNotNull(entities);
	}

	@Override
	public String toString() {
		return "Model{header='%s', domainTypes='%s', entities='%s'}".formatted(header, domainTypes, entities);
	}
}
