package io.vertigo.shell.systems.env.commands.set;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyModel;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "set", description = "Set a value in the photo context.")
public final class EnvSetCommand implements ShellCommand {

	@Parameters(index = "0", description = "The key to set (e.g., 'photo.rootpath' ).")
	private String key;

	@Parameters(index = "1", description = "The value to set.")
	private String value;

	@Override
	public ShinyModel build() {
		Env.set(key, value);
		return Shiny.paragraph()
				.withText("parma '" + key + "' is now set with '" + value + "'")
				.build();
		//		
		//		if ("rootPath".equalsIgnoreCase(variable)) {
		//			final Path rootPath = Path.of(value);
		//			if (!Files.isDirectory(rootPath)) {
		//				System.out.println("Error: " + rootPath + " is not a directory.");
		//				return;
		//			}
		//			System.out.println("Photo rootPath set to: " + rootPath);
		//		} else {
		//			System.out.println("Unknown variable: " + variable);
		//		}
	}
}
