package io.vertigo.shell.labs.db.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import io.vertigo.shell.labs.db.model.DbModel.JdbcSchema;
import io.vertigo.shell.labs.db.model.DbModel.JdbcTable;
import io.vertigo.shell.shiny.Shiny;
import picocli.CommandLine.Command;

@Command(name = "stats", description = "List stats")
public final class JdbcStatsCommand implements ShellCommand {

	@Override
	public void run() {
		final List<String> tableNames = new ArrayList<String>();
		final List<Integer> tableCounts = new ArrayList<Integer>();

		for (JdbcSchema schema : DbContext.model().schemas()) {
			for (JdbcTable table : schema.tables()) {
				String query = "select count(*) as count from " + table.name();
				try (Statement stmt = DbContext.connection().createStatement()) {
					try (ResultSet rs = stmt.executeQuery(query)) {
						rs.next();
						tableNames.add(table.name());
						tableCounts.add(rs.getInt(1));
					}
				} catch (final SQLException e) {
					throw new VSystemException(e, "Failed to execute SQL query: {0}", e.getMessage());
				}
			}
		}

		Shiny.barChart()
				.title("Tables Row Count")
				.header(tableNames)
				.rows(tableCounts)
				.print(100);
	}

}
