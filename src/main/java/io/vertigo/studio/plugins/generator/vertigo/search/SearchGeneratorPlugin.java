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
package io.vertigo.studio.plugins.generator.vertigo.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.search.FacetSketch;
import io.vertigo.studio.notebook.search.FacetedQuerySketch;
import io.vertigo.studio.notebook.search.SearchIndexSketch;
import io.vertigo.studio.plugins.generator.vertigo.search.model.FacetModel;
import io.vertigo.studio.plugins.generator.vertigo.search.model.FacetedQueryModel;
import io.vertigo.studio.plugins.generator.vertigo.search.model.SearchDtModel;
import io.vertigo.studio.plugins.generator.vertigo.search.model.SearchIndexModel;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Task.
 *
 * @author pchretien, mlaroche
 */
public final class SearchGeneratorPlugin implements GeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "javagen";

	/** {@inheritDoc} */
	@Override
	public void generate(
			final Notebook notebook,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(generatorConfig)
				.isNotNull(generatorResultBuilder);
		//-----
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.search.targetSubDir", DEFAULT_TARGET_SUBDIR);
		generateSearchAos(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private static void generateSearchAos(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {

		notebook.getAll(DtSketch.class)
				.stream()
				.filter(dtSketch -> dtSketch.getStereotype() == StudioStereotype.KeyConcept)
				.forEach(dtSketch -> generateSearchAo(notebook, targetSubDir, generatorConfig, generatorResultBuilder, dtSketch));

	}

	/**
	 * Génération d'un DAO c'est à dire des taches afférentes à un objet.
	 */
	private static void generateSearchAo(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final DtSketch dtSketch) {
		Assertion.check().isNotNull(dtSketch);

		final String dtPackageName = dtSketch.getPackageName();
		final String packageNamePrefix = generatorConfig.getProjectPackageName();
		Assertion.check()
				.isTrue(dtPackageName.startsWith(packageNamePrefix), "Package name {0}, must begin with normalised prefix: {1}", dtPackageName, packageNamePrefix)
				.isTrue(dtPackageName.substring(packageNamePrefix.length()).contains(".domain"), "Package name {0}, must contains the modifier .domain", dtPackageName);
		// ---
		//we need to find the featureName, aka between projectpackageName and .domain
		final String featureName = dtPackageName.substring(packageNamePrefix.length(), dtPackageName.indexOf(".domain"));
		if (!StringUtil.isBlank(featureName)) {
			Assertion.check().isTrue(featureName.lastIndexOf('.') == 0, "The feature {0} must not contain any dot", featureName.substring(1));
		}
		// the subpackage is what's behind the .domain
		final String subpackage = dtPackageName.substring(dtPackageName.indexOf(".domain") + ".domain".length());
		// breaking change -> need to redefine what's the desired folder structure in javagen...

		//On construit le nom du package à partir du package de la DT et de la feature.
		final String packageName = generatorConfig.getProjectPackageName() + featureName + ".search" + subpackage;

		final SearchDtModel searchDtModel = new SearchDtModel(dtSketch);

		final Optional<SearchIndexSketch> searchIndexSketchOpt = notebook.getAll(SearchIndexSketch.class)
				.stream()
				.filter(indexSketch -> indexSketch.getKeyConceptDtSketch().equals(dtSketch))
				.findFirst();

		final List<FacetedQueryModel> facetedQueryDefinitions = new ArrayList<>();
		for (final FacetedQuerySketch facetedQuerySketch : notebook.getAll(FacetedQuerySketch.class)) {
			if (facetedQuerySketch.getKeyConceptDtSketch().equals(dtSketch)) {
				final FacetedQueryModel templateFacetedQueryModel = new FacetedQueryModel(facetedQuerySketch, DomainUtil.createClassNameFromDtFunction(notebook));
				facetedQueryDefinitions.add(templateFacetedQueryModel);
			}
		}

		final List<FacetModel> facetDefinitions = new ArrayList<>();
		if (searchIndexSketchOpt.isPresent()) {
			for (final FacetSketch facetSketch : notebook.getAll(FacetSketch.class)) {
				if (facetSketch.getIndexDtSketch().equals(searchIndexSketchOpt.get().getIndexDtSketch())) {
					final FacetModel templateFacetedQueryDefinition = new FacetModel(facetSketch);
					facetDefinitions.add(templateFacetedQueryDefinition);
				}
			}
		}

		if (searchIndexSketchOpt.isPresent()) {

			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("packageName", packageName)
					.put("facetedQueryDefinitions", facetedQueryDefinitions)
					.put("facetDefinitions", facetDefinitions)
					.put("dtDefinition", searchDtModel)
					.put("indexDtDefinition", new SearchDtModel(searchIndexSketchOpt.get().getIndexDtSketch()))
					.put("searchIndexDefinition", new SearchIndexModel(searchIndexSketchOpt.get()))
					.put("hasCustomFacet", facetedQueryDefinitions.stream().anyMatch(FacetedQueryModel::hasCustomFacet))
					.put("hasRangeFacet", facetedQueryDefinitions.stream().anyMatch(FacetedQueryModel::hasRangeFacet))
					.build();

			FileGenerator.builder(generatorConfig)
					.withModel(model)
					.withFileName(searchDtModel.getClassSimpleName() + "SearchClient.java")
					.withGenSubDir(targetSubDir)
					.withPackageName(packageName)
					.withTemplateName(SearchGeneratorPlugin.class, "template/search_client.ftl")
					.build()
					.generateFile(generatorResultBuilder);
		}
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.search.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.search";
	}
}