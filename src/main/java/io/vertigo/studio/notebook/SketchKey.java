package io.vertigo.studio.notebook;

import io.vertigo.core.lang.Assertion;

public final class SketchKey {
	private final String name;

	public static SketchKey of(final String name) {
		return new SketchKey(name);
	}

	private SketchKey(String name) {
		Assertion.check().isNotBlank(name);
		//---
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof SketchKey && name.equals(((SketchKey) obj).name);
	}

	@Override
	public String toString() {
		return name;
	}
}
