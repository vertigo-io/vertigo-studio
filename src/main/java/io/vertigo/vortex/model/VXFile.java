package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.types.VXDomainType;

public record VXFile(
		VXHeader header,
		List<VXDomainType> domainTypes,
		List<VXEntity> entities) {

	public VXFile {
		Assertion.check()
				.isNotNull(header)
				.isNotNull(domainTypes)
				.isNotNull(entities);
	}

	@Override
	public String toString() {
		return "File{header='%s', domainTypes='%s', entities='%s'}".formatted(header, domainTypes, entities);
	}
}
