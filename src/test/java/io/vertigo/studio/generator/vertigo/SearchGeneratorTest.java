/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2026, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.generator.vertigo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorManager;
import io.vertigo.studio.generator.GeneratorResult;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test la génération du SearchClient et du FacetDefinitionProvider.
 */
public final class SearchGeneratorTest {

	private static final String SEARCH_CLIENT_RELATIVE_PATH =
			"javagen/io/vertigo/studio/search/car/CarSearchClient.java";

	private static final String FACET_DEFINITION_PROVIDER_RELATIVE_PATH =
			"javagen/io/vertigo/studio/search/car/CarFacetDefinitionProvider.java";

	@TempDir
	Path targetGenDir;

	@Test
	public void shouldGenerateFacetDefinitionProviderWithFacetsOnly() throws IOException {
		final String generatedContent = generate(
				"io/vertigo/studio/source/vertigo/data/searchFacetsOnly.kpr",
				FACET_DEFINITION_PROVIDER_RELATIVE_PATH);

		assertTrue(generatedContent.contains("class CarFacetDefinitionProvider implements SimpleDefinitionProvider"),
				"FacetDefinitionProvider must be generated without index");
		assertTrue(generatedContent.contains("provideDefinitions"), "Definitions must be provided via provideDefinitions()");
		assertTrue(generatedContent.contains("CollectionsManager.facetList()"),
				"Generated class must document the in-memory facet mode");
		assertFalse(generatedContent.contains("implements Component"), "Component must not be implemented without index");
		assertFalse(generatedContent.contains("SearchManager"), "SearchManager must not be referenced without index");
		assertFalse(generatedContent.contains("SearchIndexDefinitionSupplier"), "Search index definitions must not be generated");
		assertFalse(generatedContent.contains("FacetedQueryDefinitionSupplier"), "Faceted query supplier must not be used without index");
		assertTrue(generatedContent.contains("FacetTermDefinitionSupplier"), "Facet definitions must be generated");
		assertTrue(generatedContent.contains("new FacetedQueryDefinition"), "Faceted query definitions must be generated");
		assertTrue(generatedContent.contains("QryCarFacetsOnly"), "Faceted query name must be present");
		assertTrue(generatedContent.contains("\"*\""),
				"List filter query must be a wildcard when no index is declared");
	}

	@Test
	public void shouldGenerateSearchClientWithIndexAndFacets() throws IOException {
		final String generatedContent = generate(
				"io/vertigo/studio/source/vertigo/data/searchIndexAndFacets.kpr",
				SEARCH_CLIENT_RELATIVE_PATH);

		assertTrue(generatedContent.contains("class CarSearchClient implements Component, DefinitionProvider"),
				"SearchClient must be generated when an index is declared");
		assertTrue(generatedContent.contains("private final SearchManager"), "SearchManager must be present when an index is declared");
		assertTrue(generatedContent.contains("SearchIndexDefinitionSupplier"), "Search index definitions must be generated");
		assertTrue(generatedContent.contains("loadListIdxCar"), "Search load methods must be generated with index");
		assertTrue(generatedContent.contains("FacetTermDefinitionSupplier"), "Facet definitions must be generated");
		assertTrue(generatedContent.contains("FacetedQueryDefinitionSupplier"), "Faceted query definitions must be generated");
		assertTrue(generatedContent.contains("IdxCar"), "Search index name must be present");
		assertTrue(generatedContent.contains("QryCar"), "Faceted query name must be present");
		assertTrue(generatedContent.contains(".withListFilterBuilderQuery(\"year:#+query*#\")"),
				"List filter query must be generated when an index is declared");
	}

	private String generate(final String searchKprPath, final String expectedRelativePath) throws IOException {
		try (AutoCloseableNode studioApp = new AutoCloseableNode(buildNodeConfig())) {
			final SourceManager sourceManager = studioApp.getComponentSpace().resolve(SourceManager.class);
			final GeneratorManager generatorManager = studioApp.getComponentSpace().resolve(GeneratorManager.class);

			final List<Source> resources = List.of(
					Source.of("kpr", "io/vertigo/studio/source/vertigo/data/model.kpr"),
					Source.of("kpr", searchKprPath));

			final GeneratorConfig generatorConfig = GeneratorConfig.builder("io.vertigo.studio")
					.withTargetGenDir(targetGenDir.toString() + "/")
					.addProperty("vertigo.search", "true")
					.build();

			final Notebook notebook = sourceManager.read(resources);
			final GeneratorResult result = generatorManager.generate(notebook, generatorConfig);

			assertEquals(0, result.errorFiles(), "Generation must not fail");
			assertTrue(result.createdFiles() + result.updatedFiles() + result.identicalFiles() > 0,
					"At least one file must be generated");

			final Path generatedFile = targetGenDir.resolve(expectedRelativePath);
			assertTrue(Files.exists(generatedFile), "Generated file must exist at " + generatedFile);
			return Files.readString(generatedFile);
		}
	}

	private static NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
				.addModule(new StudioFeatures()
						.withSource()
						.withVertigoSource()
						.withGenerator()
						.withVertigoMda()
						.build())
				.build();
	}

}
