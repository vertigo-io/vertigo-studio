package io.vertigo.shell.systems.db.commands.disconnect;

import java.sql.SQLException;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.style.ShinyColors;
import picocli.CommandLine.Command;

@Command(name = "disconnect", description = "Disconnects from the JDBC database")
public final class DbDisconnectCommand implements ShellCommand {
	@Override
	public ShinyModel build() {
		try {
			DbContext.disconnect();
			return Shiny.paragraph()
					.withText(ShinyColors.GREEN_BRIGHT.fg("Successfully disconnected."))
					.build();
		} catch (final SQLException e) {
			return Shiny.error()
					.withText("Failed to disconnect to database: " + e.getMessage())
					.build();
		}
	}
}
