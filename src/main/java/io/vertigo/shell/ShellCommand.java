package io.vertigo.shell;

public interface ShellCommand {

	void run() throws Exception;

	void reset();
}
