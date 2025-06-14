/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.authorization;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.MapBuilder;
import io.vertigo.datamodel.data.definitions.DataDefinition;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.authorization.SecuredFeatureSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.plugins.generator.vertigo.authorization.model.GlobalAuthorizationModel;
import io.vertigo.studio.plugins.generator.vertigo.authorization.model.SecuredEntityModel;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Generation des objets relatifs au module Securite.
 *
 * @author pchretien, mlaroche
 */
public final class AuthorizationGeneratorPlugin implements GeneratorPlugin {

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
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.authorization.targetSubDir", DEFAULT_TARGET_SUBDIR);

		generateGlobalAuthorizations(notebook, targetSubDir, generatorConfig, generatorResultBuilder);

		generateOperations(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
	}

	private static List<GlobalAuthorizationModel> getGlobalAuthorizations(final Notebook notebook) {
		return notebook.getAll(SecuredFeatureSketch.class)
				.stream()
				.filter(o -> o.getLinkedResourceOpt().isEmpty())
				.map(GlobalAuthorizationModel::new)
				.toList();
	}

	private static Collection<SecuredEntityModel> getSecuredEntities(final Notebook notebook) {
		return notebook.getAll(SecuredFeatureSketch.class)
				.stream()
				.filter(securedFeature -> securedFeature.getLinkedResourceOpt().isPresent())
				.collect(Collectors.groupingBy(securedFeature -> securedFeature.getLinkedResourceOpt().get(), Collectors.toList()))
				.entrySet()
				.stream()
				.filter(entry -> notebook.contains(DataDefinition.PREFIX + entry.getKey()))// we have the studioDtDefinition
				.map(entry -> new SecuredEntityModel(entry.getValue(), notebook.resolve(DataDefinition.PREFIX + entry.getKey(), DtSketch.class)))
				.toList();
	}

	private static void generateGlobalAuthorizations(final Notebook notebook, final String targetSubDir, final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		generateDictionnary("GlobalAuthorizations", targetSubDir, generatorConfig, generatorResultBuilder, getGlobalAuthorizations(notebook));
	}

	private static void generateOperations(final Notebook notebook, final String targetSubDir, final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		generateDictionnary("SecuredEntities", targetSubDir, generatorConfig, generatorResultBuilder, getSecuredEntities(notebook));
	}

	private static void generateDictionnary(
			final String objectName,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final Collection<?> values) {
		Assertion.check()
				.isNotBlank(objectName)
				.isTrue(Character.isUpperCase(objectName.charAt(0)) && !objectName.contains("_"), "Object name ({0}) should be in camelcase and starts with UpperCase", objectName)
				.isTrue(objectName.charAt(objectName.length() - 1) == 's', "Object name ({0}) should ends with 's'", objectName);
		//----
		if (!values.isEmpty()) {
			final String lowerCaseObjectName = objectName.toLowerCase(Locale.ROOT);
			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put(lowerCaseObjectName, values)
					.put("classSimpleName", objectName)
					.put("packageName", generatorConfig.getProjectPackageName() + ".authorization")
					.build();

			FileGenerator.builder(generatorConfig)
					.withModel(model)
					.withFileName(objectName + ".java")
					.withGenSubDir(targetSubDir)
					.withPackageName(generatorConfig.getProjectPackageName() + ".authorization")
					.withTemplateName(AuthorizationGeneratorPlugin.class, "template/" + lowerCaseObjectName + ".ftl")
					.build()
					.generateFile(generatorResultBuilder);

		}
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.authorization.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.authorization";
	}
}
