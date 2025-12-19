package io.vertigo.vortex.plugins.notebook.json;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.plugins.notebook.json.library.RawLibrary;
import io.vertigo.vortex.plugins.notebook.json.module.RawModule;

public record RawNotebook(
		List<RawLibrary> rawLibraries,
		List<RawModule> rawModules) {

	public RawNotebook {
		Assertion.check()
				.isNotNull(rawLibraries)
				.isNotNull(rawModules);
	}
}
