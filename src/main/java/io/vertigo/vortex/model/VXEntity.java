package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class VXEntity {
	public final String name;
	public final List<VXAttribute> attributes;

	public VXEntity(final String name, final List<VXAttribute> attributes) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(attributes);
		//---
		this.name = name;
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "Entity{name='%s', attributes='%s'}".formatted(name, attributes);
	}
}
