package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record VXDomainType(
		String name,
		VXDataType dataType,
		List<VXValidator> validators) {

	public VXDomainType {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(dataType)
				.isNotNull(validators);
	}

	@Override

	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}
}
