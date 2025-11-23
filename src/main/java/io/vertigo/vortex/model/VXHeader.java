package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;

public record VXHeader(
		String description,
		String[] tags) {

	public VXHeader {
		Assertion.check()
				.isNotBlank(description)
				.isNotNull(tags);
	}

	@Override
	public String toString() {
		return "Header{description='%s', tags='%s'}".formatted(description, tags);
	}
}
