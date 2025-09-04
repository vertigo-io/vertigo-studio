package io.vertigo.shell.commands.db.model.scanner;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.commands.db.DbContext;
import io.vertigo.shell.commands.db.model.DbModel;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.data.tree.ShinyIcon;
import io.vertigo.shell.shiny.data.tree.ShinyTree;
import io.vertigo.shell.shiny.data.tree.ShinyTreeNode;
import picocli.CommandLine.Command;

@Command(name = "scan", description = "Scans DB model for columns potentially containing sensitive data.")
public class DbModelScanCommand implements ShellCommand {
	private DbModel mockDbModel;

	// Constructor for Picocli
	public DbModelScanCommand() {
		mockDbModel = DbContext.model();
	}

	@Override
	public void run() {
		final ShinyTree resultsTree = Shiny.tree("Sensitive Data Scan Results");
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

		resultsTree.print();
	}

}
