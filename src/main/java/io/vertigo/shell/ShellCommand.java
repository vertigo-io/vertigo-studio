package io.vertigo.shell;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;

public interface ShellCommand extends Runnable {

	default void reset() {
	}

	default ShinyWriter writer() {
		return Shiny.writer();
	}
}
