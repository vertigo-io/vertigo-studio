package io.vertigo.shell.commands.db;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.commands.db.jdbc.JdbcConnectCommand;
import io.vertigo.shell.commands.db.jdbc.JdbcDisconnectCommand;
import io.vertigo.shell.commands.db.jdbc.JdbcPingCommand;
import io.vertigo.shell.commands.db.jdbc.JdbcQueryCommand;
import io.vertigo.shell.commands.db.jdbc.JdbcStatsCommand;
import io.vertigo.shell.commands.db.model.ai.DbModelFancyCommand;
import io.vertigo.shell.commands.db.model.analyze.DbModelAnalyzeCommand;
import io.vertigo.shell.commands.db.model.analyze.DbModelMermaidCommand;
import io.vertigo.shell.commands.db.model.cluster.DbModelClusterCommand;
import io.vertigo.shell.commands.db.model.list.DbModelListCommand;
import io.vertigo.shell.commands.db.model.scanner.DbModelScanCommand;
import io.vertigo.shell.commands.db.model.store.DbModelLoadCommand;
import io.vertigo.shell.commands.db.model.store.DbModelSaveCommand;
import picocli.CommandLine.Command;

@Command(
		name = "db",
		description = "Db commands",
		subcommands = {
				//--- JDBC requires a connection with the db
				JdbcConnectCommand.class,
				JdbcDisconnectCommand.class,
				JdbcQueryCommand.class,
				JdbcPingCommand.class,
				JdbcStatsCommand.class,
				//--- Loads from db or json.
				//--- Saves to json
				DbModelLoadCommand.class,
				DbModelSaveCommand.class,
				//--- Lists elements ...
				DbModelListCommand.class,
				//--- Analyzes, clusters...
				DbModelClusterCommand.class,
				DbModelAnalyzeCommand.class,
				DbModelMermaidCommand.class,
				DbModelScanCommand.class,
				//--- AI
				DbModelFancyCommand.class
		})

public final class DbCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
