package io.vertigo.vortex.notebook.module;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.library.VXLibrary;

/**
 * Dependencies
 */
public record VXUses(
		List<VXLibrary> libraries,
		List<VXModule> modules) {

	public VXUses {
		Assertion.check()
				.isNotNull(libraries)
				.isNotNull(modules);
	}
}
