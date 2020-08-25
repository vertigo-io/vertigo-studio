package io.vertigo.studio.impl.source.dsl.raw;

import io.vertigo.core.lang.Assertion;

public final class DslRawKey {
	private final String name;

	public static DslRawKey of(final String name) {
		return new DslRawKey(name);
	}

	private DslRawKey(final String name) {
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
	public boolean equals(final Object obj) {
		return (obj instanceof DslRawKey) ? name.equals(((DslRawKey) obj).name) : false;
	}

	@Override
	public String toString() {
		return name;
	}
}
