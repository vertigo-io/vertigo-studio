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
package io.vertigo.studio.plugins.mda.vertigo.authorization;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.FileGenerator;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.authorization.SecuredFeature;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.plugins.mda.vertigo.authorization.model.GlobalAuthorizationModel;
import io.vertigo.studio.plugins.mda.vertigo.authorization.model.SecuredEntityModel;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

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
			final MetamodelRepository metamodelRepository,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		Assertion.check()
				.notNull(mdaConfig)
				.notNull(mdaResultBuilder);
		//-----
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.authorization.targetSubDir", DEFAULT_TARGET_SUBDIR);

		generateGlobalAuthorizations(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder);

		generateOperations(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	private static List<GlobalAuthorizationModel> getGlobalAuthorizations(final MetamodelRepository metamodelRepository) {
		return metamodelRepository.getAll(SecuredFeature.class).stream()
				.filter(o -> o.getLinkedResourceOpt().isEmpty())
				.map(GlobalAuthorizationModel::new)
				.collect(Collectors.toList());
	}

	private static Collection<SecuredEntityModel> getSecuredEntities(final MetamodelRepository metamodelRepository) {
		return metamodelRepository.getAll(SecuredFeature.class)
				.stream()
				.filter(securedFeature -> securedFeature.getLinkedResourceOpt().isPresent())
				.collect(Collectors.groupingBy(securedFeature -> securedFeature.getLinkedResourceOpt().get(), Collectors.toList()))
				.entrySet().stream()
				.filter(entry -> metamodelRepository.contains("StDt" + entry.getKey()))// we have the studioDtDefinition
				.map(entry -> new SecuredEntityModel(entry.getValue(), metamodelRepository.resolve("StDt" + entry.getKey(), StudioDtDefinition.class)))
				.collect(Collectors.toList());
	}

	private static void generateGlobalAuthorizations(final MetamodelRepository metamodelRepository, final String targetSubDir, final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		generateDictionnary("GlobalAuthorizations", targetSubDir, mdaConfig, mdaResultBuilder, getGlobalAuthorizations(metamodelRepository));
	}

	private static void generateOperations(final MetamodelRepository metamodelRepository, final String targetSubDir, final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		generateDictionnary("SecuredEntities", targetSubDir, mdaConfig, mdaResultBuilder, getSecuredEntities(metamodelRepository));
	}

	private static void generateDictionnary(
			final String objectName,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final Collection<?> values) {
		Assertion.check()
				.argNotEmpty(objectName)
				.argument(Character.isUpperCase(objectName.charAt(0)) && !objectName.contains("_"), "Object name ({0}) should be in camelcase and starts with UpperCase", objectName)
				.argument(objectName.charAt(objectName.length() - 1) == 's', "Object name ({0}) should ends with 's'", objectName);
		//----
		if (!values.isEmpty()) {
			final String lowerCaseObjectName = objectName.toLowerCase(Locale.ROOT);
			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put(lowerCaseObjectName, values)
					.put("classSimpleName", objectName)
					.put("packageName", mdaConfig.getProjectPackageName() + ".authorization")
					.build();

			FileGenerator.builder(mdaConfig)
					.withModel(model)
					.withFileName(objectName + ".java")
					.withGenSubDir(targetSubDir)
					.withPackageName(mdaConfig.getProjectPackageName() + ".authorization")
					.withTemplateName(AuthorizationGeneratorPlugin.class, "template/" + lowerCaseObjectName + ".ftl")
					.build()
					.generateFile(mdaResultBuilder);

		}
	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.authorization.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.authorization";
	}
}
