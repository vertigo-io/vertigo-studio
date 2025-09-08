package io.vertigo.shell.systems.db.commands.load;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shell.systems.db.DbVar;
import io.vertigo.shell.systems.env.Env;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "load", description = "Load the model")
public final class DbLoadCommand implements ShellCommand {

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT); // JSON lisible

	/** Recharge une instance depuis le fichier JSON */
	private static DbModel load() throws IOException {
		final String storage = Env.get(DbVar.STORAGE);
		return MAPPER.readValue(new File(storage), DbModel.class);
	}

	// Définition du groupe d'options mutuellement exclusives
	@ArgGroup(exclusive = true, multiplicity = "1") // Une seule option doit être spécifiée
	private LoadSource loadSource;

	// Classe interne pour regrouper les options
	static class LoadSource {
		@Option(names = { "--db", "-d" }, description = "Load model from database (schemas, tables..)")
		private boolean db;

		@Option(names = { "--file", "-f" }, description = "Load model from file (schemas, tables..)")
		private boolean file;
	}

	@Override
	public void run() {
		DbModel model = null;
		try {
			if (loadSource.db) {
				model = new DbModelLoader(DbContext.connection()).loadModel();
			} else if (loadSource.file) {
				model = load();
			}
		} catch (final Exception e) {
			throw new VSystemException(e, "Failed to load model : {0}", e.getMessage());
		}
		DbContext.model(model);
	}

	@Override
	public void reset() {
		loadSource = new LoadSource();
	}
}
