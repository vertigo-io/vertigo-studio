package io.vertigo.vortex;

import java.io.File;
import java.util.List;

import io.vertigo.vortex.impl.notebook.NotebookReaderPlugin;
import io.vertigo.vortex.impl.notebook.VXNotebookManagerImpl;
import io.vertigo.vortex.notebook.VXNotebook;
import io.vertigo.vortex.notebook.VXNotebookConfig;
import io.vertigo.vortex.notebook.VXNotebookManager;
import io.vertigo.vortex.plugins.notebook.json.JsonNotebookReaderPlugin;

public class Vortex {
	public static String MODEL_BOOK = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\module-book.json";
	public static String MODEL_AUTHOR = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\module-author.json";
	public static String LIBRARY_CORE = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\library-core.json";

	public static void main(final String[] args) throws Exception {
		final VXNotebookConfig notebookConfig = new VXNotebookConfig(
				List.of(new File(LIBRARY_CORE)),
				List.of(new File(MODEL_AUTHOR), new File(MODEL_BOOK)));

		final NotebookReaderPlugin notebookReaderPlugin = new JsonNotebookReaderPlugin();
		final VXNotebookManager notebookManager = new VXNotebookManagerImpl(notebookReaderPlugin);
		final VXNotebook notebook = notebookManager.createNotebook(notebookConfig);
		System.out.println(notebook);
	}
}
