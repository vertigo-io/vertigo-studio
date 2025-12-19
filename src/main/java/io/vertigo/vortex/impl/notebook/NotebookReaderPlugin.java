package io.vertigo.vortex.impl.notebook;

import io.vertigo.vortex.notebook.VXNotebook;
import io.vertigo.vortex.notebook.VXNotebookConfig;

public interface NotebookReaderPlugin {
	VXNotebook createNotebook(VXNotebookConfig notebookConfig) throws Exception;
}
