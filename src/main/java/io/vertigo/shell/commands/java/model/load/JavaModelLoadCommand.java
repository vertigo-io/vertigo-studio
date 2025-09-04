package io.vertigo.shell.commands.java.model.load;

import java.nio.file.Path;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.commands.java.model.JavaContext;
import picocli.CommandLine.Command;

@Command(name = "load", description = "Load the java model")
public final class JavaModelLoadCommand implements ShellCommand {

	//	// Définition du groupe d'options mutuellement exclusives
	//	@ArgGroup(exclusive = true, multiplicity = "1") // Une seule option doit être spécifiée
	//	private LoadSource loadSource;
	//
	//	// Classe interne pour regrouper les options
	//	static class LoadSource {
	//		@Option(names = { "--db", "-d" }, description = "Load model from database (schemas, tables..)")
	//		private boolean db;
	//
	//		@Option(names = { "--file", "-f" }, description = "Load model from file (schemas, tables..)")
	//		private boolean file;
	//	}

	@Override
	public void run() {
		try {
			var model = new JavaModelLoader().loadModel(Path.of("C:\\Users\\pchretien\\GitHub\\vertigo-core"));
			JavaContext.model(model);
		} catch (final Exception e) {
			throw new VSystemException(e, "Failed to load model : {0}", e.getMessage());
		}
	}
}
