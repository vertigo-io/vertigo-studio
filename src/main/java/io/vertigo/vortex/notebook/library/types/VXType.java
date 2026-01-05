package io.vertigo.vortex.notebook.library.types;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;

/**
 * Represents a domain type.
 * A domain type defines the characteristics of an attribute, such as its data type and validators.
 * For example, a 'String' domain could have a max length validator.
 * @synthetic
 */
public record VXType(
		VXKey key,
		String comment,
		VXDataType dataType,
		List<VXValidator> validators,
		List<VXProperty> properties) {

	public VXType {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.DOMAIN_TYPE, "A VXDomainType's key must be of type DOMAIN_TYPE")
				.isNotNull(dataType)
				.isNotNull(validators);

		properties = buildProperties(validators, properties);
	}

	private static List<VXProperty> buildProperties(
			final List<VXValidator> validators,
			final List<VXProperty> properties) {
		final List<VXProperty> _properties = new ArrayList<>(properties);
		for (final var v : validators) {
			_properties.add(v.getProperty());
		}
		return _properties;
	}
}
