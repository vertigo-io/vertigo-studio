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
import io.vertigo.shiny.ShinyWriter;
import picocli.CommandLine.Command;

@Command(name = "ls", description = "List files and directories.")
public final class FileLsCommand implements ShellCommand {

	@Override
	public void run() {
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
			System.out.println("Error listing files: " + e.getMessage());
		}

		final ShinyWriter writer = Shiny.writer();

		Shiny.table()
				.withTitle("files of " + path)
				.withNoDataFound("no files found")
				.withHeader("name", "isFile")
				.addAllRows(rows)
				.build()
				.render(writer);
	}
}
