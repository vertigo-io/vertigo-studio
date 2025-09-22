package io.vertigo.shell.systems.db.commands.show;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel.JdbcSchema;
import io.vertigo.shell.systems.db.DbModel.JdbcTable;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import picocli.CommandLine.Command;

@Command(name = "tables", description = "Display all tables of the model")
public final class DbShowTablesCommand implements ShellCommand {
	@Override
	public ShinyComponent build() {
		return listTables();
	}

	private ShinyComponent listTables() {
		final List<String[]> rows = new ArrayList<>();
		for (final JdbcSchema schema : DbContext.model().schemas()) {
			for (final JdbcTable table : schema.tables()) {
				final String[] row = { table.name(), table.columns().size() + "" };
				rows.add(row);
			}
		}
		return Shiny.table()
				.withTitle("Tables in the database:")
				.withNoDataFound("No tables found in the database.")
				.withHeader("TABLE_NAME", "COLUMNS")
				.addAllRows(rows)
				.build();
	}
}
