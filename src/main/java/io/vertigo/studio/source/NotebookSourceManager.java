package io.vertigo.studio.source;

import java.util.List;

import io.vertigo.core.node.component.Manager;
import io.vertigo.studio.notebook.Notebook;

public interface NotebookSourceManager extends Manager {

	Notebook read(List<NotebookSource> notebookSources);

}
