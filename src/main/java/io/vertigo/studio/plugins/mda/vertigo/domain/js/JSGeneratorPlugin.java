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
package io.vertigo.studio.plugins.mda.vertigo.domain.js;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.FileGenerator;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.plugins.mda.vertigo.domain.js.model.JSDtDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Domain.
 *
 * @author pchretien, mlaroche
 */
public final class JSGeneratorPlugin implements GeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "jsgen";

	/** {@inheritDoc} */
	@Override
	public void generate(
			final MetamodelRepository metamodelRepository,
			final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		Assertion.checkNotNull(mdaConfig);
		Assertion.checkNotNull(mdaResultBuilder);
		//-----
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.js.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		/* Génération des ressources afférentes au DT mais pour la partie JS.*/
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.js.generateDtResourcesJS", true)) {//true by default
			generateDtResourcesJS(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les définitions. */
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.js.generateJsDtDefinitions", true)) {//true by default
			generateJsDtDefinitions(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder);
		}
	}

	private static List<JSDtDefinitionModel> getJsDtDefinitionModels(final MetamodelRepository metamodelRepository) {
		return DomainUtil.getDtDefinitions(metamodelRepository).stream()
				.map(JSDtDefinitionModel::new)
				.collect(Collectors.toList());
	}

	private static void generateJsDtDefinitions(
			final MetamodelRepository metamodelRepository,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("packageName", mdaConfig.getProjectPackageName() + ".domain")
				.put("classSimpleName", "DtDefinitions")
				.put("dtDefinitions", getJsDtDefinitionModels(metamodelRepository))
				.build();

		FileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName("DtDefinitions.js")
				.withGenSubDir(targetSubDir)
				.withPackageName(mdaConfig.getProjectPackageName() + ".domain")
				.withTemplateName(JSGeneratorPlugin.class, "template/js.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	/**
	 * Génère les ressources JS pour les traductions.
	 * @param mdaConfig Configuration du domaine.
	 */
	private static void generateDtResourcesJS(
			final MetamodelRepository metamodelRepository,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		final String simpleClassName = "DtDefinitions" + "Label";
		final String packageName = mdaConfig.getProjectPackageName() + ".domain";

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("packageName", packageName)
				.put("simpleClassName", simpleClassName)
				.put("dtDefinitions", getJsDtDefinitionModels(metamodelRepository))
				.build();

		FileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(simpleClassName + ".js")
				.withGenSubDir(targetSubDir)
				.withPackageName(packageName)
				.withTemplateName(JSGeneratorPlugin.class, "template/propertiesJS.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.js.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.js";
	}

}
