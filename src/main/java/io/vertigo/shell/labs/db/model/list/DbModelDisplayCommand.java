package io.vertigo.shell.labs.db.model.list;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import io.vertigo.shell.labs.db.model.DbModel.JdbcColumn;
import io.vertigo.shell.labs.db.model.DbModel.JdbcSchema;
import io.vertigo.shell.labs.db.model.DbModel.JdbcTable;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.tree.ShinyTree;
import io.vertigo.shell.shiny.tree.ShinyTreeNode;
import io.vertigo.shell.shiny.utils.ShinyColors;
import picocli.CommandLine.Command;

@Command(name = "display", description = "Display the model")
public final class DbModelDisplayCommand implements ShellCommand {

	@Override
	public void run() {
		final ShinyTree tree = Shiny.tree("model");
		for (JdbcSchema schema : DbContext.model().schemas()) {
			final ShinyTreeNode schemaNode = tree.getRoot().addNode("schema : " + schema.name());
			final ShinyTreeNode tablesNode = schemaNode.addNode("tables (" + schema.tables().size() + ")");
			for (JdbcTable table : schema.tables()) {
				final ShinyTreeNode tableNode = tablesNode.addNode(table.name());
				final ShinyTreeNode columnsNode = tableNode.addNode("columns");
				for (JdbcColumn column : table.columns()) {

					String info = column.name() + " " + ShinyColors.GREEN_BRIGHT + column.typeName() + ShinyColors.RESET;

					if (column.isPrimaryKey()) {
						info = ShinyColors.UNDERLINE + info + ShinyColors.RESET;
					}
					columnsNode.addNode(info);
				}
			}
		}

		tree.print();
	}

}
