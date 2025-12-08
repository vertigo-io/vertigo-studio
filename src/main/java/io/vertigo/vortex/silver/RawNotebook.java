package io.vertigo.vortex.silver;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.silver.library.RawLibrary;
import io.vertigo.vortex.silver.module.RawModule;

public record RawNotebook(
		List<RawLibrary> rawLibraries,
		List<RawModule> rawModules) {

	public RawNotebook {
		Assertion.check()
				.isNotNull(rawLibraries)
				.isNotNull(rawModules);
	}
}
