package io.vertigo.studio.source;

import java.util.List;

import io.vertigo.core.node.component.Manager;
import io.vertigo.studio.notebook.Notebook;

public interface SourceManager extends Manager {

	Notebook read(List<Source> sources);

}
