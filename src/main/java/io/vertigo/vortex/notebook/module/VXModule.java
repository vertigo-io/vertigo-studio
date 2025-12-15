package io.vertigo.vortex.notebook.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXIdentification;

/**
 * Represents a module.
 * A module is composed of a list of entities.
 * @synthetic 
 */
public record VXModule(
		VXIdentification identification,
		VXUses uses,
		List<VXEntity> entities) {

	public VXModule {
		Assertion.check()
				.isNotNull(identification)
				.isTrue(identification.key().type() == VXElementType.MODULE, "A module's key must be of type MODULE")
				.isNotNull(uses)
				.isNotNull(entities);
	}
}
