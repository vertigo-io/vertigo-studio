package io.vertigo.shell.systems.db.commands.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shell.systems.db.DbModel.JdbcColumn;
import io.vertigo.shell.systems.db.DbModel.JdbcSchema;
import io.vertigo.shell.systems.db.DbModel.JdbcTable;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.data.tree.ShinyTree;
import io.vertigo.shiny.components.data.tree.ShinyTreeNode;
import io.vertigo.shiny.style.ShinyColors;
import io.vertigo.shiny.style.ShinyEffects;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", description = "Display elements of the model")
public final class DbListCommand implements ShellCommand {
	@Option(names = { "--all", "-a" }, description = "select all objects")
	private boolean all;

	@Option(names = { "--tableName", "-T" }, description = "select a table")
	private String tableName;

	@Option(names = { "--tables", "-t" }, description = "select all tables")
	private boolean tables;

	@Override
	public void run() {
		final ShinyWriter writer = Shiny.writer();
		if (all) {
			listAll(writer);
		}
		if (tableName != null) {
			describeTable(writer);
		}
		if (tables) {
			listTables(writer);
		}
	}

	private void listAll(ShinyWriter writer) {
		final ShinyTree tree = Shiny.tree("model").build();
		for (final JdbcSchema schema : DbContext.model().schemas()) {
			final ShinyTreeNode schemaNode = tree.getRoot().addChild("schema : " + schema.name());
			final ShinyTreeNode tablesNode = schemaNode.addChild("tables (" + schema.tables().size() + ")");
			for (final JdbcTable table : schema.tables()) {
				final ShinyTreeNode tableNode = tablesNode.addChild(table.name());
				final ShinyTreeNode columnsNode = tableNode.addChild("columns");
				for (final JdbcColumn column : table.columns()) {

					String info = column.name() + " " + ShinyColors.GREEN_BRIGHT.fg(column.typeName());

					if (column.isPrimaryKey()) {
						info = ShinyEffects.UNDERLINE.apply(info);
					}
					columnsNode.addChild(info);
				}
			}
		}

		tree.render(writer);
	}

	private void listTables(ShinyWriter writer) {
		final List<String[]> rows = new ArrayList<>();
		for (final JdbcSchema schema : DbContext.model().schemas()) {
			for (final JdbcTable table : schema.tables()) {
				final String[] row = { table.name(), table.columns().size() + "" };
				rows.add(row);
			}
		}
		Shiny.table()
				.withTitle("Tables in the database:")
				.withNoDataFound("No tables found in the database.")
				.withHeader("TABLE_NAME", "COLUMNS")
				.addAllRows(rows)
				.build()
				.render(writer);
	}

	private void describeTable(ShinyWriter writer) {
		JdbcTable table = null;
		for (final JdbcSchema schema : DbContext.model().schemas()) {
			for (final JdbcTable t : schema.tables()) {
				if (tableName.equalsIgnoreCase(t.name())) {
					table = t;
					break;
				}
			}

			final List<String[]> columns = new ArrayList<>();
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
			Shiny.table()
					.withTitle("Structure of table " + tableName + ":")
					.withNoDataFound("\"Table '\" + tableName + '\' not found or has no columns.\"")
					.withHeader("Name", "Type", "Size", "Nullable")
					.addAllRows(columns)
					.build()
					.render(writer);
		}
	}
}
