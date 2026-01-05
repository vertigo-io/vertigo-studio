package io.vertigo.vortex.notebook.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXInfo;

/**
 * Represents a module.
 * A module is composed of a list of entities.
 * @synthetic 
 */
public record VXModule(
		VXInfo info,
		VXImports imports,
		List<VXEntity> entities,
		List<VXValueObject> valueObjects,
		List<VXTrait> traits) {

	public VXModule {
		Assertion.check()
				.isNotNull(info)
				.isTrue(info.key().type() == VXElementType.MODULE, "A module's key must be of type MODULE")
				.isNotNull(imports)
				.isNotNull(entities)
				.isNotNull(valueObjects)
				.isNotNull(traits);
	}
}
