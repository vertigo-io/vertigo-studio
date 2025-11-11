package io.vertigo.shell.systems.db.commands.show;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel.JdbcColumn;
import io.vertigo.shell.systems.db.DbModel.JdbcSchema;
import io.vertigo.shell.systems.db.DbModel.JdbcTable;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.tree.ShinyTreeBuilder;
import io.vertigo.shiny.style.ShinyColors;
import io.vertigo.shiny.style.ShinyEffects;
import picocli.CommandLine.Command;

@Command(name = "model", description = "Display elements of the model")
public final class DbShowModelCommand implements ShellCommand {
	@Override
	public ShinyModel build() {
		return showModel();
	}

	private ShinyModel showModel() {
		final ShinyTreeBuilder treeBuilder = Shiny.tree().addTree("model");
		for (final JdbcSchema schema : DbContext.model().schemas()) {
			final ShinyTreeBuilder schemaTreeBuilder = treeBuilder.addTree("schema : " + schema.name());
			final ShinyTreeBuilder tablesTreeBuilder = schemaTreeBuilder.addTree("tables (" + schema.tables().size() + ")");
			for (final JdbcTable table : schema.tables()) {
				final var tableTreeBuilder = tablesTreeBuilder.addTree(table.name());
				final var columnsTreeBuilder = tableTreeBuilder.addTree("columns");
				for (final JdbcColumn column : table.columns()) {
					String info = column.name() + " " + ShinyColors.GREEN_BRIGHT.fg(column.typeName());
					if (column.isPrimaryKey()) {
						info = ShinyEffects.UNDERLINE.apply(info);
					}
					columnsTreeBuilder.addLeaf(info);
				}
			}
		}
		return treeBuilder.build();
	}
}
