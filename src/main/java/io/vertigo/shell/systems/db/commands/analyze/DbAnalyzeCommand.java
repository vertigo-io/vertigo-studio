package io.vertigo.shell.systems.db.commands.analyze;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel.JdbcSchema;
import io.vertigo.shell.systems.db.DbModel.JdbcTable;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import picocli.CommandLine.Command;

@Command(name = "analyze", description = "Analyze the model")
public final class DbAnalyzeCommand implements ShellCommand {

	@Override
	public void run() {
		final ShinyWriter writer = ShinyRenderer.writer();
		int tables = 0;
		int columns = 0;
		int relations = 0;

		for (final JdbcSchema schema : DbContext.model().schemas()) {
			for (final JdbcTable table : schema.tables()) {
				tables++;
				columns += table.columns().size();
				relations += table.relations().size();
			}
		}
		final String[] result = { "" + tables, "" + columns, "" + relations };
		final List<String[]> rows = new ArrayList<>();
		rows.add(result);

		ShinyRenderer.render(
				Shiny.table()
						.withTitle("Objects in the database:")
						.withNoDataFound("No object found in the database.")
						.withHeader("Table", "Column", "Relations")
						.addAllRows(rows)
						.build());
		//
		final int complexity = (10 * tables + 1 * columns + relations * 3) / tables;
		writer.println("Complexity :" + complexity);

		//--------------------------------------------
		//------ANALYZER------------------------------
		//--------------------------------------------
		final DbModelAnalysisReport report = DbModelAnalyzer.analyze(DbContext.model());
		writer.println("tablesWithoutPrimaryKey :" + report.tablesWithoutPrimaryKey());
		writer.println("trivialCheckConstraints :" + report.trivialCheckConstraints());
		//lister ici toutes les anomalies détectées

		final List<String[]> dependencies = new ArrayList<>();
		report.tableDependencyStats().forEach((t, stats) -> {
			dependencies.add(new String[] { t, "" + stats.fanIn(), "" + stats.fanOut(), "" + stats.transitiveFanIn() });
		});

		ShinyRenderer.render(
				Shiny.table()
						.withTitle("Dependency stats")
						.withHeader("table", "in", "out", "transitive Fan In")
						.addAllRows(dependencies)
						.build());
	}
}
