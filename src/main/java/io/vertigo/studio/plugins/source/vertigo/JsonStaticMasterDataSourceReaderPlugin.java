/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.google.gson.Gson;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.core.util.FileUtil;
import io.vertigo.studio.impl.source.SourceReaderPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.notebook.domain.masterdata.MasterDataValue;
import io.vertigo.studio.notebook.domain.masterdata.StaticMasterDataSketch;
import io.vertigo.studio.source.Source;

/**
 * Plugin for retrieving masterdata values from a json file.
 * @author mlaroche
 *
 */
public final class JsonStaticMasterDataSourceReaderPlugin implements SourceReaderPlugin {

	private final ResourceManager resourceManager;

	private final Gson gson = new Gson();

	/**
	 * Constructor
	 * @param resourceManager resourceManager
	 * @param fileName the json file where masterdata values are stored
	 */
	@Inject
	public JsonStaticMasterDataSourceReaderPlugin(final ResourceManager resourceManager) {
		Assertion.check().isNotNull(resourceManager);
		//---
		this.resourceManager = resourceManager;
	}

	@Override
	public Set<String> getHandledSourceTypes() {
		return Set.of("staticMasterData");
	}

	@Override
	public Stream<Sketch> parseResources(final List<Source> sources, final Notebook notebook) {
		Assertion.check()
				.isNotNull(sources)
				.isNotNull(notebook);
		//---
		final JsonMasterDataValues result = new JsonMasterDataValues();
		for (final Source source : sources) {
			final String jsonFileAsString = FileUtil.read(resourceManager.resolve(source.getPath()));
			final JsonMasterDataValues masterDataValues = gson.fromJson(jsonFileAsString, JsonMasterDataValues.class);

			// we aggregate the results of all files
			masterDataValues.entrySet()
					.stream()
					.forEach(entry -> {
						result.computeIfPresent(entry.getKey(), (key, value) -> {
							entry.getValue()
									.entrySet()
									.stream()
									// we check that a name is unique for an object type
									.peek(newEntry -> Assertion.check().isFalse(value.containsKey(newEntry.getKey()), "Value with name '{0}' for MasterData '{1}' is declared in two files", newEntry.getKey(), entry.getKey()))
									.forEach(newEntry -> value.put(newEntry.getKey(), newEntry.getValue()));
							return value;
						});
						result.computeIfAbsent(entry.getKey(), key -> entry.getValue());
					});

		}
		return result.entrySet()
				.stream()
				.map(entry -> new StaticMasterDataSketch(entry.getKey(), entry.getValue()));
	}

	public static class JsonMasterDataValues extends HashMap<String, Map<String, MasterDataValue>> {
		private static final long serialVersionUID = 4030075643175718355L;

	}

}
