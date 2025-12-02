package io.vertigo.vortex.model.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		List<VxProperty> properties) {

	public VXDomainType(String name,
			VXDataType dataType,
			List<VXValidator> validators,
			List<VxProperty> properties) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(dataType)
				.isNotNull(validators);
		this.name = name;
		this.dataType = dataType;
		this.validators = validators;
		this.properties = buildProperties(validators, properties);
	}

	private static List<VxProperty> buildProperties(
			List<VXValidator> validators,
			List<VxProperty> properties) {
		final List<VxProperty> _properties = new ArrayList<>(properties);
		for (var v : validators) {
			if (v.getProperty().isPresent()) {
				final Optional<VxProperty> propertyOpt = v.getProperty();
				_properties.add(propertyOpt.get());
			}
		}
		return _properties;
	}

	@Override
	public String toString() {
		return "DomainType{name='%s', dataType='%s'}".formatted(name, dataType);
	}
}
