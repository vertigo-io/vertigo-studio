package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;

public final class DomainType {
	public final String name;
	public final String dataType;

	public DomainType(String name, String dataType) {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(dataType);
		//---
		this.name = name;
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}

	public String toJavaType() {
		String type = switch (dataType) {
			case "String" -> "String";
			case "Integer" -> "Integer";
			case "Decimal" -> "java.math.BigDecimal";
			case "Float" -> "Float";
			case "Boolean" -> "Boolean";
			case "Date" -> "java.time.LocalDate";
			default -> throw new IllegalArgumentException();
		};
		return type;
	}
}
