package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class Entity {
	public final String name;
	public final List<Attribute> attributes;

	public Entity(String name, List<Attribute> attributes) {
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
