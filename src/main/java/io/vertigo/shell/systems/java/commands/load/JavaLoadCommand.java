package io.vertigo.shell.systems.java.commands.load;

import java.io.IOException;
import java.nio.file.Path;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shell.systems.java.JavaContext;
import io.vertigo.shell.systems.java.JavaVar;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;
import picocli.CommandLine.Command;

@Command(name = "load", description = "Load the java model")
public final class JavaLoadCommand implements ShellCommand {

	@Override
	public ShinyComponent build() {
		final Path rootPath = Path.of(Env.get(JavaVar.ROOT_PATH));
		try {
			load(rootPath);
			return Shiny.paragraph()
					.withText(ShinyColors.GREEN_BRIGHT.fg("Successfully loaded."))
					.build();
		} catch (final Exception e) {
			return Shiny.error()
					.withText("Failed to load model : " + e.getMessage())
					.build();
		}
	}

	private static void load(Path rootPath) throws IOException {
		final var model = new JavaModelLoader().loadModel(rootPath);
		JavaContext.model(model);
		System.out.println("model java loaded : packages (" + model.packages().size() + ")");
	}
}
