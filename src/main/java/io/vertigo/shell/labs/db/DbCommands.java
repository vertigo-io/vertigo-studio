package io.vertigo.shell.labs.db;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.jdbc.JdbcConnectCommand;
import io.vertigo.shell.labs.db.jdbc.JdbcDisconnectCommand;
import io.vertigo.shell.labs.db.jdbc.JdbcPingCommand;
import io.vertigo.shell.labs.db.jdbc.JdbcQueryCommand;
import io.vertigo.shell.labs.db.jdbc.JdbcStatsCommand;
import io.vertigo.shell.labs.db.model.analyze.DbModelAnalyzeCommand;
import io.vertigo.shell.labs.db.model.analyze.DbModelFancyCommand;
import io.vertigo.shell.labs.db.model.cluster.DbModelClusterCommand;
import io.vertigo.shell.labs.db.model.list.DbModelDisplayCommand;
import io.vertigo.shell.labs.db.model.list.DbModelListTableCommand;
import io.vertigo.shell.labs.db.model.store.DbModelLoadCommand;
import io.vertigo.shell.labs.db.model.store.DbModelSaveCommand;
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
				//--- Load from db or json. 
				//--- Save to json
				DbModelLoadCommand.class,
				DbModelSaveCommand.class,
				//--- Display, list...
				DbModelDisplayCommand.class,
				DbModelListTableCommand.class,
				//--- Analyze, cluster...
				DbModelClusterCommand.class,
				DbModelAnalyzeCommand.class,
				DbModelFancyCommand.class
		})

public final class DbCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
