package io.vertigo.shell.systems.db.commands.load;

import java.sql.SQLException;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.style.ShinyColors;
import picocli.CommandLine.Command;

@Command(name = "load", description = "Load the model")
public final class DbLoadCommand implements ShellCommand {

	@Override
	public ShinyModel build() {
		try {
			DbModel model = new DbModelLoader(DbContext.connection()).loadModel();
			DbContext.model(model);
			return Shiny.paragraph()
					.withText(ShinyColors.GREEN_BRIGHT.fg("Successfully loaded."))
					.build();
		} catch (final SQLException e) {
			return Shiny.error()
					.withText("Failed to load model from database: " + e.getMessage())
					.build();
		}
	}

}
