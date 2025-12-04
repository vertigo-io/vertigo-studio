package io.vertigo.vortex.model;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.library.VXLibrary;
import io.vertigo.vortex.model.module.VXModule;

/**
 * Represents the entire data model.
 * This model is composed of a header and a list of files.
 * @synthetic
 */
public record VXNotebook(
		List<VXModule> modules,
		List<VXLibrary> libraries) {

	public VXNotebook {
		Assertion.check()
				.isNotNull(modules)
				.isNotNull(libraries);
	}
}
