package io.vertigo.vortex.impl.notebook;

import io.vertigo.core.node.component.Plugin;
import io.vertigo.vortex.notebook.VXNotebook;
import io.vertigo.vortex.notebook.VXNotebookConfig;

public interface NotebookReaderPlugin extends Plugin {
	VXNotebook createNotebook(VXNotebookConfig notebookConfig) throws Exception;
}
