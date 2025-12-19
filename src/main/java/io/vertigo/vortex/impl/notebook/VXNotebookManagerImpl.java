package io.vertigo.vortex.impl.notebook;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXNotebook;
import io.vertigo.vortex.notebook.VXNotebookConfig;
import io.vertigo.vortex.notebook.VXNotebookManager;
import jakarta.inject.Inject;

public class VXNotebookManagerImpl implements VXNotebookManager {
	private final NotebookReaderPlugin notebookReaderPlugin;

	@Inject
	public VXNotebookManagerImpl(NotebookReaderPlugin notebookReaderPlugin) {
		Assertion.check().isNotNull(notebookReaderPlugin);
		//---
		this.notebookReaderPlugin = notebookReaderPlugin;
	}

	@Override
	public VXNotebook createNotebook(VXNotebookConfig notebookConfig) throws Exception {
		Assertion.check()
				.isNotNull(notebookConfig);
		return notebookReaderPlugin.createNotebook(notebookConfig);
	}

}
