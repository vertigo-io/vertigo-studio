/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.js;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.plugins.generator.vertigo.domain.js.model.JSDtModel;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

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
			final Notebook notebook,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(generatorConfig)
				.isNotNull(generatorResultBuilder);
		//-----
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.js.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		/* Génération des ressources afférentes au DT mais pour la partie JS.*/
		if (generatorConfig.getOrDefaultAsBoolean("vertigo.domain.js.generateDtResourcesJS", true)) {//true by default
			generateDtResourcesJS(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		}
		/* Génération des fichiers javascripts référençant toutes les définitions. */
		if (generatorConfig.getOrDefaultAsBoolean("vertigo.domain.js.generateJsDtDefinitions", true)) {//true by default
			generateJsDtDefinitions(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		}
	}

	private static List<JSDtModel> getJsDtDefinitionModels(final Notebook notebook) {
		return DomainUtil.getDtSketchs(notebook)
				.stream()
				.map(JSDtModel::new)
				.collect(Collectors.toList());
	}

	private static void generateJsDtDefinitions(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("packageName", generatorConfig.getProjectPackageName() + ".domain")
				.put("classSimpleName", "DtDefinitions")
				.put("dtDefinitions", getJsDtDefinitionModels(notebook))
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName("DtDefinitions.js")
				.withGenSubDir(targetSubDir)
				.withPackageName(generatorConfig.getProjectPackageName() + ".domain")
				.withTemplateName(JSGeneratorPlugin.class, "template/js.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	/**
	 * Génère les ressources JS pour les traductions.
	 * @param generatorConfig Configuration du domaine.
	 */
	private static void generateDtResourcesJS(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		final String simpleClassName = "DtDefinitions" + "Label";
		final String packageName = generatorConfig.getProjectPackageName() + ".domain";

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("packageName", packageName)
				.put("simpleClassName", simpleClassName)
				.put("dtDefinitions", getJsDtDefinitionModels(notebook))
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(simpleClassName + ".js")
				.withGenSubDir(targetSubDir)
				.withPackageName(packageName)
				.withTemplateName(JSGeneratorPlugin.class, "template/propertiesJS.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.js.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.js";
	}

}
