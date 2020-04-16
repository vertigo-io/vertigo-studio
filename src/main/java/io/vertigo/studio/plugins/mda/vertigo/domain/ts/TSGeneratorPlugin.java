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
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.param.ParamValue;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.FileGenerator;
import io.vertigo.studio.impl.mda.FileGeneratorConfig;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioStereotype;
import io.vertigo.studio.metamodel.domain.masterdata.MasterDataValue;
import io.vertigo.studio.metamodel.domain.masterdata.StaticMasterData;
import io.vertigo.studio.plugins.mda.vertigo.domain.ts.model.TSMasterDataDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.ts.model.TSStudioDtDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Domain.
 *
 * @author rgrange, npiedeloup, pchretien
 */
public final class TSGeneratorPlugin implements GeneratorPlugin {
	private final String targetSubDir;
	private final boolean shouldGenerateDtResourcesTS;
	private final boolean shouldGenerateTsDtDefinitions;
	private final boolean shouldGenerateTsMasterData;

	/**
	 * Constructeur.
	 * @param targetSubDirOpt Repertoire de generation des fichiers de ce plugin
	 * @param generateDtResourcesTSOpt Si on génère les fichiers i18n pour les labels des champs en TS
	 * @param generateTsDtDefinitionsOpt Si on génère les classes JS.
	 */
	@Inject
	public TSGeneratorPlugin(
			@ParamValue("targetSubDir") final Optional<String> targetSubDirOpt,
			@ParamValue("generateDtResourcesTS") final Optional<Boolean> generateDtResourcesTSOpt,
			@ParamValue("generateTsDtDefinitions") final Optional<Boolean> generateTsDtDefinitionsOpt,
			@ParamValue("generateTsMasterData") final Optional<Boolean> generateTsMasterDataOpt) {
		//-----
		targetSubDir = targetSubDirOpt.orElse("tsgen");
		shouldGenerateDtResourcesTS = generateDtResourcesTSOpt.orElse(true); //true by default
		shouldGenerateTsDtDefinitions = generateTsDtDefinitionsOpt.orElse(true); //true by default
		shouldGenerateTsMasterData = generateTsMasterDataOpt.orElse(false);
	}

	/** {@inheritDoc} */
	@Override
	public void generate(
			final MetamodelRepository metamodelRepository,
			final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		Assertion.checkNotNull(fileGeneratorConfig);
		Assertion.checkNotNull(mdaResultBuilder);
		//-----
		/* Génération des ressources afférentes au DT mais pour la partie JS.*/
		if (shouldGenerateDtResourcesTS) {
			generateDtResourcesTS(metamodelRepository, targetSubDir, fileGeneratorConfig, mdaResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les définitions. */
		if (shouldGenerateTsDtDefinitions) {
			generateTsDtDefinitions(metamodelRepository, targetSubDir, fileGeneratorConfig, mdaResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les masterdatas. */
		if (shouldGenerateTsMasterData) {
			generateTsMasterData(metamodelRepository, fileGeneratorConfig, mdaResultBuilder);
		}
	}

	private static List<TSStudioDtDefinitionModel> getTsDtDefinitionModels(final MetamodelRepository metamodelRepository) {
		return DomainUtil.getDtDefinitions(metamodelRepository).stream()
				.map(TSStudioDtDefinitionModel::new)
				.collect(Collectors.toList());
	}

	private static void generateTsDtDefinitions(
			final MetamodelRepository metamodelRepository, final String targetSubDir, final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		for (final TSStudioDtDefinitionModel dtDefinitionModel : getTsDtDefinitionModels(metamodelRepository)) {
			generateTs(dtDefinitionModel, targetSubDir, fileGeneratorConfig, mdaResultBuilder);
		}
	}

	private void generateTsMasterData(
			final MetamodelRepository metamodelRepository,
			final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder) {

		final Map<String, Map<String, MasterDataValue>> staticMasterDataValues = metamodelRepository.getAll(StaticMasterData.class).stream().collect(Collectors.toMap(StaticMasterData::getEntityClassName, StaticMasterData::getValues));

		final List<TSMasterDataDefinitionModel> tsMasterDataDefinitionModels = metamodelRepository.getAll(StudioDtDefinition.class)
				.stream()
				.filter(dtDefinition -> dtDefinition.getStereotype() == StudioStereotype.StaticMasterData)
				.map(dtDefinition -> new TSMasterDataDefinitionModel(dtDefinition, staticMasterDataValues.getOrDefault(dtDefinition.getClassCanonicalName(), Collections.emptyMap())))
				.collect(Collectors.toList());

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("masterdatas", tsMasterDataDefinitionModels)
				.build();

		FileGenerator.builder(fileGeneratorConfig)
				.withModel(model)
				.withFileName("masterdata.ts")
				.withGenSubDir(targetSubDir)
				.withPackageName("")
				.withTemplateName(TSGeneratorPlugin.class, "template/ts_masterdata.ftl")
				.build()
				.generateFile(mdaResultBuilder);

	}

	private static void generateTs(final TSStudioDtDefinitionModel dtDefinitionModel, final String targetSubDir, final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("classSimpleName", "DtDefinitions")
				.put("dtDefinition", dtDefinitionModel)
				.build();

		FileGenerator.builder(fileGeneratorConfig)
				.withModel(model)
				.withFileName(dtDefinitionModel.getJsClassFileName() + ".ts")
				.withGenSubDir(targetSubDir)
				.withPackageName(fileGeneratorConfig.getProjectPackageName() + ".ui." + dtDefinitionModel.getFunctionnalPackageName() + ".config.entity-definition-gen")
				.withTemplateName(TSGeneratorPlugin.class, "template/ts.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	/**
	 * Génère les ressources JS pour les traductions.
	 * @param fileGeneratorConfig Configuration du domaine.
	 */
	private static void generateDtResourcesTS(
			final MetamodelRepository metamodelRepository,
			final String targetSubDir,
			final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder) {

		final Map<String, List<TSStudioDtDefinitionModel>> packageMap = new HashMap<>();
		for (final TSStudioDtDefinitionModel dtDefinitionModel : getTsDtDefinitionModels(metamodelRepository)) {
			final String packageName = dtDefinitionModel.getFunctionnalPackageName();
			packageMap.computeIfAbsent(packageName, o -> new ArrayList<>()).add(dtDefinitionModel);
		}

		final String simpleClassName = "DtDefinitions" + "Label";
		//final String packageName = fileGeneratorConfig.getProjectPackageName() + ".domain";

		for (final Entry<String, List<TSStudioDtDefinitionModel>> entry : packageMap.entrySet()) {
			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("packageName", entry.getKey())
					.put("simpleClassName", simpleClassName)
					.put("dtDefinitions", entry.getValue())
					.build();

			FileGenerator.builder(fileGeneratorConfig)
					.withModel(model)
					.withFileName(entry.getKey() + ".ts")
					.withGenSubDir(targetSubDir)
					.withPackageName(fileGeneratorConfig.getProjectPackageName() + ".ui." + entry.getKey() + ".i18n.generated")
					.withTemplateName(TSGeneratorPlugin.class, "template/propertiesTS.ftl")
					.build()
					.generateFile(mdaResultBuilder);
		}
	}

	@Override
	public void clean(final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		MdaUtil.deleteFiles(new File(fileGeneratorConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

}
