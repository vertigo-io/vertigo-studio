package io.vertigo.shell.systems.core.commands.uptime;

import java.time.Duration;
import java.time.Instant;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ShellContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import picocli.CommandLine.Command;

@Command(name = "uptime", description = "Displays the shell uptime.")
public class UptimeCommand implements ShellCommand {

	@Override
	public ShinyComponent build() {
		final Duration uptime = Duration.between(ShellContext.startTime, Instant.now());
		var text = String.format("Uptime: %d minutes, %d seconds%n",
				uptime.toMinutes(),
				uptime.toSecondsPart());
		return Shiny.paragraph()
				.withText(text).build();
	}

}
