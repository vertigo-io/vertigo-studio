package io.vertigo.shell;

import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;

public interface ShellCommand extends Runnable {

	default void run() {
		ShinyRenderer.render(build());
	}

	default ShinyModel build() {
		return null;
	}

	default void reset() {
	}

	default ShinyWriter writer() {
		return ShinyRenderer.writer();
	}
}
