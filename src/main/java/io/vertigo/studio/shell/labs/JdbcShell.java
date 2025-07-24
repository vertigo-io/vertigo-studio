package io.vertigo.studio.shell.labs;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class JdbcShell {
	private Connection connection = null;
	private Scanner scanner = new Scanner(System.in);

	// Command: connect
	static class ConnectCommand {
		@Parameter(names = { "--url" }, description = "Database URL", required = true)
		String url;
		@Parameter(names = { "--user" }, description = "Username", required = true)
		String username;
		@Parameter(names = { "--password" }, description = "Password", required = true)
		String password;
	}

	// Command: query
	static class QueryCommand {
		@Parameter(description = "SQL query to execute", required = true)
		List<String> sql = new ArrayList<>();
	}

	// Command: describe
	static class DescribeCommand {
		@Parameter(description = "Table name to describe", required = true)
		String tableName;
	}

	// Command: indexes
	static class IndexesCommand {
		@Parameter(description = "Table name to list indexes for", required = true)
		String tableName;
	}

	public void startShell() {
		System.out.println("JDBC Shell - Type 'help' to see available commands.");
		JCommander jc = JCommander.newBuilder()
				.addObject(new Object())
				.programName("JdbcShell")
				.build();

		while (true) {
			System.out.print("> ");
			String input = scanner.nextLine().trim();
			if (input.equalsIgnoreCase("exit")) {
				disconnect();
				break;
			}
			processCommand(input, jc);
		}
	}

	private void processCommand(String input, JCommander jc) {
		try {
			String[] parts = input.split("\\s+", 2);
			String command = parts[0].toLowerCase();
			String args = parts.length > 1 ? parts[1] : "";

			switch (command) {
				case "connect":
					ConnectCommand connectCmd = new ConnectCommand();
					jc = JCommander.newBuilder().addObject(connectCmd).build();
					jc.parse(args.split("\\s+"));
					connect(connectCmd.url, connectCmd.username, connectCmd.password);
					break;
				case "disconnect":
					disconnect();
					break;
				case "query":
					QueryCommand queryCmd = new QueryCommand();
					jc = JCommander.newBuilder().addObject(queryCmd).build();
					jc.parse(args.split("\\s+"));
					executeQuery(String.join(" ", queryCmd.sql));
					break;
				case "ping":
					ping();
					break;
				case "tables":
					listTables();
					break;
				case "describe":
					DescribeCommand describeCmd = new DescribeCommand();
					jc = JCommander.newBuilder().addObject(describeCmd).build();
					jc.parse(args.split("\\s+"));
					describeTable(describeCmd.tableName);
					break;
				case "indexes":
					IndexesCommand indexesCmd = new IndexesCommand();
					jc = JCommander.newBuilder().addObject(indexesCmd).build();
					jc.parse(args.split("\\s+"));
					listIndexes(indexesCmd.tableName);
					break;
				case "schemas":
					listSchemas();
					break;
				case "explain":
					QueryCommand explainCmd = new QueryCommand();
					jc = JCommander.newBuilder().addObject(explainCmd).build();
					jc.parse(args.split("\\s+"));
					explainQuery(String.join(" ", explainCmd.sql));
					break;
				case "stats":
					showStats();
					break;
				case "help":
					showHelp();
					break;
				default:
					System.out.println("Unknown command. Type 'help' for the list of commands.");
			}
		} catch (ParameterException e) {
			System.out.println("Syntax error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void connect(String url, String username, String password) {
		if (connection != null) {
			System.out.println("Already connected. Disconnect first.");
			return;
		}
		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Successfully connected to " + url);
		} catch (SQLException e) {
			System.out.println("Connection error: " + e.getMessage());
		}
	}

	private void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Successfully disconnected.");
			} catch (SQLException e) {
				System.out.println("Disconnection error: " + e.getMessage());
			} finally {
				connection = null;
			}
		} else {
			System.out.println("No active connection.");
		}
	}

	private void executeQuery(String sql) {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try (Statement stmt = connection.createStatement()) {
			long startTime = System.currentTimeMillis();
			if (sql.trim().toLowerCase().startsWith("select")) {
				ResultSet rs = stmt.executeQuery(sql);
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();

				for (int i = 1; i <= columnCount; i++) {
					System.out.print(metaData.getColumnName(i) + "\t");
				}
				System.out.println("\n" + "-".repeat(columnCount * 10));

				while (rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						System.out.print(rs.getString(i) + "\t");
					}
					System.out.println();
				}
			} else {
				int rowsAffected = stmt.executeUpdate(sql);
				System.out.println("Rows affected: " + rowsAffected);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Execution time: " + (endTime - startTime) + " ms");
		} catch (SQLException e) {
			System.out.println("SQL error: " + e.getMessage());
		}
	}

	private void ping() {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try {
			long startTime = System.nanoTime();
			connection.isValid(2);
			long endTime = System.nanoTime();
			System.out.println("Ping: " + (endTime - startTime) / 1_000_000.0 + " ms");
		} catch (SQLException e) {
			System.out.println("Ping error: " + e.getMessage());
		}
	}

	private void listTables() {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });
			System.out.println("Tables in the database:");
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void describeTable(String tableName) {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getColumns(null, null, tableName, "%");
			System.out.println("Structure of table " + tableName + ":");
			System.out.println("Name\tType\tSize\tNullable");
			while (rs.next()) {
				System.out.printf("%s\t%s\t%s\t%s%n",
						rs.getString("COLUMN_NAME"),
						rs.getString("TYPE_NAME"),
						rs.getString("COLUMN_SIZE"),
						rs.getString("IS_NULLABLE"));
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void listIndexes(String tableName) {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getIndexInfo(null, null, tableName, false, false);
			System.out.println("Indexes of table " + tableName + ":");
			System.out.println("Name\tColumns\tType");
			while (rs.next()) {
				System.out.printf("%s\t%s\t%s%n",
						rs.getString("INDEX_NAME"),
						rs.getString("COLUMN_NAME"),
						rs.getString("TYPE"));
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void listSchemas() {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getSchemas();
			System.out.println("Schemas in the database:");
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_SCHEM"));
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void explainQuery(String sql) {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery("EXPLAIN " + sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			for (int i = 1; i <= columnCount; i++) {
				System.out.print(metaData.getColumnName(i) + "\t");
			}
			System.out.println("\n" + "-".repeat(columnCount * 10));

			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void showStats() {
		if (connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });
			System.out.println("Table statistics:");
			System.out.println("Name\tRow Count");
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				try (Statement stmt = connection.createStatement()) {
					ResultSet countRs = stmt.executeQuery("SELECT COUNT(*) AS count FROM " + tableName);
					if (countRs.next()) {
						System.out.printf("%s\t%s%n", tableName, countRs.getString("count"));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void showHelp() {
		System.out.println("Available commands:");
		System.out.println("connect --url <url> --user <username> --password <password> - Connect to a database");
		System.out.println("  Example: connect --url jdbc:mysql://localhost:3306/mydb --user root --password pass");
		System.out.println("disconnect - Disconnect from the database");
		System.out.println("query <sql> - Execute an SQL query");
		System.out.println("ping - Measure connection latency");
		System.out.println("tables - List all tables in the database");
		System.out.println("describe <table> - Show the structure of a table");
		System.out.println("indexes <table> - List indexes of a table");
		// Search for: schemas - List schemas in the database
		System.out.println("explain <sql> - Show the execution plan of a query");
		System.out.println("stats - Show table statistics");
		System.out.println("exit - Quit the shell");
		System.out.println("help - Show this help");
	}

	public static void main(String[] args) {
		JdbcShell shell = new JdbcShell();
		shell.startShell();
	}
}
