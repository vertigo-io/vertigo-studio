package io.vertigo.studio.shell.labs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;

import io.vertigo.core.lang.VSystemException;

public class JdbcCommand {

	private static Connection connection;

	@Parameters(commandNames = "connect", commandDescription = "Connects to a JDBC database")
	public static class ConnectCommand {
		@Parameter(names = { "--url", "-u" }, description = "JDBC URL", required = true)
		private String url;

		@Parameter(names = { "--user", "-U" }, description = "Database user")
		private String user;

		@Parameter(names = { "--password", "-P" }, description = "Database password")
		private String password;

		@Parameter(names = { "--help", "-h" }, description = "Show help for connect command", help = true)
		private boolean help;

		public void execute() {
			if (help) {
				// JCommander will print usage
				return;
			}
			if (connection != null) {
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
				connection = DriverManager.getConnection(url, props);
				System.out.println("Connected to " + url);
			} catch (final SQLException e) {
				throw new VSystemException(e, "Failed to connect to database: {0}", e.getMessage());
			}
		}
	}

	@Parameters(commandNames = "disconnect", commandDescription = "Disconnects from the JDBC database")
	public static class DisconnectCommand {
		@Parameter(names = { "--help", "-h" }, description = "Show help for disconnect command", help = true)
		private boolean help;

		public void execute() {
			if (help) {
				// JCommander will print usage
				return;
			}
			if (connection == null) {
				System.out.println("Not connected.");
				return;
			}
			try {
				connection.close();
				connection = null;
				System.out.println("Disconnected.");
			} catch (final SQLException e) {
				throw new VSystemException(e, "Failed to disconnect from database: {0}", e.getMessage());
			}
		}
	}

	@Parameters(commandNames = "sql", commandDescription = "Executes a SQL query")
	public static class SqlCommand {
		@Parameter(names = { "--query", "-q" }, description = "SQL query to execute", required = true)
		private String query;

		@Parameter(names = { "--help", "-h" }, description = "Show help for sql command", help = true)
		private boolean help;

		public void execute() {
			if (help) {
				// JCommander will print usage
				return;
			}
			if (connection == null) {
				System.out.println("Not connected. Use 'connect' first.");
				return;
			}
			try (Statement stmt = connection.createStatement()) {
				if (query.trim().toLowerCase().startsWith("select")) {
					try (ResultSet rs = stmt.executeQuery(query)) {
						printResultSet(rs);
					}
				} else {
					final int rowsAffected = stmt.executeUpdate(query);
					System.out.println(rowsAffected + " row(s) affected.");
				}
			} catch (final SQLException e) {
				throw new VSystemException(e, "Failed to execute SQL query: {0}", e.getMessage());
			}
		}

		private void printResultSet(final ResultSet rs) throws SQLException {
			final ResultSetMetaData rsmd = rs.getMetaData();
			final int columnsNumber = rsmd.getColumnCount();
			for (int i = 1; i <= columnsNumber; i++) {
				System.out.print(rsmd.getColumnName(i) + (i == columnsNumber ? "" : "	"));
			}
			System.out.println();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					System.out.print(rs.getString(i) + (i == columnsNumber ? "" : "	"));
				}
				System.out.println();
			}
		}
	}

	@ParametersDelegate
	private ConnectCommand connectCommand = new ConnectCommand();

	@ParametersDelegate
	private DisconnectCommand disconnectCommand = new DisconnectCommand();

	@ParametersDelegate
	private SqlCommand sqlCommand = new SqlCommand();

	public ConnectCommand getConnectCommand() {
		return connectCommand;
	}

	public DisconnectCommand getDisconnectCommand() {
		return disconnectCommand;
	}

	public SqlCommand getSqlCommand() {
		return sqlCommand;
	}
}
