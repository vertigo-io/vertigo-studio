package io.vertigo.vortex.model.types;

import java.util.ArrayList;
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
		List<VXValidator> validators,
		List<VXProperty> properties) {

	public VXDomainType(String name,
			VXDataType dataType,
			List<VXValidator> validators,
			List<VXProperty> properties) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(dataType)
				.isNotNull(validators);
		this.name = name;
		this.dataType = dataType;
		this.validators = validators;
		this.properties = buildProperties(validators, properties);
	}

	private static List<VXProperty> buildProperties(
			List<VXValidator> validators,
			List<VXProperty> properties) {
		final List<VXProperty> _properties = new ArrayList<>(properties);
		for (var v : validators) {
			_properties.add(v.getProperty());
		}
		return _properties;
	}

	@Override
	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}
}
