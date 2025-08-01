package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;

public final class VXDomainType {
	public final String name;
	public final VXDataType dataType;

	public VXDomainType(String name, String dataType) {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(dataType);
		//---
		this.name = name;
		this.dataType = VXDataType.valueOf(dataType);
	}

	@Override
	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}
}
