package io.vertigo.shell.systems.java.commands.load;

import java.io.IOException;
import java.nio.file.Path;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shell.systems.java.JavaContext;
import io.vertigo.shell.systems.java.JavaVar;
import picocli.CommandLine.Command;

@Command(name = "load", description = "Load the java model")
public final class JavaLoadCommand implements ShellCommand {

	@Override
	public void run() {
		final Path rootPath = Path.of(Env.get(JavaVar.ROOT_PATH));
		try {
			load(rootPath);
		} catch (final IOException e) {
			throw new VSystemException(e, "Failed to load model : {0}", e.getMessage());
		}
	}

	private static void load(Path rootPath) throws IOException {
		final var model = new JavaModelLoader().loadModel(rootPath);
		JavaContext.model(model);
		System.out.println("model java loaded : packages (" + model.packages().size() + ")");
	}
}
