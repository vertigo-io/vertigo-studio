package io.vertigo.shell.commands.db.model.analyze;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.commands.db.DbContext;
import io.vertigo.shell.commands.db.model.DbModel.JdbcSchema;
import io.vertigo.shell.commands.db.model.DbModel.JdbcTable;
import io.vertigo.shell.shiny.Shiny;
import picocli.CommandLine.Command;

@Command(name = "analyze", description = "Analyze the model")
public final class DbModelAnalyzeCommand implements ShellCommand {

	@Override
	public void run() {
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

		Shiny.table()
				.title("Objects in the database:")
				.noDataFound("No object found in the database.")
				.header("Table", "Column", "Relations")
				.rows(rows)
				.print();
		//
		final int complexity = (10 * tables + 1 * columns + relations * 3) / tables;
		System.out.println("Complexity :" + complexity);

		//--------------------------------------------
		//------ANALYZER------------------------------
		//--------------------------------------------
		final DbModelAnalysisReport report = DbModelAnalyzer.analyze(DbContext.model());
		System.out.println("tablesWithoutPrimaryKey :" + report.tablesWithoutPrimaryKey());
		System.out.println("trivialCheckConstraints :" + report.trivialCheckConstraints());
		//lister ici toutes les anomalies détectées

		final List<String[]> dependencies = new ArrayList<>();
		report.tableDependencyStats().forEach((t, stats) -> {
			dependencies.add(new String[] { t, "" + stats.fanIn(), "" + stats.fanOut(), "" + stats.transitiveFanIn() });
		});

		Shiny.table()
				.title("Dependency stats")
				.header("table", "in", "out", "transitive Fan In")
				.rows(dependencies)
				.print();
	}
}
