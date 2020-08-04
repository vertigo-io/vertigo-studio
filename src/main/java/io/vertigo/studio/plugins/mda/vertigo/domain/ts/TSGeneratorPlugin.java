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
package io.vertigo.studio.plugins.mda.vertigo.domain.ts;

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
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.masterdata.MasterDataValue;
import io.vertigo.studio.notebook.domain.masterdata.StaticMasterDataSketch;
import io.vertigo.studio.plugins.mda.vertigo.domain.ts.model.TSMasterDataDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.ts.model.TSStudioDtDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Domain.
 *
 * @author rgrange, npiedeloup, pchretien
 */
public final class TSGeneratorPlugin implements MdaGeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "tsgen";

	/** {@inheritDoc} */
	@Override
	public void generate(
			final Notebook notebook,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(mdaConfig)
				.isNotNull(mdaResultBuilder);
		//-----
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.ts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//----
		/* Génération des ressources afférentes au DT mais pour la partie JS.*/
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.ts.generateDtResourcesTS", true)) {//true by default
			generateDtResourcesTS(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les définitions. */
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.ts.generateTsDtDefinitions", true)) {//true by default
			generateTsDtDefinitions(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les masterdatas. */
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.ts.generateTsMasterData", false)) {//false by default
			generateTsMasterData(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
		}
	}

	private static List<TSStudioDtDefinitionModel> getTsDtDefinitionModels(final Notebook notebook) {
		return DomainUtil.getDtSketchs(notebook)
				.stream()
				.map(TSStudioDtDefinitionModel::new)
				.collect(Collectors.toList());
	}

	private static void generateTsDtDefinitions(
			final Notebook notebook, final String targetSubDir, final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		for (final TSStudioDtDefinitionModel dtDefinitionModel : getTsDtDefinitionModels(notebook)) {
			generateTs(dtDefinitionModel, targetSubDir, mdaConfig, mdaResultBuilder);
		}
	}

	private static void generateTsMasterData(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		final Map<String, Map<String, MasterDataValue>> staticMasterDataValues = notebook.getAll(StaticMasterDataSketch.class)
				.stream()
				.collect(Collectors.toMap(StaticMasterDataSketch::getEntityClassName, StaticMasterDataSketch::getValues));

		final List<TSMasterDataDefinitionModel> tsMasterDataDefinitionModels = notebook.getAll(DtSketch.class)
				.stream()
				.filter(dtSketch -> dtSketch.getStereotype() == StudioStereotype.StaticMasterData)
				.map(dtSketch -> new TSMasterDataDefinitionModel(dtSketch, staticMasterDataValues.getOrDefault(dtSketch.getClassCanonicalName(), Collections.emptyMap())))
				.collect(Collectors.toList());

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("masterdatas", tsMasterDataDefinitionModels)
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName("masterdata.ts")
				.withGenSubDir(targetSubDir)
				.withPackageName("")
				.withTemplateName(TSGeneratorPlugin.class, "template/ts_masterdata.ftl")
				.build()
				.generateFile(mdaResultBuilder);

	}

	private static void generateTs(final TSStudioDtDefinitionModel dtDefinitionModel, final String targetSubDir, final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("classSimpleName", "DtDefinitions")
				.put("dtDefinition", dtDefinitionModel)
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(dtDefinitionModel.getJsClassFileName() + ".ts")
				.withGenSubDir(targetSubDir)
				.withPackageName(mdaConfig.getProjectPackageName() + ".ui." + dtDefinitionModel.getFunctionnalPackageName() + ".config.entity-definition-gen")
				.withTemplateName(TSGeneratorPlugin.class, "template/ts.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	/**
	 * Génère les ressources JS pour les traductions.
	 * @param mdaConfig Configuration du domaine.
	 */
	private static void generateDtResourcesTS(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		final Map<String, List<TSStudioDtDefinitionModel>> packageMap = new HashMap<>();
		for (final TSStudioDtDefinitionModel dtDefinitionModel : getTsDtDefinitionModels(notebook)) {
			final String packageName = dtDefinitionModel.getFunctionnalPackageName();
			packageMap.computeIfAbsent(packageName, o -> new ArrayList<>()).add(dtDefinitionModel);
		}

		final String simpleClassName = "DtDefinitions" + "Label";
		//final String packageName = mdaConfig.getProjectPackageName() + ".domain";

		for (final Entry<String, List<TSStudioDtDefinitionModel>> entry : packageMap.entrySet()) {
			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("packageName", entry.getKey())
					.put("simpleClassName", simpleClassName)
					.put("dtDefinitions", entry.getValue())
					.build();

			MdaFileGenerator.builder(mdaConfig)
					.withModel(model)
					.withFileName(entry.getKey() + ".ts")
					.withGenSubDir(targetSubDir)
					.withPackageName(mdaConfig.getProjectPackageName() + ".ui." + entry.getKey() + ".i18n.generated")
					.withTemplateName(TSGeneratorPlugin.class, "template/propertiesTS.ftl")
					.build()
					.generateFile(mdaResultBuilder);
		}
	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.ts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.ts";
	}

}
