package io.vertigo.shell.systems.db.commands;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.commands.analyze.DbAnalyzeCommand;
import io.vertigo.shell.systems.db.commands.cluster.DbClusterCommand;
import io.vertigo.shell.systems.db.commands.connect.DbConnectCommand;
import io.vertigo.shell.systems.db.commands.disconnect.DbDisconnectCommand;
import io.vertigo.shell.systems.db.commands.labs.DbFancyCommand;
import io.vertigo.shell.systems.db.commands.list.DbListCommand;
import io.vertigo.shell.systems.db.commands.load.DbLoadCommand;
import io.vertigo.shell.systems.db.commands.mermaid.DbMermaidCommand;
import io.vertigo.shell.systems.db.commands.ping.DbPingCommand;
import io.vertigo.shell.systems.db.commands.query.DbQueryCommand;
import io.vertigo.shell.systems.db.commands.save.DbSaveCommand;
import io.vertigo.shell.systems.db.commands.scan.DbScanCommand;
import io.vertigo.shell.systems.db.commands.stats.DbStatsCommand;
import picocli.CommandLine.Command;

@Command(
		name = "db",
		description = "Db commands",
		subcommands = {
				//--- JDBC requires a connection with the db
				DbConnectCommand.class,
				DbDisconnectCommand.class,
				DbQueryCommand.class,
				DbPingCommand.class,
				DbStatsCommand.class,
				//--- Loads from db or json.
				DbLoadCommand.class,
				//--- Saves to json
				DbSaveCommand.class,
				//--- Lists elements ...
				DbListCommand.class,
				//--- Analyzes, clusters...
				DbClusterCommand.class,
				DbAnalyzeCommand.class,
				DbMermaidCommand.class,
				DbScanCommand.class,
				//--- AI
				DbFancyCommand.class
		})

public final class DbCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
