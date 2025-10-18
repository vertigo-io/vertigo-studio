package io.vertigo.shell.systems.file.commands.cd;

import java.nio.file.Path;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.file.FileContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "cd", description = "Change directory.")
public final class FileCdCommand implements ShellCommand {

	@Parameters(index = "0", description = "The directory to change to.", arity = "1")
	private String newDirectory;

	@Override
	public ShinyModel build() {
		return changeDirectory();
	}

	private ShinyModel changeDirectory() {
		try {
			final FileContext fileContext = FileContext.get();

			final Path newPath = Path.of(newDirectory);
			fileContext.changeDirectory(newPath);
			return Shiny.textPath()
					.withPath(fileContext.getCurrentAbsolutePath().toString())
					.withSeparator("/")
					.build();
		} catch (Exception e) {
			return Shiny.error()
					.withText(e.getMessage())
					.build();
		}
	}
}
