package io.vertigo.vortex.notebook;

import java.io.File;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public record VXNotebookConfig(
		List<File> libraries,
		List<File> modules) {

	public VXNotebookConfig {
		Assertion.check()
				.isNotNull(libraries)
				.isNotNull(modules);
	}
}
