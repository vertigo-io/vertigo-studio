package io.vertigo.vortex.model;

import io.vertigo.core.lang.Assertion;

public final class Header {
	public final String description;
	public final String[] tags;

	public Header(String description, String[] tags) {
		Assertion.check()
				.isNotBlank(description)
				.isNotNull(tags);
		//---
		this.description = description;
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Header{description='%s', tags='%s'}".formatted(description, tags);
	}
}
