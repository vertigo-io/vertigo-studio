package io.vertigo.shell.systems.db.commands.mermaid;

import java.io.IOException;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.mermaid.ShinyMermaidServer;
import picocli.CommandLine.Command;

@Command(name = "mermaid", description = "Analyze the model")
public final class DbMermaidCommand implements ShellCommand {
	//classDiagram
	//    Animal <-- Duck
	//    Animal <-- Fish
	//    Animal <-- Zebra
	//    class Animal {
	//        +int age
	//        +String gender
	//    }
	//    class Duck {
	//        +beakColor
	//    }
	//    class Fish {
	//        +sizeInFeet
	//    }
	//    class Zebra {
	//        +is_wild
	//    }
	/////
	@Override
	public void run() {
		ShinyMermaidServer mermaidServer = Shiny.mermaid();
		mermaidServer.updateDiagram(convertDbModelToMermaid(DbContext.model()));
		try {
			mermaidServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String convertDbModelToMermaid(final DbModel model) {
		final String tab = "    ";

		final StringBuilder mermaid = new StringBuilder()
				.append("classDiagram\n");

		// Define classes (tables)
		for (final DbModel.JdbcSchema schema : model.schemas()) {
			for (final DbModel.JdbcTable table : schema.tables()) {
				final String tableName = schema.name() + "_" + table.name(); // Sanitize for Mermaid
				mermaid.append(tab).append("class ").append(tableName).append(" {\n")
						//				// Add columns
						//				for (final DbModel.JdbcColumn column : table.columns()) {
						//					mermaid.append("    ").append(column.name()).append(" : ").append(column.typeName()).append("\n");
						//				}
						.append(tab).append("}\n");
			}
		}

		// Define relationships
		for (final DbModel.JdbcSchema schema : model.schemas()) {
			for (final DbModel.JdbcTable table : schema.tables()) {
				final String sourceTableName = schema.name() + "_" + table.name();
				for (final DbModel.JdbcRelation rel : table.relations()) {
					final String targetTableName = schema.name() + "_" + rel.targetTable(); // Assuming target table is in same schema
					// Mermaid relationship syntax: ClassA --|> ClassB : Description
					// Or ClassA -- ClassB : Description
					// For FK, let's use --
					mermaid.append(tab).append(sourceTableName).append(" --> ").append(targetTableName).append("\n");
				}
			}
		}
		return mermaid.toString();
	}

}
