package io.vertigo.shell.labs.db.model.analyze;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import io.vertigo.shell.labs.db.model.DbModel.JdbcSchema;
import io.vertigo.shell.labs.db.model.DbModel.JdbcTable;
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
		//A linear
		final int complexity = 10 * tables + 1 * columns + relations * 3;
		System.out.println("Complexity :" + complexity);

		//--------------------------------------------
		//------ANALYZER------------------------------
		//--------------------------------------------
		final DbModelAnalysisReport report = DbModelAnalyzer.analyze(DbContext.model());
		//lister ici toutes les anomalies détectées
		report.tableDependencyStats().forEach((table, stats) -> {
			System.out.printf("Table %s: fanIn=%d, fanOut=%d, fanInTransitive=%d%n",
					table, stats.fanIn(), stats.fanOut(), stats.transitiveFanIn());
		});
	}

}
