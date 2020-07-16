package io.vertigo.studio.impl.notebook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.NotebookManager;

public class NotebookManagerImpl implements NotebookManager {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public String toJson(final Notebook notebook) {
		return GSON.toJson(notebook);
	}

}
