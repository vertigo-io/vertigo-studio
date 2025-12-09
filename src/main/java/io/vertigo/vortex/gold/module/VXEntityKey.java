package io.vertigo.vortex.gold.module;

import io.vertigo.core.lang.Assertion;

public record VXEntityKey(String name) {
	public VXEntityKey {
		Assertion.check().isNotBlank(name);
	}
}
