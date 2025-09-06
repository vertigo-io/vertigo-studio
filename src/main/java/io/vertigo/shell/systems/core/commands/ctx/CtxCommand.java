package io.vertigo.shell.systems.core.commands.ctx;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.file.FileContext;
import io.vertigo.shell.systems.photo.PhotoContext;
import io.vertigo.shiny.Shiny;
import picocli.CommandLine.Command;

@Command(name = "ctx", description = "Display context variables.")
public final class CtxCommand implements ShellCommand {

	@Override
	public void run() {
		final List<String[]> rows = new ArrayList<>();

		// File Context
		if (FileContext.get().getRootPath() != null) {
			rows.add(new String[] { "File", "rootPath", FileContext.get().getRootPath().toString() });
			rows.add(new String[] { "File", "currentPath", FileContext.get().getCurrentPath().toString() });
		} else {
			rows.add(new String[] { "File", "(not initialized)", "" });
		}

		// Photos Context
		if (PhotoContext.getRootPath() != null) {
			rows.add(new String[] { "Photo", "rootPath", PhotoContext.getRootPath().toString() });
			rows.add(new String[] { "Photo", "photo loaded", String.valueOf(PhotoContext.getPhotos().size()) });
		} else {
			rows.add(new String[] { "Photo", "(not initialized)", "" });
		}

		// DB Context
		if (DbContext.isConnected()) {
			rows.add(new String[] { "DB", "Connected", "yes" });
			rows.add(new String[] { "DB", "Model loaded", String.valueOf(DbContext.model() != null) });
		} else {
			rows.add(new String[] { "DB", "Connected", "no" });
		}

		Shiny.table()
				.title("Context Variables")
				.header("Context", "Variable", "Value")
				.rows(rows)
				.print();
	}
}
