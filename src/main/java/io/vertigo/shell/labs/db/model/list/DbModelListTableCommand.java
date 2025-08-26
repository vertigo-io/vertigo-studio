package io.vertigo.shell.labs.db.model.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import io.vertigo.shell.labs.db.model.DbModel;
import io.vertigo.shell.labs.db.model.DbModel.JdbcSchema;
import io.vertigo.shell.labs.db.model.DbModel.JdbcTable;
import io.vertigo.shell.shiny.Shiny;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "table", description = "Display the tables")
public final class DbModelListTableCommand implements ShellCommand {
	@Option(names = { "--all", "-a" }, description = "all tables")
	private boolean all;

	@Option(names = { "--tableName", "-t" }, description = "select a table")
	private String tableName;

	@Override
	public void run() {
		if (tableName != null) {
			describeTable();
		}
		if (all) {
			listTables();
		}
	}

	private void listTables() {
		final List<String[]> rows = new ArrayList<>();
		for (JdbcSchema schema : DbContext.model().schemas()) {
			for (JdbcTable table : schema.tables()) {
				String[] row = { table.name(), table.columns().size() + "" };
				rows.add(row);
			}
		}
		Shiny.table()
				.title("Tables in the database:")
				.noDataFound("No tables found in the database.")
				.header("TABLE_NAME", "COLUMNS")
				.rows(rows)
				.print();
	}

	private void describeTable() {
		JdbcTable table = null;
		for (JdbcSchema schema : DbContext.model().schemas()) {
			for (JdbcTable t : schema.tables()) {
				if (tableName.equalsIgnoreCase(t.name())) {
					table = t;
					break;
				}
			}

			final List<String[]> columns = new ArrayList<>();
			if (table != null) {
				for (DbModel.JdbcColumn col : table.columns()) {
					String[] column = {
							col.name(),
							col.typeName(),
							"" + col.size(),
							"" + col.nullable()
					};
					columns.add(column);
				}
			}
			Shiny.table()
					.title("Structure of table " + tableName + ":")
					.noDataFound("\"Table '\" + tableName + '\' not found or has no columns.\"")
					.header("Name", "Type", "Size", "Nullable")
					.rows(columns)
					.print();
		}
	}

}
