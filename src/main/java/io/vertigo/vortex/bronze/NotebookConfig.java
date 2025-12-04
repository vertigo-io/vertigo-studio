package io.vertigo.vortex.bronze;

import java.io.File;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public record NotebookConfig(
		List<File> libraries,
		List<File> modules) {

	public NotebookConfig {
		Assertion.check()
				.isNotNull(libraries)
				.isNotNull(modules);
	}
}
