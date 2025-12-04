package io.vertigo.vortex.silver;

import java.util.List;

import io.vertigo.vortex.silver.library.RawLibrary;
import io.vertigo.vortex.silver.module.RawModule;

public record RawNotebook(
		List<RawLibrary> libraries,
		List<RawModule> rawModules) {
}
