package io.vertigo.studio.impl.source;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import io.vertigo.core.node.component.Plugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.source.Source;

public interface SourceReaderPlugin extends Plugin {

	Set<String> getHandledSourceTypes();

	Stream<? extends Sketch> parseResources(List<Source> resources, Notebook notebook);

}
