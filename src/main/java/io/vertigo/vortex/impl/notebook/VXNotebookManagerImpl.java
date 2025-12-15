package io.vertigo.vortex.impl.notebook;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.notebook.VXNotebookConfig;
import io.vertigo.vortex.notebook.VXNotebookManager;
import io.vertigo.vortex.notebook.VXNotebook;

public class VXNotebookManagerImpl implements VXNotebookManager {

	@Override
	public VXNotebook createNotebook(VXNotebookConfig notebookConfig) throws Exception {
		Assertion.check()
				.isNotNull(notebookConfig);
		//---
		return FileToRaw
				.from(notebookConfig)
				.toRaw()
				.toNotebook();
	}

}
