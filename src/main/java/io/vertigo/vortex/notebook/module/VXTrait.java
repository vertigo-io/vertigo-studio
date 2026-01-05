package io.vertigo.vortex.notebook.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;

public record VXTrait(
		VXKey key,
		String comment,
		List<VXAttribute> attributes) {

	public VXTrait {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.TRAIT, "A trait's key must be of type TRAIT")
				// comment can be null
				.isNotNull(attributes);
	}
}
