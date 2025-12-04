package io.vertigo.vortex.bronze;

import java.io.File;
import java.util.List;

public record NotebookConfig(
		List<File> libraries,
		List<File> modules) {
}
