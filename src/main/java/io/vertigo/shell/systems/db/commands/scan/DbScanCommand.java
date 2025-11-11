package io.vertigo.shell.systems.db.commands.scan;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.tree.ShinyTreeBuilder;
import picocli.CommandLine.Command;

@Command(name = "scan", description = "Scans DB model for columns potentially containing sensitive data.")
public class DbScanCommand implements ShellCommand {

	@Override
	public ShinyModel build() {
		final DbModel mockDbModel = DbContext.model();
		final ShinyTreeBuilder resultsTreeBuilder = Shiny.tree().withLabel("Sensitive Data Scan Results");

		for (DbModel.JdbcSchema schema : mockDbModel.schemas()) {
			boolean sensitiveDataFound = false;
			final var schemaTreeBuilder = resultsTreeBuilder.addTree(schema.name());
			for (DbModel.JdbcTable table : schema.tables()) {
				ShinyTreeBuilder tableTreeBuilder = null;
				for (DbModel.JdbcColumn column : table.columns()) {
					final String columnName = column.name();
					final String sensitiveReason = DbModelScanner.sensitive(columnName);
					if (sensitiveReason != null) {
						if (tableTreeBuilder == null) {
							tableTreeBuilder = schemaTreeBuilder.addTree(table.name());
						}
						tableTreeBuilder.addLeaf(columnName + " (" + sensitiveReason + ")");
						sensitiveDataFound = true;
					}
				}
			}
			if (!sensitiveDataFound) {
				schemaTreeBuilder.addLeaf("No sensitive data found based on current patterns.");
			}
		}

		return resultsTreeBuilder.build();
	}

}
