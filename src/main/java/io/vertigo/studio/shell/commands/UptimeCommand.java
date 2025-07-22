package io.vertigo.studio.shell.commands;

import java.time.Duration;
import java.time.Instant;

import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.Assertion;

@Parameters(commandNames = "uptime", commandDescription = "Displays the shell uptime.")
public class UptimeCommand implements Runnable {

	private final Instant startTime;

	public UptimeCommand(Instant startTime) {
		Assertion.check().isNotNull(startTime);
		//---
		this.startTime = startTime;
	}

	public void run() {
		final Duration uptime = Duration.between(startTime, Instant.now());
		System.out.printf("Uptime: %d minutes, %d seconds%n",
				uptime.toMinutes(),
				uptime.toSecondsPart());
	}
}
