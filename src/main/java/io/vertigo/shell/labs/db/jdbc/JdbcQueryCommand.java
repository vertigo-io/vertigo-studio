package io.vertigo.shell.labs.db.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import io.vertigo.shell.shiny.Shiny;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "query", description = "Executes a query")
public final class JdbcQueryCommand implements ShellCommand {
	@Option(names = { "--query", "-q" }, description = "SQL query to execute", required = true)
	private String query;

	@Override
	public void run() {
		try (Statement stmt = DbContext.connection().createStatement()) {
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
		final List<String[]> rows = new ArrayList<>();
		while (rs.next()) {
			final String[] row = new String[columnsNumber];
			for (int i = 1; i <= columnsNumber; i++) {
				final String value = rs.getString(i);
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

	@Override
	public void reset() {
		this.query = null;
	}
}
