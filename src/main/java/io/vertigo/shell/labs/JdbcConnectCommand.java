package io.vertigo.shell.labs;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.VSystemException;

@Parameters(commandNames = "jdbc-connect", commandDescription = "Connects to a JDBC database")
public final class JdbcConnectCommand implements Runnable {
	@Parameter(names = { "--url", "-u" }, description = "JDBC URL")
	private String url;

	@Parameter(names = { "--user", "-U" }, description = "Database user")
	private String user;

	@Parameter(names = { "--password", "-P" }, description = "Database password")
	private String password;

	@Parameter(names = { "--help", "-h" }, description = "Show help for connect command", help = true)
	private boolean help;

	public void run() {
		if (help) {
			// JCommander will print usage
			return;
		}
		if (JdbcContext.connection != null) {
			System.out.println("Already connected. Disconnect first.");
			return;
		}
		try {
			final Properties props = new Properties();
			if (user != null) {
				props.setProperty("user", user);
			}
			if (password != null) {
				props.setProperty("password", password);
			}
			JdbcContext.connection = DriverManager.getConnection(url, props);
			System.out.println("Connected to " + url);
		} catch (final SQLException e) {
			throw new VSystemException(e, "Failed to connect to database: {0}", e.getMessage());
		}
	}
}
