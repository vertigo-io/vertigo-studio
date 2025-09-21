package io.vertigo.shell.systems.db.commands.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "query", description = "Executes a query")
public final class DbQueryCommand implements ShellCommand {
	@Option(names = { "--query", "-q" }, description = "SQL query to execute", required = true)
	private String query;

	@Override
	public ShinyComponent build() {
		final ShinyComponent component;
		try (Statement stmt = DbContext.connection().createStatement()) {
			if (query.trim().toLowerCase().startsWith("select")) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					component = buildResultSet(rs);
				}
			} else {
				final int rowsAffected = stmt.executeUpdate(query);
				component = Shiny.paragraph()
						.withText(rowsAffected + " row(s) affected.")
						.build();
			}
			query = null;
		} catch (final SQLException e) {
			return Shiny.error()
					.withText("Failed to execute SQL query:" + e.getMessage())
					.build();
		}
		return component;
	}

	private ShinyComponent buildResultSet(final ResultSet rs) throws SQLException {
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

		return Shiny.table()
				.withTitle("Result of query:")
				.withNoDataFound("No data found")
				.withHeader(header)
				.addAllRows(rows)
				.build();
	}

	@Override
	public void reset() {
		this.query = null;
	}
}
