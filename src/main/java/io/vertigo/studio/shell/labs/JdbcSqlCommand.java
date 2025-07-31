package io.vertigo.studio.shell.labs;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.VSystemException;

@Parameters(commandNames = "jdbc-sql", commandDescription = "Executes a SQL query")
public final class JdbcSqlCommand implements Runnable {
	@Parameter(names = { "--query", "-q" }, description = "SQL query to execute")
	private String query;

	@Parameter(names = { "--tables", "-t" }, description = "SQL list tables")
	private boolean tables;

	@Parameter(names = { "--table", "-T" }, description = "SQL describe table")
	private String tableName;

	@Parameter(names = { "--help", "-h" }, description = "Show help for sql command")
	private boolean help;

	public void run() {
		if (help) {
			// JCommander will print usage
			return;
		}
		if (JdbcContext.connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		if (query != null) {
			executeQuery();
		}
		if (tables) {
			listTables();
		}
		if (tableName != null) {
			describeTable();
		}
	}

	private void executeQuery() {
		try (Statement stmt = JdbcContext.connection.createStatement()) {
			if (query.trim().toLowerCase().startsWith("select")) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					printResultSet(rs);
				}
			} else {
				final int rowsAffected = stmt.executeUpdate(query);
				System.out.println(rowsAffected + " row(s) affected.");
			}
			query = null;
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

	private void listTables() {
		try {
			DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });
			System.out.println("Tables in the database:");
			while (rs.next()) {
				System.out.println(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void describeTable() {
		try {
			DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
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
	//	private void showHelp() {
	//		System.out.println("Available commands:");
	//		System.out.println("connect --url <url> --user <username> --password <password> - Connect to a database");
	//		System.out.println("  Example: connect --url jdbc:mysql://localhost:3306/mydb --user root --password pass");
	//		System.out.println("disconnect - Disconnect from the database");
	//		System.out.println("query <sql> - Execute an SQL query");
	//		System.out.println("ping - Measure connection latency");
	//		System.out.println("tables - List all tables in the database");
	//		System.out.println("describe <table> - Show the structure of a table");
	//		System.out.println("indexes <table> - List indexes of a table");
	//		// Search for: schemas - List schemas in the database
	//		System.out.println("explain <sql> - Show the execution plan of a query");
	//		System.out.println("stats - Show table statistics");
	//		System.out.println("exit - Quit the shell");
	//		System.out.println("help - Show this help");

	//	private void listIndexes(String tableName) {
	//		try {
	//			DatabaseMetaData metaData = connection.getMetaData();
	//			ResultSet rs = metaData.getIndexInfo(null, null, tableName, false, false);
	//			System.out.println("Indexes of table " + tableName + ":");
	//			System.out.println("Name\tColumns\tType");
	//			while (rs.next()) {
	//				System.out.printf("%s\t%s\t%s%n",
	//						rs.getString("INDEX_NAME"),
	//						rs.getString("COLUMN_NAME"),
	//						rs.getString("TYPE"));
	//			}
	//		} catch (SQLException e) {
	//			System.out.println("Error: " + e.getMessage());
	//		}
	//	}
	//
	//	private void listSchemas() {
	//		try {
	//			DatabaseMetaData metaData = connection.getMetaData();
	//			ResultSet rs = metaData.getSchemas();
	//			System.out.println("Schemas in the database:");
	//			while (rs.next()) {
	//				System.out.println(rs.getString("TABLE_SCHEM"));
	//			}
	//		} catch (SQLException e) {
	//			System.out.println("Error: " + e.getMessage());
	//		}
	//	}
	//
	//	private void explainQuery(String sql) {
	//		try (Statement stmt = connection.createStatement()) {
	//			ResultSet rs = stmt.executeQuery("EXPLAIN " + sql);
	//			ResultSetMetaData metaData = rs.getMetaData();
	//			int columnCount = metaData.getColumnCount();
	//
	//			for (int i = 1; i <= columnCount; i++) {
	//				System.out.print(metaData.getColumnName(i) + "\t");
	//			}
	//			System.out.println("\n" + "-".repeat(columnCount * 10));
	//
	//			while (rs.next()) {
	//				for (int i = 1; i <= columnCount; i++) {
	//					System.out.print(rs.getString(i) + "\t");
	//				}
	//				System.out.println();
	//			}
	//		} catch (SQLException e) {
	//			System.out.println("Error: " + e.getMessage());
	//		}
	//	}
	//
	//	private void showStats() {
	//		try {
	//			DatabaseMetaData metaData = connection.getMetaData();
	//			ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });
	//			System.out.println("Table statistics:");
	//			System.out.println("Name\tRow Count");
	//			while (rs.next()) {
	//				String tableName = rs.getString("TABLE_NAME");
	//				try (Statement stmt = connection.createStatement()) {
	//					ResultSet countRs = stmt.executeQuery("SELECT COUNT(*) AS count FROM " + tableName);
	//					if (countRs.next()) {
	//						System.out.printf("%s\t%s%n", tableName, countRs.getString("count"));
	//					}
	//				}
	//			}
	//		} catch (SQLException e) {
	//			System.out.println("Error: " + e.getMessage());
	//		}
	//	}

}
