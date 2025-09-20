package io.vertigo.shell.systems.file.commands.pwd;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.file.FileContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import picocli.CommandLine.Command;

@Command(name = "pwd", description = "Print current directory.")
public final class FilePwdCommand implements ShellCommand {

	@Override
	public ShinyComponent build() {
		final FileContext fileContext = FileContext.get();
		return Shiny.textPath()
				.withSeparator("/")
				.withPath(fileContext.getRootPath().toString())
				.build();
	}
}
