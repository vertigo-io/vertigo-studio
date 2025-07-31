package io.vertigo.studio.shell.labs;

import java.sql.SQLException;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.VSystemException;

@Parameters(commandNames = "jdbc-disconnect", commandDescription = "Disconnects from the JDBC database")
public final class JdbcDisconnectCommand implements Runnable {
	@Parameter(names = { "--help", "-h" }, description = "Show help for disconnect command", help = true)
	private boolean help;

	public void run() {
		if (help) {
			// JCommander will print usage
			return;
		}
		if (JdbcContext.connection == null) {
			System.out.println("Not connected.");
			return;
		}
		try {
			JdbcContext.connection.close();
			JdbcContext.connection = null;
			System.out.println("Disconnected.");
		} catch (final SQLException e) {
			throw new VSystemException(e, "Failed to disconnect from database: {0}", e.getMessage());
		}
	}
}
