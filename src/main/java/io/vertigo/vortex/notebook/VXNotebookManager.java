package io.vertigo.vortex.notebook;

import io.vertigo.core.node.component.Manager;

public interface VXNotebookManager extends Manager {
	VXNotebook createNotebook(VXNotebookConfig notebookConfig) throws Exception;
}
