package io.vertigo.shell;

import java.time.Instant;
import java.util.Locale;

import io.vertigo.studio.notebook.NotebookConfig;

public final class ShellContext {
	public static Locale LOCALE = Locale.FRANCE;
	public static final Instant startTime = Instant.now();
	public static NotebookConfig notebookConfig;
}
