package io.vertigo.shell;

public interface ShellCommand extends Runnable {

	default void reset() {
	}
}
