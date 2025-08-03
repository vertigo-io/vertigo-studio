package io.vertigo.shell.labs.Jdbc;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(
		name = "jdbc",
		description = "JDBC commands",
		subcommands = {
				JdbcConnectCommand.class,
				JdbcDisconnectCommand.class,
				JdbcQueryCommand.class,
				JdbcPingCommand.class
		})
public final class JdbcCommand implements ShellCommand {
	@Override
	public void run() {
	}
}
