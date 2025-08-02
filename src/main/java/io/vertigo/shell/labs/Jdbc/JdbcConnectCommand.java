package io.vertigo.shell.labs.Jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;

@Parameters(commandNames = "jdbc-connect", commandDescription = "Connects to a JDBC database")
public final class JdbcConnectCommand implements ShellCommand {
	@Parameter(names = { "--url", "-u" }, description = "JDBC URL")
	private String url;

	@Parameter(names = { "--user", "-U" }, description = "Database user")
	private String user;

	@Parameter(names = { "--password", "-P" }, description = "Database password")
	private String password;

	@Parameter(names = { "--help", "-h" }, description = "Show help for connect command", help = true)
	private boolean help;

	private Properties secrets = new Properties();

	public JdbcConnectCommand() {
		String userHome = System.getProperty("user.home");
		String filePath = userHome + File.separator + "vortex.txt";

		System.out.println("loading secrets");
		try (FileInputStream fis = new FileInputStream(filePath)) {
			secrets.load(fis);
			System.out.println("secrets found :" + secrets.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
			if (url == null) {
				url = secrets.getProperty("db.url");
			}

			final Properties props = new Properties();
			if (user != null) {
				props.setProperty("user", user);
			} else {
				props.setProperty("user", secrets.getProperty("db.user"));
			}

			if (password != null) {
				props.setProperty("password", password);
			} else {
				props.setProperty("password", secrets.getProperty("db.password"));
			}
			JdbcContext.connection = DriverManager.getConnection(url, props);
		} catch (final SQLException e) {
			throw new VSystemException(e, "Failed to connect to database: {0}", e.getMessage());
		}
		System.out.println("Successfully connected.");
	}

	@Override
	public void reset() {
		url = null;
		user = null;
		password = null;
		help = false;
	}
}
