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
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.param.ParamValue;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.FileGenerator;
import io.vertigo.studio.impl.mda.FileGeneratorConfig;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.authorization.SecuredFeature;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.plugins.mda.vertigo.authorization.model.GlobalAuthorizationModel;
import io.vertigo.studio.plugins.mda.vertigo.authorization.model.SecuredEntityModel;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Generation des objets relatifs au module Securite.
 *
 * @author pchretien
 */
public final class AuthorizationGeneratorPlugin implements GeneratorPlugin {

	private final String targetSubDir;

	/**
	 * Constructeur.
	 * @param targetSubDirOpt Repertoire de generation des fichiers de ce plugin
	 */
	@Inject
	public AuthorizationGeneratorPlugin(@ParamValue("targetSubDir") final Optional<String> targetSubDirOpt) {
		//-----
		targetSubDir = targetSubDirOpt.orElse("javagen");
	}

	/** {@inheritDoc} */
	@Override
	public void generate(
			final DefinitionSpace definitionSpace,
			final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder) {
		Assertion.checkNotNull(fileGeneratorConfig);
		Assertion.checkNotNull(mdaResultBuilder);
		//-----
		generateGlobalAuthorizations(definitionSpace, targetSubDir, fileGeneratorConfig, mdaResultBuilder);

		generateOperations(definitionSpace, targetSubDir, fileGeneratorConfig, mdaResultBuilder);
	}

	private static List<GlobalAuthorizationModel> getGlobalAuthorizations(final DefinitionSpace definitionSpace) {
		return definitionSpace.getAll(SecuredFeature.class).stream()
				.filter(o -> o.getLinkedResourceOpt().isEmpty())
				.map(GlobalAuthorizationModel::new)
				.collect(Collectors.toList());
	}

	private static Collection<SecuredEntityModel> getSecuredEntities(final DefinitionSpace definitionSpace) {
		return definitionSpace.getAll(SecuredFeature.class)
				.stream()
				.filter(securedFeature -> securedFeature.getLinkedResourceOpt().isPresent())
				.collect(Collectors.groupingBy(securedFeature -> securedFeature.getLinkedResourceOpt().get(), Collectors.toList()))
				.entrySet().stream()
				.filter(entry -> definitionSpace.contains("StDt" + entry.getKey()))// we have the studioDtDefinition
				.map(entry -> new SecuredEntityModel(entry.getValue(), definitionSpace.resolve("StDt" + entry.getKey(), StudioDtDefinition.class)))
				.collect(Collectors.toList());
	}

	private static void generateGlobalAuthorizations(final DefinitionSpace definitionSpace, final String targetSubDir, final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		generateDictionnary("GlobalAuthorizations", targetSubDir, fileGeneratorConfig, mdaResultBuilder, getGlobalAuthorizations(definitionSpace));
	}

	private static void generateOperations(final DefinitionSpace definitionSpace, final String targetSubDir, final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		generateDictionnary("SecuredEntities", targetSubDir, fileGeneratorConfig, mdaResultBuilder, getSecuredEntities(definitionSpace));
	}

	private static void generateDictionnary(
			final String objectName,
			final String targetSubDir,
			final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder,
			final Collection<?> values) {
		Assertion.checkArgNotEmpty(objectName);
		Assertion.checkArgument(Character.isUpperCase(objectName.charAt(0)) && !objectName.contains("_"), "Object name ({0}) should be in camelcase and starts with UpperCase", objectName);
		Assertion.checkArgument(objectName.charAt(objectName.length() - 1) == 's', "Object name ({0}) should ends with 's'", objectName);
		//----
		if (!values.isEmpty()) {
			final String lowerCaseObjectName = objectName.toLowerCase(Locale.ROOT);
			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put(lowerCaseObjectName, values)
					.put("classSimpleName", objectName)
					.put("packageName", fileGeneratorConfig.getProjectPackageName() + ".authorization")
					.build();

			FileGenerator.builder(fileGeneratorConfig)
					.withModel(model)
					.withFileName(objectName + ".java")
					.withGenSubDir(targetSubDir)
					.withPackageName(fileGeneratorConfig.getProjectPackageName() + ".authorization")
					.withTemplateName(AuthorizationGeneratorPlugin.class, "template/" + lowerCaseObjectName + ".ftl")
					.build()
					.generateFile(mdaResultBuilder);

		}
	}

	@Override
	public void clean(final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		MdaUtil.deleteFiles(new File(fileGeneratorConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}
}
