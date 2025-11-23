package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record VXEntity(
		String name,
		List<VXAttribute> attributes) {

	public VXEntity {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(attributes);
	}

	@Override
	public String toString() {
		return "Entity{name='%s', attributes='%s'}".formatted(name, attributes);
	}
}
