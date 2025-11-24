package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;


/**
 * Represents the entire data model.
 * This model is composed of a header, a list of domain types, and a list of entities.
 * It is typically created by a ModelReader.
 * @synthetic
 */
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
