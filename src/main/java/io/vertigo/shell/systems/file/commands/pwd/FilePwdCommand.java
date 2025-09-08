package io.vertigo.shell.systems.file.commands.pwd;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.file.FileContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import picocli.CommandLine.Command;

@Command(name = "pwd", description = "Print current directory.")
public final class FilePwdCommand implements ShellCommand {

	@Override
	public void run() {
		final ShinyWriter writer = Shiny.writer();

		final FileContext fileContext = FileContext.get();
		System.out.println("Path");
		System.out.println("____");
		Shiny.textPath()
				.separator("/")
				.path(fileContext.getRootPath().toString())
				.render(writer);
	}
}
