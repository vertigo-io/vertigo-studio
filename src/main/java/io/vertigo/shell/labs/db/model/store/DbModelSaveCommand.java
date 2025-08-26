package io.vertigo.shell.labs.db.model.store;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import picocli.CommandLine.Command;

@Command(name = "save", description = "Save the model")
public final class DbModelSaveCommand implements ShellCommand {

	@Override
	public void run() {
		try {
			DbModelStorage.save(DbContext.model());
		} catch (Exception e) {
			throw new VSystemException(e, "Failed to load model : {0}", e.getMessage());

		}
	}

}
