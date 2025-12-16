package io.vertigo.vortex.notebook.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.library.VXLibrary;

/**
 * Dependencies
 */
public record VXImports(
		List<VXLibrary> libraries,
		List<VXModule> modules) {

	public VXImports {
		Assertion.check()
				.isNotNull(libraries)
				.isNotNull(modules);
	}
}
