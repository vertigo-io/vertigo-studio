package io.vertigo.vortex.gold.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.VXUID;

/**
 * Represents a module.
 * A module is composed of a list of entities.
 * @synthetic 
 */
public record VXModule(
		VXUID uid,
		String description,
		VXUses uses,
		List<VXEntity> entities) {

	public VXModule {
		Assertion.check()
				.isNotNull(uid)
				.isNotBlank(description)
				.isNotNull(uses)
				.isNotNull(entities);
	}
}
