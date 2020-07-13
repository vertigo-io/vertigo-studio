package io.vertigo.studio.impl.source;

import java.util.List;
import java.util.Set;

import io.vertigo.core.node.component.Plugin;
import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.NotebookSource;

public interface NotebookSourceReaderPlugin extends Plugin {

	Set<String> getHandledSourceTypes();

	List<SketchSupplier> parseResources(List<NotebookSource> resources, Notebook notebook);

}
