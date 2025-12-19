package io.vertigo.vortex.plugins.notebook.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.impl.notebook.NotebookReaderPlugin;
import io.vertigo.vortex.notebook.VXNotebook;
import io.vertigo.vortex.notebook.VXNotebookConfig;

public class JsonNotebookReaderPlugin implements NotebookReaderPlugin {
	@Override
	public VXNotebook createNotebook(VXNotebookConfig notebookConfig) throws Exception {
		Assertion.check()
				.isNotNull(notebookConfig);
		return FileToRaw
				.from(notebookConfig)
				.toRaw()
				.toNotebook();
	}

}
