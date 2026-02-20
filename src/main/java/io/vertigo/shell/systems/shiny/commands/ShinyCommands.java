package io.vertigo.shell.systems.shiny.commands;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.shiny.commands.ShinyCommands.ShinyAsciiCommand;
import io.vertigo.shell.systems.shiny.commands.ShinyCommands.ShinyRoundedCommand;
import io.vertigo.shell.systems.shiny.commands.ShinyCommands.ShinySquareCommand;
import io.vertigo.shell.systems.shiny.commands.ShinyCommands.ShinyUnicodeCommand;
import io.vertigo.shiny.ShinyRenderer;
import picocli.CommandLine.Command;

@Command(
		name = "shiny",
		description = "Shinycommands",
		subcommands = {
				ShinyAsciiCommand.class,
				ShinyUnicodeCommand.class,
				ShinySquareCommand.class,
				ShinyRoundedCommand.class })

public final class ShinyCommands implements ShellCommand {
	@Override
	public void run() {
	}

	@Command(
			name = "ascii",
			description = "Sets ascii mode")

	public static final class ShinyAsciiCommand implements ShellCommand {
		@Override
		public void run() {
			ShinyRenderer.theme().withAscii(true);
		}
	}

	@Command(
			name = "unicode",
			description = "Sets unicode mode")

	public static final class ShinyUnicodeCommand implements ShellCommand {
		@Override
		public void run() {
			ShinyRenderer.theme().withAscii(false);
		}
	}

	@Command(
			name = "square",
			description = "Sets square mode")

	public static final class ShinySquareCommand implements ShellCommand {
		@Override
		public void run() {
			ShinyRenderer.theme().withSquare(true);
		}
	}

	@Command(
			name = "rounded",
			description = "Sets rounded mode")

	public static final class ShinyRoundedCommand implements ShellCommand {
		@Override
		public void run() {
			ShinyRenderer.theme().withSquare(false);
		}
	}
}
