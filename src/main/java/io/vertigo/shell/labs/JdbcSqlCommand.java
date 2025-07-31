package io.vertigo.shell.labs;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellUtil;

@Parameters(commandNames = "jdbc-sql", commandDescription = "Executes a SQL query")
public final class JdbcSqlCommand implements Runnable {
	@Parameter(names = { "--query", "-q" }, description = "SQL query to execute")
	private String query;

	@Parameter(names = { "--tables", "-t" }, description = "SQL list tables")
	private boolean tables;

	@Parameter(names = { "--ping", "-p" }, description = "SQL ping database")
	private boolean ping;

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
		if (ping) {
			ping();
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

		// Récupération des en-têtes
		String[] headers = new String[columnsNumber];
		for (int i = 1; i <= columnsNumber; i++) {
			headers[i - 1] = rsmd.getColumnName(i);
		}

		// Récupération des données
		List<String[]> rowsList = new ArrayList<>();
		while (rs.next()) {
			String[] row = new String[columnsNumber];
			for (int i = 1; i <= columnsNumber; i++) {
				String value = rs.getString(i);
				row[i - 1] = value != null ? value : "NULL";
			}
			rowsList.add(row);
		}

		// Conversion en tableau
		String[][] rows = rowsList.toArray(new String[0][]);

		// Affichage avec ShellUtil
		ShellUtil.printTable("Result of query:", headers, rows);
	}

	private void ping() {
		try {
			long startTime = System.nanoTime();
			JdbcContext.connection.isValid(2);
			long endTime = System.nanoTime();
			System.out.println("Ping: " + (endTime - startTime) / 1_000_000.0 + " ms");
		} catch (SQLException e) {
			System.out.println("Ping error: " + e.getMessage());
		}
	}

	private void listTables() {
		try {
			DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
			ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });

			List<String[]> tablesList = new ArrayList<>();
			while (rs.next()) {
				tablesList.add(new String[] { rs.getString("TABLE_NAME") });
			}

			if (!tablesList.isEmpty()) {
				String[] headers = { "TABLE_NAME" };
				String[][] tables = tablesList.toArray(new String[0][]);
				ShellUtil.printTable("Tables in the database:", headers, tables);
			} else {
				System.out.println("No tables found in the database.");
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void describeTable() {
		try {
			DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
			ResultSet rs = metaData.getColumns(null, null, tableName, "%");

			List<String[]> columnsList = new ArrayList<>();
			while (rs.next()) {
				String[] column = {
						rs.getString("COLUMN_NAME"),
						rs.getString("TYPE_NAME"),
						rs.getString("COLUMN_SIZE"),
						rs.getString("IS_NULLABLE")
				};
				columnsList.add(column);
			}

			if (!columnsList.isEmpty()) {
				String title = "Structure of table " + tableName + ":";
				String[] headers = { "Name", "Type", "Size", "Nullable" };
				String[][] columns = columnsList.toArray(new String[0][]);
				ShellUtil.printTable(title, headers, columns);
			} else {
				System.out.println("Table '" + tableName + "' not found or has no columns.");
			}
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
