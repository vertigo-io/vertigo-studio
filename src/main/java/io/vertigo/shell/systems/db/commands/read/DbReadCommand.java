package io.vertigo.shell.systems.db.commands.read;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shell.systems.db.DbVar;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;
import picocli.CommandLine.Command;

@Command(name = "read", description = "read the model")
public final class DbReadCommand implements ShellCommand {

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT); // JSON lisible

	/** Recharge une instance depuis le fichier JSON */
	private static DbModel read() throws IOException {
		final String storage = Env.get(DbVar.STORAGE);
		return MAPPER.readValue(new File(storage), DbModel.class);
	}

	@Override
	public ShinyComponent build() {
		try {
			DbModel model = read();
			DbContext.model(model);
			return Shiny.paragraph()
					.withText(ShinyColors.GREEN_BRIGHT.fg("Successfully read."))
					.build();
		} catch (final Exception e) {
			e.printStackTrace();
			return Shiny.error()
					.withText("Failed to read model from file: " + e.getMessage())
					.build();
		}
	}

}
