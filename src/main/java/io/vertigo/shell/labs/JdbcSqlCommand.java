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
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.Shiny;

@Parameters(commandNames = "jdbc-sql", commandDescription = "Executes a SQL query")
public final class JdbcSqlCommand implements ShellCommand {
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

	@Override
	public void reset() {
		query = null;
		tables = false;
		ping = false;
		tableName = null;
		help = false;
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
		final String[] header = new String[columnsNumber];
		for (int i = 1; i <= columnsNumber; i++) {
			header[i - 1] = rsmd.getColumnName(i);
		}

		// Récupération des données
		List<String[]> rows = new ArrayList<>();
		while (rs.next()) {
			String[] row = new String[columnsNumber];
			for (int i = 1; i <= columnsNumber; i++) {
				String value = rs.getString(i);
				row[i - 1] = value != null ? value : "NULL";
			}
			rows.add(row);
		}

		Shiny.table()
				.title("Result of query:")
				.noDataFound("No data found")
				.header(header)
				.rows(rows)
				.print();
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
			final DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
			final ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });

			final List<String[]> rows = new ArrayList<>();
			while (rs.next()) {
				rows.add(new String[] { rs.getString("TABLE_NAME") });
			}

			Shiny.table()
					.title("Tables in the database:")
					.noDataFound("No tables found in the database.")
					.header("TABLE_NAME")
					.rows(rows)
					.print();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private void describeTable() {
		try {
			final DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
			final ResultSet rs = metaData.getColumns(null, null, tableName, "%");

			final List<String[]> columns = new ArrayList<>();
			while (rs.next()) {
				String[] column = {
						rs.getString("COLUMN_NAME"),
						rs.getString("TYPE_NAME"),
						rs.getString("COLUMN_SIZE"),
						rs.getString("IS_NULLABLE")
				};
				columns.add(column);
			}

			Shiny.table()
					.title("Structure of table " + tableName + ":")
					.noDataFound("\"Table '\" + tableName + \"' not found or has no columns.\"")
					.header("Name", "Type", "Size", "Nullable")
					.rows(columns)
					.print();
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
