/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
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
package io.vertigo.studio.plugins.metamodel.vertigo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.gson.Gson;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.studio.impl.metamodel.MetamodelResourceParserPlugin;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.domain.masterdata.MasterDataValue;
import io.vertigo.studio.metamodel.domain.masterdata.StaticMasterData;

/**
 * Plugin for retrieving masterdata values from a json file.
 * @author mlaroche
 *
 */
public final class JsonStaticMasterDataParserPlugin implements MetamodelResourceParserPlugin {

	private final ResourceManager resourceManager;

	private final Gson gson = new Gson();

	/**
	 * Constructor
	 * @param resourceManager resourceManager
	 * @param fileName the json file where masterdata values are stored
	 */
	@Inject
	public JsonStaticMasterDataParserPlugin(
			final ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	private static String parseFile(final URL url) {
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			final StringBuilder buff = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				buff.append(line);
				line = reader.readLine();
				buff.append("\r\n");
			}
			return buff.toString();
		} catch (final IOException e) {
			throw WrappedException.wrap(e, "Error reading json file : '{0}'", url);
		}
	}

	@Override
	public Set<String> getHandledResourceTypes() {
		return Set.of("staticMasterData");
	}

	@Override
	public List<DefinitionSupplier> parseResources(final List<MetamodelResource> resources, final MetamodelRepository metamodelRepository) {

		final JsonMasterDataValues result = new JsonMasterDataValues();
		for (final MetamodelResource resource : resources) {
			final String jsonFileAsString = parseFile(resourceManager.resolve(resource.getPath()));
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
									.peek(newEntry -> Assertion.check().state(!value.containsKey(newEntry.getKey()), "Value with name '{0}' for MasterData '{1}' is declared in two files", newEntry.getKey(), entry.getKey()))
									.forEach(newEntry -> value.put(newEntry.getKey(), newEntry.getValue()));
							return value;
						});
						result.computeIfAbsent(entry.getKey(), key -> entry.getValue());
					});

		}
		return result.entrySet().stream()
				.map(entry -> (DefinitionSupplier) dS -> new StaticMasterData(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	public static class JsonMasterDataValues extends HashMap<String, Map<String, MasterDataValue>> {
		private static final long serialVersionUID = 4030075643175718355L;

	}

}
