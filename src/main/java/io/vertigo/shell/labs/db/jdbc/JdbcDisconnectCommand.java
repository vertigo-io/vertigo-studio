package io.vertigo.shell.labs.db.jdbc;

import java.sql.SQLException;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "disconnect", description = "Disconnects from the JDBC database")
public final class JdbcDisconnectCommand implements ShellCommand {
	@Option(names = { "--help", "-h" }, usageHelp = true, description = "Show help for disconnect command")
	private boolean help;

	@Override
	public void run() {
		if (help) {
			// Picocli will print usage
			return;
		}
		try {
			DbContext.connection().close();
			System.out.println("Disconnected.");
		} catch (final SQLException e) {
			throw new VSystemException(e, "Failed to disconnect from database: {0}", e.getMessage());
		}
	}

	@Override
	public void reset() {
		help = false;
	}
}
