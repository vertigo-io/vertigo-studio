/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.studio.impl.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

public class SourceManagerImpl implements SourceManager {

	private final Map<String, SourceReaderPlugin> metamodelResourceParserPluginsByType = new HashMap<>();

	@Inject
	public SourceManagerImpl(
			final List<SourceReaderPlugin> sourceReaderPlugins) {
		Assertion.check().isNotNull(sourceReaderPlugins);
		//---
		for (final SourceReaderPlugin sourceReaderPlugin : sourceReaderPlugins) {
			for (final String resourceType : sourceReaderPlugin.getHandledSourceTypes()) {
				Assertion.check().isFalse(metamodelResourceParserPluginsByType.containsKey(resourceType),
						"Only one plugin can manage the ResourceType {0}", resourceType);
				metamodelResourceParserPluginsByType.put(resourceType, sourceReaderPlugin);
			}
		}
	}

	@Override
	public Notebook read(final List<Source> resources) {
		final Map<SourceReaderPlugin, List<Source>> resourcesByPlugin = resources
				.stream()
				.collect(Collectors.groupingBy(resource -> metamodelResourceParserPluginsByType.get(resource.getType())));

		final Notebook notebook = new Notebook();
		resourcesByPlugin.entrySet()
				.stream()
				.flatMap(entry -> entry.getKey().parseResources(entry.getValue(), notebook))
				.forEach(notebook::register);
		return notebook;
	}

}
