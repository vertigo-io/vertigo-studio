package io.vertigo.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.vertigo.shell.systems.core.commands.exit.ExitCommand;
import io.vertigo.shell.systems.core.commands.help.HelpCommand;
import io.vertigo.shell.systems.core.commands.history.HistoryCommand;
import io.vertigo.shell.systems.core.commands.ip.IpCommand;
import io.vertigo.shell.systems.core.commands.uptime.UptimeCommand;
import io.vertigo.shell.systems.db.commands.DbCommands;
import io.vertigo.shell.systems.env.commands.EnvCommands;
import io.vertigo.shell.systems.file.commands.cd.FileCdCommand;
import io.vertigo.shell.systems.file.commands.ls.FileLsCommand;
import io.vertigo.shell.systems.file.commands.pwd.FilePwdCommand;
import io.vertigo.shell.systems.java.commands.JavaCommands;
import io.vertigo.shell.systems.photo.commands.PhotoCommands;
import io.vertigo.shell.systems.shiny.commands.ShinyCommands;
import io.vertigo.shell.systems.studio.commands.StudioCommands;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
		name = "shell",
		mixinStandardHelpOptions = true,
		version = "Shell 1.0",
		description = "Vertigo Shell.",
		subcommands = {
				//--- Db commands
				DbCommands.class,
				//--- Java commands
				JavaCommands.class,
				//--- Studio commands 
				StudioCommands.class,
				//--- Shiny commands
				ShinyCommands.class,
				//--- Photo commands
				PhotoCommands.class,
				//=================
				//--- Core commands
				//=================
				//--- Env commands
				EnvCommands.class,
				//--- Simple commands
				ExitCommand.class,
				UptimeCommand.class,
				HistoryCommand.class,
				IpCommand.class,
				HelpCommand.class,
				//-----File Commands
				FileCdCommand.class,
				FileLsCommand.class,
				FilePwdCommand.class
		})
public final class Shell implements Runnable {
	//	private final Instant startTime = Instant.now();
	private final List<String> history = new ArrayList<>();
	private final CommandLine cmd;

	public Shell() {
		cmd = new CommandLine(this);

	}

	public static void main(final String[] args) {
		new Shell().run();
	}

	@Override
	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("MiniShell started. Type 'help' for a list of commands.");

			while (true) {
				System.out.print("> ");
				final String line = scanner.nextLine().trim();
				if (line.isEmpty()) {
					continue;
				}

				if (line.startsWith("!")) {
					runFromHistory(line);
				} else {
					handleCommand(splitArguments(line));
				}
				history.add(line);
			}
		}
	}

	public static String[] splitArguments(final String input) {
		final List<String> result = new ArrayList<>();
		// Pattern pour matcher les chaînes entre guillemets ou les tokens simples
		final Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
		final Matcher matcher = pattern.matcher(input.trim());

		while (matcher.find()) {
			if (matcher.group(1) != null) {
				// Contenu entre guillemets
				result.add(matcher.group(1));
			} else {
				// Token sans guillemets
				result.add(matcher.group(2));
			}
		}

		return result.toArray(new String[0]);
	}

	private void runFromHistory(final String line) {
		try {
			final int index = Integer.parseInt(line.substring(1)) - 1;
			if (index >= 0 && index < history.size()) {
				final String historicCmd = history.get(index);
				System.out.println("\u2192 " + historicCmd);
				handleCommand(historicCmd.split("\\s+"));
			} else {
				System.out.println("Invalid history index.");
			}
		} catch (final NumberFormatException e) {
			System.out.println("Invalid format. Use !n to replay command n.");
		}
	}

	private void handleCommand(final String[] args) {
		cmd.execute(args);
	}

}
