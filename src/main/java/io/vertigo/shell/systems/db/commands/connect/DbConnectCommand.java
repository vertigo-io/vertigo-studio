package io.vertigo.shell.systems.db.commands.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbVar;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shiny.style.ShinyColors;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "connect", description = "Connects to a JDBC database")
public final class DbConnectCommand implements ShellCommand {
	@Option(names = { "--url", "-u" }, description = "JDBC URL")
	private String url;

	@Option(names = { "--user", "-U" }, description = "Database user")
	private String user;

	@Option(names = { "--password", "-P" }, description = "Database password")
	private String password;

	@Option(names = { "--help", "-h" }, usageHelp = true, description = "Show help for connect command")
	private boolean help;

	@Override
	public void run() {
		if (help) {
			// Picocli will print usage
		}
		if (DbContext.isConnected()) {
			System.err.println("Already connected. Disconnect first.");
			return;
		}
		try {
			if (url == null) {
				url = Env.get(DbVar.URL);
			}

			final Properties props = new Properties();
			if (user != null) {
				props.setProperty("user", user);
			} else {
				props.setProperty("user", Env.get(DbVar.USER));
			}

			if (password != null) {
				props.setProperty("password", password);
			} else {
				props.setProperty("password", Env.get(DbVar.PASSWORD));
			}
			final Connection connection = DriverManager.getConnection(url, props);
			connection.setReadOnly(true);
			DbContext.connection(connection);
		} catch (final SQLException e) {
			throw new VSystemException(e, "Failed to connect to database: {0}", e.getMessage());
		}
		System.out.println(ShinyColors.GREEN_BRIGHT + "Successfully connected." + ShinyColors.RESET);
	}

	@Override
	public void reset() {
		url = null;
		user = null;
		password = null;
		help = false;
	}
}
