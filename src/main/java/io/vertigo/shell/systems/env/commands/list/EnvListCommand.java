package io.vertigo.shell.systems.env.commands.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbVar;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shell.systems.file.FileContext;
import io.vertigo.shell.systems.java.JavaVar;
import io.vertigo.shell.systems.photo.PhotoVar;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import picocli.CommandLine.Command;

@Command(name = "list", description = "Display env properties.")
public final class EnvListCommand implements ShellCommand {

	@Override
	public ShinyComponent build() {
		final List<String[]> rows = new ArrayList<>();

		// File Context
		if (FileContext.get().getRootPath() != null) {
			rows.add(new String[] { "File", "rootPath", FileContext.get().getRootPath().toString() });
			rows.add(new String[] { "File", "currentPath", FileContext.get().getCurrentPath().toString() });
		} else {
			rows.add(new String[] { "File", "(not initialized)", "" });
		}

		// Photo Env
		rows.add(new String[] { "photo", "rootPath", Env.get(PhotoVar.ROOT_PATH) });

		// DB 
		rows.add(new String[] { "db", "url", Env.get(DbVar.URL) });
		rows.add(new String[] { "db", "storage", Env.get(DbVar.STORAGE) });
		rows.add(new String[] { "db", "user", Env.get(DbVar.USER) });
		rows.add(new String[] { "db", "password", "*****" });

		// Java 
		rows.add(new String[] { "java", "rootPath", Env.get(JavaVar.ROOT_PATH) });

		return Shiny.table()
				.withTitle("Systems Properties")
				.withHeader("System", "properties", "Value")
				.addAllRows(rows)
				.build();
	}
}
