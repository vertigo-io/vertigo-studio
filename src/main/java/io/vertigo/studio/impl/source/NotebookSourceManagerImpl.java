package io.vertigo.studio.impl.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.NotebookSource;
import io.vertigo.studio.source.NotebookSourceManager;

public class NotebookSourceManagerImpl implements NotebookSourceManager {

	private final Map<String, NotebookSourceReaderPlugin> metamodelResourceParserPluginsByType = new HashMap<>();

	@Inject
	public NotebookSourceManagerImpl(
			final List<NotebookSourceReaderPlugin> notebookSourceReaderPlugins) {
		Assertion.check().isNotNull(notebookSourceReaderPlugins);
		//---
		for (final NotebookSourceReaderPlugin notebookSourceReaderPlugin : notebookSourceReaderPlugins) {
			for (final String resourceType : notebookSourceReaderPlugin.getHandledSourceTypes()) {
				Assertion.check().isFalse(metamodelResourceParserPluginsByType.containsKey(resourceType),
						"Only one plugin can manage the ResourceType {0}", resourceType);
				metamodelResourceParserPluginsByType.put(resourceType, notebookSourceReaderPlugin);
			}
		}
	}

	@Override
	public Notebook read(final List<NotebookSource> resources) {
		final Map<NotebookSourceReaderPlugin, List<NotebookSource>> resourcesByPlugin = resources
				.stream()
				.collect(Collectors.groupingBy(resource -> metamodelResourceParserPluginsByType.get(resource.getType())));

		final Notebook notebook = new Notebook();
		resourcesByPlugin.entrySet()
				.stream()
				.flatMap(entry -> entry.getKey().parseResources(entry.getValue(), notebook).stream())
				.map(sketchSupplier -> sketchSupplier.get(notebook))
				.forEach(notebook::register);
		return notebook;
	}

}
