package io.vertigo.shell.systems.db.commands.save;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import picocli.CommandLine.Command;

@Command(name = "save", description = "Save the model")
public final class DbSaveCommand implements ShellCommand {

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT); // JSON lisible

	/** Sauvegarde l'instance en JSON sur disque */
	private static void save(final DbModel model) throws IOException {
		MAPPER.writeValue(DbContext.STORAGE_FILE, model);
	}

	@Override
	public void run() {
		try {
			save(DbContext.model());
		} catch (final Exception e) {
			throw new VSystemException(e, "Failed to load model : {0}", e.getMessage());
		}
	}

}
