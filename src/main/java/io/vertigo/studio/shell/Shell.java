package io.vertigo.studio.shell;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.shell.commands.CleanCommand;
import io.vertigo.studio.shell.commands.ExitCommand;
import io.vertigo.studio.shell.commands.GenerateCommand;
import io.vertigo.studio.shell.commands.HelpCommand;
import io.vertigo.studio.shell.commands.UptimeCommand;
import io.vertigo.studio.shell.commands.WatchCommand;
import io.vertigo.studio.shell.labs.ClearCommand;
import io.vertigo.studio.shell.labs.HelloCommand;
import io.vertigo.studio.shell.labs.HistoryCommand;
import io.vertigo.studio.shell.labs.WhoCommand;
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
				.addCommand("clean", new CleanCommand())
				.addCommand("generate", new GenerateCommand())
				.addCommand("watch", new WatchCommand())
				//--- experimental commands 
				.addCommand("clear", new ClearCommand())
				.addCommand("exit", new ExitCommand(this))
				.addCommand("generate", new GenerateCommand())
				.addCommand("hello", new HelloCommand())
				.addCommand("history", new HistoryCommand(history))
				.addCommand("uptime", new UptimeCommand(startTime))
				.addCommand("who", new WhoCommand());
		//.addCommand("list-modules", new ListSketchesCommand());
		//.addCommand("list-sketches", new ListSketchesCommand());
		//show
		//stats

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

				history.add(line);

				if (line.startsWith("!")) {
					runFromHistory(line);
				} else {
					handleCommand(line.split("\\s+"));
				}
			}
		}
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
		try {
			jc.parse(args);
			String parsedCommand = jc.getParsedCommand();
			if (parsedCommand != null) {
				Runnable command = (Runnable) jc.getCommands().get(parsedCommand).getObjects().get(0);
				command.run();
			} else {
				System.out.println("Unknown command: " + args[0]);
				jc.usage();
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			//jc.usage(jc.getParsedCommand());
		}
	}

	public void stop() {
		running = false;
	}
}
