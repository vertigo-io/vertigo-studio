package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.modules.VXModule;
import io.vertigo.vortex.model.types.VXLibrary;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXModel(
		List<VXModule> modules,
		VXLibrary library) {

	public VXModel {
		Assertion.check()
				.isNotNull(modules)
				.isNotNull(library);
	}

	@Override
	public String toString() {
		return "Model{modules='%s', library='%s'}"
				.formatted(modules, library);
	}
}
