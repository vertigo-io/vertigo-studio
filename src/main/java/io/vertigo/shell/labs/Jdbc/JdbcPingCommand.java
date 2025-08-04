package io.vertigo.shell.labs.Jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "ping", description = "SQL ping database")
public final class JdbcPingCommand implements ShellCommand {
	@Option(names = { "--calls", "-c" }, description = "Number of database calls (default: 1)", defaultValue = "1")
	private int calls;

	@Override
	public void run() {
		if (JdbcContext.connection == null) {
			System.err.println("Not connected. Use 'connect' first.");
			return;
		}

		// Validate number of calls
		if (calls <= 0 && calls > 50) {
			System.err.println("Error: Number of calls must be greater than 0 and lower than 50");
			return;
		}

		final List<Double> pingTimes = new ArrayList<>();

		for (int i = 0; i < calls; i++) {
			ping(pingTimes);
			try {
				//Waiting to avoid DDOS
				Thread.sleep(30);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}

		// Display statistics if calls > 1
		if (calls > 1) {
			double min = pingTimes.stream().min(Double::compare).orElse(0.0);
			double max = pingTimes.stream().max(Double::compare).orElse(0.0);
			double average = pingTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

			System.out.println("Number of calls: " + calls);
			System.out.printf("Min: %.3f ms%n", min);
			System.out.printf("Max: %.3f ms%n", max);
			System.out.printf("Average: %.3f ms%n", average);
		}
	}

	private void ping(final List<Double> pingTimes) {
		long startTime = System.nanoTime();
		try {
			JdbcContext.connection.isValid(2);
			long endTime = System.nanoTime();
			double pingTimeMs = (endTime - startTime) / 1_000_000.0;
			pingTimes.add(pingTimeMs);
			if (calls == 1) {
				System.out.println("Ping: " + pingTimeMs + " ms");
			}
		} catch (SQLException e) {
			System.err.println("Unable to ping database: " + e.getMessage());
			return;
		}
	}
}
