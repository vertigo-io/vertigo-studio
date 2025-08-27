package io.vertigo.shell.commands.db.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.commands.db.DbContext;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.progressbar.ShinyProgressBar;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "ping", description = "SQL ping database")
public final class JdbcPingCommand implements ShellCommand {
	@Option(names = { "--calls", "-c" }, description = "Number of database calls (default: 1)", defaultValue = "5")
	private int calls;

	@Override
	public void run() {
		// Validate number of calls
		if (calls <= 0 && calls > 50) {
			System.err.println("Error: Number of calls must be greater than 0 and lower than 50");
			return;
		}

		final List<Double> pingTimes = new ArrayList<>();

		final ShinyProgressBar progressBar = Shiny.progressBar().total(calls);
		System.out.println();
		for (int i = 0; i < calls; i++) {
			ping(pingTimes);
			progressBar.setProgress(i + 1);
			progressBar.print();
			try {
				//Waiting to avoid DDOS
				Thread.sleep(30);
			} catch (final InterruptedException e) {
				//e.printStackTrace();
			}

		}
		progressBar.finish();

		// Display statistics if calls > 1
		if (calls > 1) {
			final double min = pingTimes.stream().min(Double::compare).orElse(0.0);
			final double max = pingTimes.stream().max(Double::compare).orElse(0.0);
			final double average = pingTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

			System.out.println("Number of calls: " + calls);
			System.out.printf("Min: %.3f ms%n", min);
			System.out.printf("Max: %.3f ms%n", max);
			System.out.printf("Average: %.3f ms%n", average);
		}
	}

	private void ping(final List<Double> pingTimes) {
		final long startTime = System.nanoTime();
		try {
			DbContext.connection().isValid(2);
			final long endTime = System.nanoTime();
			final double pingTimeMs = (endTime - startTime) / 1_000_000.0;
			pingTimes.add(pingTimeMs);
			if (calls == 1) {
				System.out.println("Ping: " + pingTimeMs + " ms");
			}
		} catch (final SQLException e) {
			System.err.println("Unable to ping database: " + e.getMessage());
			return;
		}
	}
}
