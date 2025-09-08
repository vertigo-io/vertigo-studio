package io.vertigo.shell.systems.core.commands.uptime;

import java.time.Duration;
import java.time.Instant;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ShellContext;
import picocli.CommandLine.Command;

@Command(name = "uptime", description = "Displays the shell uptime.")
public class UptimeCommand implements ShellCommand {

	@Override
	public void run() {
		final Duration uptime = Duration.between(ShellContext.startTime, Instant.now());
		writer().printf("Uptime: %d minutes, %d seconds%n",
				uptime.toMinutes(),
				uptime.toSecondsPart());
	}

}
