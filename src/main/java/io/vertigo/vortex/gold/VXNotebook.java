package io.vertigo.vortex.gold;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.library.VXLibrary;
import io.vertigo.vortex.gold.module.VXModule;

/**
 * Represents the entire model called "Notebook".
 * This notebook is composed of a list of modules and a list of libraries.
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
