package io.vertigo.vortex.gold.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;

/**
 * Represents a module.
 * A module is composed of a list of entities.
 * @synthetic
 */
public record VXModule(
		String name,
		List<VXEntity> entities) {

	public VXModule {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(entities);
	}
}
