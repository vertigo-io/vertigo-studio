package io.vertigo.vortex.notebook.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;

/**
 * Represents a module.
 * A module is composed of a list of entities.
 * @synthetic 
 */
public record VXModule(
		VXKey key,
		String description,
		VXUses uses,
		List<VXEntity> entities) {

	public VXModule {
		Assertion.check()
				.isNotNull(key)
				.isTrue(key.type() == VXElementType.MODULE, "An module's key must be of type MODULE_TYPE")
				.isNotBlank(description)
				.isNotNull(uses)
				.isNotNull(entities);
	}
}
