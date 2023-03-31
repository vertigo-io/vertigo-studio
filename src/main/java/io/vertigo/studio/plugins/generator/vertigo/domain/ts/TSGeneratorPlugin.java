/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.ts;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.masterdata.MasterDataValue;
import io.vertigo.studio.notebook.domain.masterdata.StaticMasterDataSketch;
import io.vertigo.studio.plugins.generator.vertigo.domain.ts.model.TSMasterDataModel;
import io.vertigo.studio.plugins.generator.vertigo.domain.ts.model.TSStudioDtModel;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Domain.
 *
 * @author rgrange, npiedeloup, pchretien
 */
public final class TSGeneratorPlugin implements GeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "tsgen";

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
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.ts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//----
		/* Génération des ressources afférentes au DT mais pour la partie JS.*/
		if (generatorConfig.getOrDefaultAsBoolean("vertigo.domain.ts.generateDtResourcesTS", true)) {//true by default
			generateDtResourcesTS(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les définitions. */
		if (generatorConfig.getOrDefaultAsBoolean("vertigo.domain.ts.generateTsDtDefinitions", true)) {//true by default
			generateTsDtDefinitions(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les masterdatas. */
		if (generatorConfig.getOrDefaultAsBoolean("vertigo.domain.ts.generateTsMasterData", false)) {//false by default
			generateTsMasterData(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		}
	}

	private static List<TSStudioDtModel> getTsDtDefinitionModels(final Notebook notebook, final GeneratorConfig generatorConfig) {
		return DomainUtil.getDtSketchs(notebook)
				.stream()
				.filter(dtSketch -> dtSketch.getPackageName().startsWith(generatorConfig.getProjectPackageName())) // only the one for my project
				.map(TSStudioDtModel::new)
				.collect(Collectors.toList());
	}

	private static void generateTsDtDefinitions(
			final Notebook notebook, final String targetSubDir, final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		for (final TSStudioDtModel dtDefinitionModel : getTsDtDefinitionModels(notebook, generatorConfig)) {
			generateTs(dtDefinitionModel, targetSubDir, generatorConfig, generatorResultBuilder);
		}
	}

	private static void generateTsMasterData(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {

		final Map<String, Map<String, MasterDataValue>> staticMasterDataValues = notebook.getAll(StaticMasterDataSketch.class)
				.stream()
				.collect(Collectors.toMap(StaticMasterDataSketch::getEntityClassName, StaticMasterDataSketch::getValues));

		final List<TSMasterDataModel> tsMasterDataModels = notebook.getAll(DtSketch.class)
				.stream()
				.filter(dtSketch -> dtSketch.getStereotype() == StudioStereotype.StaticMasterData)
				.map(dtSketch -> new TSMasterDataModel(dtSketch, staticMasterDataValues.getOrDefault(dtSketch.getClassCanonicalName(), Collections.emptyMap())))
				.collect(Collectors.toList());

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("masterdatas", tsMasterDataModels)
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName("masterdata.ts")
				.withGenSubDir(targetSubDir)
				.withPackageName("")
				.withTemplateName(TSGeneratorPlugin.class, "template/ts_masterdata.ftl")
				.build()
				.generateFile(generatorResultBuilder);

	}

	private static void generateTs(final TSStudioDtModel dtDefinitionModel, final String targetSubDir, final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("classSimpleName", "DtDefinitions")
				.put("dtDefinition", dtDefinitionModel)
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(dtDefinitionModel.getJsClassFileName() + ".ts")
				.withGenSubDir(targetSubDir)
				.withPackageName(generatorConfig.getProjectPackageName() + ".ui." + dtDefinitionModel.getFunctionnalPackageName() + ".config.entity-definition-gen")
				.withTemplateName(TSGeneratorPlugin.class, "template/ts.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	/**
	 * Génère les ressources JS pour les traductions.
	 * @param generatorConfig Configuration du domaine.
	 */
	private static void generateDtResourcesTS(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {

		final Map<String, List<TSStudioDtModel>> packageMap = new HashMap<>();
		for (final TSStudioDtModel dtDefinitionModel : getTsDtDefinitionModels(notebook, generatorConfig)) {
			final String packageName = dtDefinitionModel.getFunctionnalPackageName();
			packageMap.computeIfAbsent(packageName, o -> new ArrayList<>()).add(dtDefinitionModel);
		}

		final String simpleClassName = "DtDefinitions" + "Label";
		//final String packageName = mdaConfig.getProjectPackageName() + ".domain";

		for (final Entry<String, List<TSStudioDtModel>> entry : packageMap.entrySet()) {
			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("packageName", entry.getKey())
					.put("simpleClassName", simpleClassName)
					.put("dtDefinitions", entry.getValue())
					.build();

			FileGenerator.builder(generatorConfig)
					.withModel(model)
					.withFileName(entry.getKey() + ".ts")
					.withGenSubDir(targetSubDir)
					.withPackageName(generatorConfig.getProjectPackageName() + ".ui." + entry.getKey() + ".i18n.generated")
					.withTemplateName(TSGeneratorPlugin.class, "template/propertiesTS.ftl")
					.build()
					.generateFile(generatorResultBuilder);
		}
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.ts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.ts";
	}

}
