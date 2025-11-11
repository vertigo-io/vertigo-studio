package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class VXDomainType {
	public final String name;
	public final VXDataType dataType;
	public final List<VXValidator> validators;

	public VXDomainType(final String name, final VXDataType dataType, final List<VXValidator> validators) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(dataType)
				.isNotNull(validators);
		//---
		this.name = name;
		this.dataType = dataType;
		this.validators = validators;
	}

	@Override

	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}
}
