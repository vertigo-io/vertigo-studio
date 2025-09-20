package io.vertigo.shell.systems.file.commands.ls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.file.FileContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import picocli.CommandLine.Command;

@Command(name = "ls", description = "List files and directories.")
public final class FileLsCommand implements ShellCommand {

	@Override
	public ShinyComponent build() {
		final FileContext fileContext = FileContext.get();
		final Path path = fileContext.getCurrentAbsolutePath();

		final List<String[]> rows = new ArrayList<>();

		try (Stream<Path> stream = Files.list(path)) {
			stream.forEach(p -> {
				final String[] row = new String[2];
				row[0] = p.getFileName().toString();
				row[1] = Boolean.valueOf(!Files.isDirectory(p)).toString();
				rows.add(row);
			});
		} catch (final IOException e) {
			return Shiny.error()
					.withText("Error listing files: " + e.getMessage())
					.build();
		}

		return Shiny.table()
				.withTitle("files of " + path)
				.withNoDataFound("no files found")
				.withHeader("name", "isFile")
				.addAllRows(rows)
				.build();
	}
}
