package io.vertigo.vortex.gold;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.gold.library.VXLibrary;
import io.vertigo.vortex.gold.module.VXModule;

/**
 * Represents the entire model called "Notebook".
 * This notebook is composed of 
 * - a list of modules  
 * - a list of libraries
 * 
 * @synthetic
 */
public record VXNotebook(
		List<VXLibrary> libraries,
		List<VXModule> modules) {

	public VXNotebook {
		Assertion.check()
				.isNotNull(libraries)
				.isNotNull(modules);
	}
}
