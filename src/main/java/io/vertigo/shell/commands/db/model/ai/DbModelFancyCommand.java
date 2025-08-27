package io.vertigo.shell.commands.db.model.ai;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ai.Agent;
import io.vertigo.shell.ai.AgentBuilder;
import io.vertigo.shell.commands.db.DbContext;
import io.vertigo.shell.commands.db.model.DbModel;
import io.vertigo.shell.commands.db.model.DbModel.JdbcColumn;
import io.vertigo.shell.commands.db.model.DbModel.JdbcTable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "fancy", description = "Create a fancy set of 10 data for a table")
public final class DbModelFancyCommand implements ShellCommand {

	@Option(names = { "--table", "-T" }, required = true, description = "table name")
	private String tableName;

	@Override
	public void reset() {
		tableName = null;
	}

	@Override
	public void run() {
		final Agent agent = new AgentBuilder().build();
		JdbcTable tableXX = null;
		for (final DbModel.JdbcSchema schema : DbContext.model().schemas()) {
			for (final JdbcTable table : schema.tables()) {
				if ("artist".equals(table.name())) {
					tableXX = table;
				}
			}
		}
		final StringBuilder info = new StringBuilder()
				.append("table : ").append(tableName);
		for (final JdbcColumn column : tableXX.columns()) {
			info
					.append("{ column ")
					.append("name : ").append(column.name()).append(", ")
					.append("primaryKey : ").append(column.isPrimaryKey()).append(", ")
					.append("nullable : ").append(column.nullable()).append(", ")
					.append("type : ").append(column.typeName())
					.append(" }, ");
		}
		final String query = """
				Crée un insert SQL pour remplir la table artist
				dont la structure est la suivants :"
				""" + info.toString()
				+ """
						utilise des noms d'artistes pop connus de tous
							""";
		final String response = agent.answer(query);
		System.out.println(response);
	}

}
