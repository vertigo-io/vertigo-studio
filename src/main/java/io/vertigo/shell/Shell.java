package io.vertigo.shell;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.commands.CleanCommand;
import io.vertigo.shell.commands.ExitCommand;
import io.vertigo.shell.commands.GenerateCommand;
import io.vertigo.shell.commands.HelpCommand;
import io.vertigo.shell.commands.UptimeCommand;
import io.vertigo.shell.commands.WatchCommand;
import io.vertigo.shell.labs.ClearCommand;
import io.vertigo.shell.labs.HistoryCommand;
import io.vertigo.shell.labs.JdbcConnectCommand;
import io.vertigo.shell.labs.JdbcDisconnectCommand;
import io.vertigo.shell.labs.JdbcSqlCommand;
import io.vertigo.shell.labs.ListSketchesCommand;
import io.vertigo.shell.labs.ListSourcesCommand;
import io.vertigo.shell.labs.WhoCommand;
import io.vertigo.studio.tools.VertigoStudioMda;

public final class Shell {
	private final Instant startTime = Instant.now();
	private boolean running = true;
	private final List<String> history = new ArrayList<>();
	private final JCommander jc;

	public Shell(String notebookConfigYaml) {
		ShellContext.notebookConfig = VertigoStudioMda.loadNotebookConfig(notebookConfigYaml);
		//---
		JCommander.Builder builder = JCommander.newBuilder()
				.addCommand(new CleanCommand())
				.addCommand(new GenerateCommand())
				.addCommand(new WatchCommand())
				.addCommand(new ExitCommand(this))
				.addCommand(new UptimeCommand(startTime))
				.addCommand(new HistoryCommand(history))
				//--- experimental commands 
				.addCommand(new JdbcConnectCommand())
				.addCommand(new JdbcSqlCommand())
				.addCommand(new JdbcDisconnectCommand())
				.addCommand(new ClearCommand())
				.addCommand(new ListSketchesCommand())
				.addCommand(new ListSourcesCommand())
				//ip ??
				.addCommand(new WhoCommand());

		jc = builder.build();
		builder.addCommand("help", new HelpCommand(jc));
	}

	public static void main(String[] args) {
		Assertion.check()
				.isTrue(args.length == 1, "expected the yam notebookConfig");
		//--
		final String notebookConfigYaml = args[0];
		new Shell(notebookConfigYaml).run();
	}

	public void run() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("MiniShell started. Type 'help' for a list of commands.");

			while (running) {
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
				String cmd = history.get(index);
				System.out.println("→ " + cmd);
				handleCommand(cmd.split("\\s+"));
			} else {
				System.out.println("Invalid history index.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid format. Use !n to replay command n.");
		}
	}

	private void handleCommand(String[] args) {
		String parsedCommand = null;
		try {
			jc.parse(args);
			parsedCommand = jc.getParsedCommand();
		} catch (ParameterException e) {
			System.err.println("Unknown command: " + args[0]);
			return;
		}
		if (parsedCommand != null) {
			ShellCommand command = null;
			try {
				command = (ShellCommand) jc.getCommands().get(parsedCommand).getObjects().get(0);
				command.run();
			} catch (Exception e) {
				System.err.println("Error during execution command: " + parsedCommand);
			}
			if (command != null) {
				command.reset();
			}
		} else {
			System.err.println("Unknown command: " + args[0]);
			jc.usage();
		}
	}

	public void stop() {
		running = false;
	}
}
