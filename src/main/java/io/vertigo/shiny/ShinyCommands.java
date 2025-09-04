package io.vertigo.shiny;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shiny.ShinyCommands.ShinyAsciiCommand;
import io.vertigo.shiny.ShinyCommands.ShinyRoundedCommand;
import io.vertigo.shiny.ShinyCommands.ShinySquareCommand;
import io.vertigo.shiny.ShinyCommands.ShinyUnicodeCommand;
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
			Shiny.theme().ascii(true);
		}
	}

	@Command(
			name = "unicode",
			description = "Sets unicode mode")

	public static final class ShinyUnicodeCommand implements ShellCommand {
		@Override
		public void run() {
			Shiny.theme().ascii(false);
		}
	}

	@Command(
			name = "square",
			description = "Sets square mode")

	public static final class ShinySquareCommand implements ShellCommand {
		@Override
		public void run() {
			Shiny.theme().square(true);
		}
	}

	@Command(
			name = "rounded",
			description = "Sets rounded mode")

	public static final class ShinyRoundedCommand implements ShellCommand {
		@Override
		public void run() {
			Shiny.theme().square(false);
		}
	}
}
