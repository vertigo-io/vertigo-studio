package io.vertigo.vortex.impl.notebook.raw;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.impl.notebook.raw.library.RawLibrary;
import io.vertigo.vortex.impl.notebook.raw.module.RawModule;

public record RawNotebook(
		List<RawLibrary> rawLibraries,
		List<RawModule> rawModules) {

	public RawNotebook {
		Assertion.check()
				.isNotNull(rawLibraries)
				.isNotNull(rawModules);
	}
}
