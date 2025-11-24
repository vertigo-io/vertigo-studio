package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;


/**
 * Represents a domain type.
 * A domain type defines the characteristics of an attribute, such as its data type and validators.
 * For example, a 'String' domain could have a max length validator.
 * @synthetic
 */
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
