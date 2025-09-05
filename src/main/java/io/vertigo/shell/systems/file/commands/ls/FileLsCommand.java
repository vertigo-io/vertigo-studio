package io.vertigo.shell.systems.file.commands.ls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.file.FileContext;
import picocli.CommandLine.Command;

@Command(name = "ls", description = "List files and directories.")
public final class FileLsCommand implements ShellCommand {

	@Override
	public void run() {
		final FileContext fileContext = FileContext.get();
		final Path path = fileContext.getCurrentAbsolutePath();
		try (Stream<Path> stream = Files.list(path)) {
			stream.forEach(p -> {
				final String color = Files.isDirectory(path) ? "34" : "0"; // blue for directories
				System.out.println("\u001B[" + color + "m" + p.getFileName().toString() + "\u001B[0m");
			});
		} catch (final IOException e) {
			System.out.println("Error listing files: " + e.getMessage());
		}
	}
}
