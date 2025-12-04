package io.vertigo.vortex.raw;

import java.util.List;

import io.vertigo.vortex.raw.library.RawLibrarySource;
import io.vertigo.vortex.raw.module.RawModuleSource;

public record RawNotebook(
		List<RawLibrarySource> libraries,
		List<RawModuleSource> rawModules) {
}
