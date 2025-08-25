package io.vertigo.shell.labs.Jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.shiny.ShinyColors;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "connect", description = "Connects to a JDBC database")
public final class JdbcConnectCommand implements ShellCommand {
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
		if (JdbcContext.connection != null) {
			System.err.println("Already connected. Disconnect first.");
			return;
		}
		try {
			if (url == null) {
				url = JdbcContext.secrets.getProperty("db.url");
			}

			final Properties props = new Properties();
			if (user != null) {
				props.setProperty("user", user);
			} else {
				props.setProperty("user", JdbcContext.secrets.getProperty("db.user"));
			}

			if (password != null) {
				props.setProperty("password", password);
			} else {
				props.setProperty("password", JdbcContext.secrets.getProperty("db.password"));
			}
			JdbcContext.connection = DriverManager.getConnection(url, props);
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
