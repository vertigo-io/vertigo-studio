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
package io.vertigo.studio.plugins.mda.vertigo.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioStereotype;
import io.vertigo.studio.metamodel.search.StudioFacetDefinition;
import io.vertigo.studio.metamodel.search.StudioFacetedQueryDefinition;
import io.vertigo.studio.metamodel.search.StudioSearchIndexDefinition;
import io.vertigo.studio.plugins.mda.vertigo.search.model.FacetDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.search.model.FacetedQueryDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.search.model.SearchDtDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.search.model.SearchIndexDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Task.
 *
 * @author pchretien, mlaroche
 */
public final class SearchGeneratorPlugin implements MdaGeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "javagen";

	/** {@inheritDoc} */
	@Override
	public void generate(
			final DefinitionSpace definitionSpace,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		Assertion.check()
				.notNull(definitionSpace)
				.notNull(mdaConfig)
				.notNull(mdaResultBuilder);
		//-----
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.search.targetSubDir", DEFAULT_TARGET_SUBDIR);
		generateSearchAos(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private static void generateSearchAos(
			final DefinitionSpace definitionSpace,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		definitionSpace.getAll(StudioDtDefinition.class)
				.stream()
				.filter(dtDefinition -> dtDefinition.getStereotype() == StudioStereotype.KeyConcept)
				.forEach(dtDefinition -> generateSearchAo(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder, dtDefinition));

	}

	/**
	 * Génération d'un DAO c'est à dire des taches afférentes à un objet.
	 */
	private static void generateSearchAo(
			final DefinitionSpace definitionSpace,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final StudioDtDefinition dtDefinition) {
		Assertion.check().notNull(dtDefinition);

		final String definitionPackageName = dtDefinition.getPackageName();
		final String packageNamePrefix = mdaConfig.getProjectPackageName();
		Assertion.check()
				.argument(definitionPackageName.startsWith(packageNamePrefix), "Package name {0}, must begin with normalised prefix: {1}", definitionPackageName, packageNamePrefix)
				.argument(definitionPackageName.substring(packageNamePrefix.length()).contains(".domain"), "Package name {0}, must contains the modifier .domain", definitionPackageName);
		// ---
		//we need to find the featureName, aka between projectpackageName and .domain
		final String featureName = definitionPackageName.substring(packageNamePrefix.length(), definitionPackageName.indexOf(".domain"));
		if (!StringUtil.isEmpty(featureName)) {
			Assertion.check().state(featureName.lastIndexOf('.') == 0, "The feature {0} must not contain any dot", featureName.substring(1));
		}
		// the subpackage is what's behind the .domain
		final String subpackage = definitionPackageName.substring(definitionPackageName.indexOf(".domain") + ".domain".length());
		// breaking change -> need to redefine what's the desired folder structure in javagen...

		//On construit le nom du package à partir du package de la DT et de la feature.
		final String packageName = mdaConfig.getProjectPackageName() + featureName + ".search" + subpackage;

		final SearchDtDefinitionModel searchDtDefinitionModel = new SearchDtDefinitionModel(dtDefinition);

		final Optional<StudioSearchIndexDefinition> searchIndexDefinitionOpt = definitionSpace.getAll(StudioSearchIndexDefinition.class).stream()
				.filter(indexDefinition -> indexDefinition.getKeyConceptDtDefinition().equals(dtDefinition))
				.findFirst();

		final List<FacetedQueryDefinitionModel> facetedQueryDefinitions = new ArrayList<>();
		for (final StudioFacetedQueryDefinition facetedQueryDefinition : definitionSpace.getAll(StudioFacetedQueryDefinition.class)) {
			if (facetedQueryDefinition.getKeyConceptDtDefinition().equals(dtDefinition)) {
				final FacetedQueryDefinitionModel templateFacetedQueryDefinition = new FacetedQueryDefinitionModel(facetedQueryDefinition, DomainUtil.createClassNameFromDtFunction(definitionSpace));
				facetedQueryDefinitions.add(templateFacetedQueryDefinition);
			}
		}

		final List<FacetDefinitionModel> facetDefinitions = new ArrayList<>();
		if (searchIndexDefinitionOpt.isPresent()) {
			for (final StudioFacetDefinition facetDefinition : definitionSpace.getAll(StudioFacetDefinition.class)) {
				if (facetDefinition.getIndexDtDefinition().equals(searchIndexDefinitionOpt.get().getIndexDtDefinition())) {
					final FacetDefinitionModel templateFacetedQueryDefinition = new FacetDefinitionModel(facetDefinition);
					facetDefinitions.add(templateFacetedQueryDefinition);
				}
			}
		}

		if (searchIndexDefinitionOpt.isPresent()) {

			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("packageName", packageName)
					.put("facetedQueryDefinitions", facetedQueryDefinitions)
					.put("facetDefinitions", facetDefinitions)
					.put("dtDefinition", searchDtDefinitionModel)
					.put("indexDtDefinition", new SearchDtDefinitionModel(searchIndexDefinitionOpt.get().getIndexDtDefinition()))
					.put("searchIndexDefinition", new SearchIndexDefinitionModel(searchIndexDefinitionOpt.get()))
					.build();

			MdaFileGenerator.builder(mdaConfig)
					.withModel(model)
					.withFileName(searchDtDefinitionModel.getClassSimpleName() + "SearchClient.java")
					.withGenSubDir(targetSubDir)
					.withPackageName(packageName)
					.withTemplateName(SearchGeneratorPlugin.class, "template/search_client.ftl")
					.build()
					.generateFile(mdaResultBuilder);
		}
	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.search.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.search";
	}
}
