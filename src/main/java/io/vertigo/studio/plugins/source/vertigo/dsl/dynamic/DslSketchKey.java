package io.vertigo.studio.plugins.source.vertigo.dsl.dynamic;

import io.vertigo.core.lang.Assertion;

public final class DslSketchKey {
	private final String name;

	public static DslSketchKey of(final String name) {
		return new DslSketchKey(name);
	}

	private DslSketchKey(String name) {
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
		return (obj instanceof DslSketchKey) ? name.equals(((DslSketchKey) obj).name) : false;
	}

	@Override
	public String toString() {
		return name;
	}
}
