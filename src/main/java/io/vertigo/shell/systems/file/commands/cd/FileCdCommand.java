package io.vertigo.shell.systems.file.commands.cd;

import java.io.IOException;
import java.nio.file.Path;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.file.FileContext;
import io.vertigo.shiny.Shiny;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "cd", description = "Change directory.")
public final class FileCdCommand implements ShellCommand {

	@Parameters(index = "0", description = "The directory to change to.", arity = "1")
	private String newDirectory;

	@Override
	public void run() {
		try {
			changeDirectory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void changeDirectory() throws IOException {
		final FileContext fileContext = FileContext.get();

		final Path newPath = Path.of(newDirectory);

		try {
			fileContext.changeDirectory(newPath);
			Shiny.textPath()
					.path(fileContext.getCurrentAbsolutePath().toString())
					.separator("/")
					.print();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
