package io.vertigo.shell.systems.db.commands.connect;

import java.sql.SQLException;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbVar;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.style.ShinyColors;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "connect", description = "Connects to a JDBC database")
public final class DbConnectCommand implements ShellCommand {
	@Option(names = { "--url", "-u" }, description = "JDBC URL")
	private String url;

	@Option(names = { "--user", "-U" }, description = "Database user")
	private String user;

	@Option(names = { "--password", "-P" }, description = "Database password")
	private String password;

	@Override
	public ShinyModel build() {
		if (DbContext.isConnected()) {
			return Shiny.error()
					.withText("Already connected. Disconnect first.")
					.build();
		}
		if (url == null) {
			url = Env.get(DbVar.URL);
		}

		if (user == null) {
			user = Env.get(DbVar.USER);
		}

		if (password == null) {
			password = Env.get(DbVar.PASSWORD);
		}
		return connect(url, user, password);
	}

	public static ShinyModel connect(final String url, String user, String password) {
		try {
			DbContext.connect(url, user, password);
			return Shiny.paragraph()
					.withText(ShinyColors.GREEN_BRIGHT.fg("Successfully connected."))
					.build();
		} catch (final SQLException e) {
			return Shiny.error()
					.withText("Failed to connect to database: " + e.getMessage())
					.build();
		}
	}

	@Override
	public void reset() {
		url = null;
		user = null;
		password = null;
	}
}
