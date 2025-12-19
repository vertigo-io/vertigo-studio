package io.vertigo.vortex.notebook.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;

public record VXValueObject(
		VXKey key,
		String comment,
		List<VXAttribute> attributes) {

	public VXValueObject {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.VALUE, "A value object's key must be of type VALUE_OBJECT")
				.isNotBlank(comment)
				.isNotNull(attributes);
	}
}
