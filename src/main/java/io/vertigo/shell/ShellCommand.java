package io.vertigo.shell;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public interface ShellCommand extends Runnable {

	default void run() {
		Shiny.render(build());
	}

	default ShinyComponent build() {
		return null;
	}

	default void reset() {
	}

	default ShinyWriter writer() {
		return Shiny.writer();
	}
}
