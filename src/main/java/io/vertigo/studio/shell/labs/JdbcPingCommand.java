package io.vertigo.studio.shell.labs;

import java.sql.SQLException;

import com.beust.jcommander.Parameters;

@Parameters(commandNames = "jdbc-connect", commandDescription = "Connects to a JDBC database")
public final class JdbcPingCommand implements Runnable {
	//	
	//	@Parameter(names = { "--help", "-h" }, description = "Show help for connect command", help = true)
	//	private boolean help;

	public void run() {
		//		if (help) {
		//			// JCommander will print usage
		//			return;
		//		}
		if (JdbcContext.connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try {
			long startTime = System.nanoTime();
			JdbcContext.connection.isValid(2);
			long endTime = System.nanoTime();
			System.out.println("Ping: " + (endTime - startTime) / 1_000_000.0 + " ms");
		} catch (SQLException e) {
			System.out.println("Ping error: " + e.getMessage());
		}
	}
}
