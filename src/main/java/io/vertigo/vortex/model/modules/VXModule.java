package io.vertigo.vortex.model.modules;

import java.util.List;

import io.vertigo.core.lang.Assertion;

/**
 * Represents a module.
 * A module is composed of a list of entities.
 * @synthetic
 */
public record VXModule(
		List<VXEntity> entities) {

	public VXModule {
		Assertion.check()
				.isNotNull(entities);
	}

	@Override
	public String toString() {
		return "Module{entities='%s''}"
				.formatted(entities);
	}
}
