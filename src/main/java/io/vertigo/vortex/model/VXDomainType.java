package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class VXDomainType {
	public final String name;
	public final VXDataType dataType;
	public final List<VXValidator> validators;

	public VXDomainType(final String name, final String dataType, final List<VXValidator> validators) {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(dataType)
				.isNotNull(validators);
		//---
		this.name = name;
		this.dataType = VXDataType.valueOf(dataType);
		this.validators = validators;
	}

	@Override

	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}

	public static VXDomainTypeBuilder builder(final String name, final VXDataType dataType) {
		return new VXDomainTypeBuilder(name, dataType);
	}
}
