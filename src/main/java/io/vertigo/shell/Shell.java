package io.vertigo.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.commands.CleanCommand;
import io.vertigo.shell.commands.ExitCommand;
import io.vertigo.shell.commands.GenerateCommand;
import io.vertigo.shell.commands.HelpCommand;
import io.vertigo.shell.commands.UptimeCommand;
import io.vertigo.shell.commands.WatchCommand;
import io.vertigo.shell.labs.HistoryCommand;
import io.vertigo.shell.labs.ListSketchesCommand;
import io.vertigo.shell.labs.ListSourcesCommand;
import io.vertigo.shell.labs.WhoCommand;
import io.vertigo.shell.labs.db.DbCommands;
import io.vertigo.studio.tools.VertigoStudioMda;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
		name = "shell",
		mixinStandardHelpOptions = true,
		version = "Shell 1.0",
		description = "Vertigo Shell.",
		subcommands = {
				CleanCommand.class,
				GenerateCommand.class,
				WatchCommand.class,
				ExitCommand.class,
				UptimeCommand.class,
				HistoryCommand.class,
				DbCommands.class,
				ListSketchesCommand.class,
				ListSourcesCommand.class,
				WhoCommand.class,
				HelpCommand.class
		})
public final class Shell implements Runnable {
	//	private final Instant startTime = Instant.now();
	private final List<String> history = new ArrayList<>();
	private final CommandLine cmd;

	public Shell(String notebookConfigYaml) {
		ShellContext.notebookConfig = VertigoStudioMda.loadNotebookConfig(notebookConfigYaml);
		//---
		cmd = new CommandLine(this);
		//		cmd.getSubcommands().get("history").setCommand(new HistoryCommand(history));

	}

	public static void main(String[] args) {
		Assertion.check()
				.isTrue(args.length == 1, "expected the yam notebookConfig");
		//--
		final String notebookConfigYaml = args[0];
		new Shell(notebookConfigYaml).run();
	}

	@Override
	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("MiniShell started. Type 'help' for a list of commands.");

			while (true) {
				System.out.print("> ");
				String line = scanner.nextLine().trim();
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

	public static String[] splitArguments(String input) {
		List<String> result = new ArrayList<>();
		// Pattern pour matcher les chaînes entre guillemets ou les tokens simples
		Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");
		Matcher matcher = pattern.matcher(input.trim());

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

	private void runFromHistory(String line) {
		try {
			int index = Integer.parseInt(line.substring(1)) - 1;
			if (index >= 0 && index < history.size()) {
				String historicCmd = history.get(index);
				System.out.println("\u2192 " + historicCmd);
				handleCommand(historicCmd.split("\\s+"));
			} else {
				System.out.println("Invalid history index.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid format. Use !n to replay command n.");
		}
	}

	private void handleCommand(String[] args) {
		cmd.execute(args);
	}

}
