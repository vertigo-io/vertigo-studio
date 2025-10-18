package io.vertigo.shell.systems.db.commands.describe;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shell.systems.db.DbModel.JdbcSchema;
import io.vertigo.shell.systems.db.DbModel.JdbcTable;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "describe", description = "Describe an elements of the model")
public final class DbDescribeCommand implements ShellCommand {
	@Option(names = { "--tableName", "-T" }, description = "select a table")
	public String tableName;

	@Override
	public ShinyModel build() {
		if (tableName == null) {
			return Shiny.error()
					.withText("Please specify a valid table name.")
					.build();
		}
		return describeTable();
	}

	private ShinyModel describeTable() {
		JdbcTable table = null;
		final List<String[]> columns = new ArrayList<>();
		for (final JdbcSchema schema : DbContext.model().schemas()) {
			for (final JdbcTable t : schema.tables()) {
				if (tableName.equalsIgnoreCase(t.name())) {
					table = t;
					break;
				}
			}
		}

		if (table != null) {
			for (final DbModel.JdbcColumn col : table.columns()) {
				final String[] column = {
						col.name(),
						col.typeName(),
						"" + col.size(),
						"" + col.nullable()
				};
				columns.add(column);
			}
		}
		return Shiny.table()
				.withTitle("Structure of table " + tableName + ":")
				.withNoDataFound("\"Table '\" + tableName + '\' not found.\"")
				.withHeader("Name", "Type", "Size", "Nullable")
				.addAllRows(columns)
				.build();
	}
}
