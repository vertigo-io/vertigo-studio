package io.vertigo.shell.systems.db.commands.scan;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.tree.ShinyIcon;
import io.vertigo.shiny.models.data.tree.ShinyTree;
import io.vertigo.shiny.models.data.tree.ShinyTreeNode;
import picocli.CommandLine.Command;

@Command(name = "scan", description = "Scans DB model for columns potentially containing sensitive data.")
public class DbScanCommand implements ShellCommand {

	@Override
	public ShinyModel build() {
		final DbModel mockDbModel = DbContext.model();
		final ShinyTree resultsTree = Shiny.tree("Sensitive Data Scan Results").build();
		final ShinyTreeNode root = resultsTree.getRoot();

		boolean sensitiveDataFound = false;

		for (DbModel.JdbcSchema schema : mockDbModel.schemas()) {
			final ShinyTreeNode schemaNode = root.addChild(schema.name(), ShinyIcon.FOLDER_OPEN);
			for (DbModel.JdbcTable table : schema.tables()) {
				ShinyTreeNode tableNode = null;
				for (DbModel.JdbcColumn column : table.columns()) {
					final String columnName = column.name();
					final String sensitiveReason = DbModelScanner.sensitive(columnName);
					if (sensitiveReason != null) {
						if (tableNode == null) {
							tableNode = schemaNode.addChild(table.name());
						}
						tableNode.addChild(columnName + " (" + sensitiveReason + ")", ShinyIcon.WARNING);
						sensitiveDataFound = true;
					}
				}
			}
		}

		if (!sensitiveDataFound) {
			root.addChild("No sensitive data found based on current patterns.", ShinyIcon.INFO);
		}
		return resultsTree;
	}

}
