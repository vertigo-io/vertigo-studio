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
package io.vertigo.studio.plugins.mda.vertigo.domain.java;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.FileGenerator;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioStereotype;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationDefinition;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationNNDefinition;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationSimpleDefinition;
import io.vertigo.studio.metamodel.domain.masterdata.MasterDataValue;
import io.vertigo.studio.metamodel.domain.masterdata.StaticMasterData;
import io.vertigo.studio.plugins.mda.vertigo.domain.java.model.MethodAnnotationsModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.java.model.StudioDtDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.java.model.masterdata.MasterDataDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Domain.
 *
 * @author pchretien, mlaroche
 */
public final class DomainGeneratorPlugin implements GeneratorPlugin {

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

		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.java.targetSubDir", DEFAULT_TARGET_SUBDIR);
		/* Génération des ressources afférentes au DT. */
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.java.generateDtResources", Boolean.TRUE)) {// true by default
			generateDtResources(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder);
		}

		/* Génération de la lgeneratee référençant toutes des définitions. */
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.java.generateDtDefinitions", Boolean.TRUE)) {// true by default
			generateDtDefinitions(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder, mdaConfig.getOrDefaultAsString("vertigo.domain.java.dictionaryClassName", "DtDefinitions"));
		}

		/* Générations des DTO. */
		generateDtObjects(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder);
		generateJavaEnums(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder);

	}

	private static void generateDtDefinitions(
			final DefinitionSpace definitionSpace,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final String dictionaryClassName) {

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("packageName", mdaConfig.getProjectPackageName() + ".domain")
				.put("classSimpleName", dictionaryClassName)
				.put("dtDefinitions", toModels(definitionSpace, DomainUtil.getDtDefinitions(definitionSpace)))
				.build();

		FileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(dictionaryClassName + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(mdaConfig.getProjectPackageName() + ".domain")
				.withTemplateName(DomainGeneratorPlugin.class, "template/dtdefinitions.ftl")
				.build()
				.generateFile(mdaResultBuilder);

	}

	private static void generateDtObjects(
			final DefinitionSpace definitionSpace,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		for (final StudioDtDefinition dtDefinition : DomainUtil.getDtDefinitions(definitionSpace)) {
			generateDtObject(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder, dtDefinition, getAssociationsByDtDefinition(definitionSpace, dtDefinition));
		}
	}

	private static void generateDtObject(
			final DefinitionSpace definitionSpace,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final StudioDtDefinition dtDefinition,
			final List<? extends StudioAssociationDefinition> associations) {
		final StudioDtDefinitionModel dtDefinitionModel = new StudioDtDefinitionModel(dtDefinition, associations, DomainUtil.createClassNameFromDtFunction(definitionSpace));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("dtDefinition", dtDefinitionModel)
				.put("annotations", new MethodAnnotationsModel())
				.build();

		FileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(dtDefinitionModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(dtDefinitionModel.getPackageName())
				.withTemplateName(DomainGeneratorPlugin.class, "template/dto.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	private static List<StudioDtDefinitionModel> toModels(final DefinitionSpace definitionSpace, final Collection<StudioDtDefinition> dtDefinitions) {
		return dtDefinitions.stream()
				.map(dtDef -> new StudioDtDefinitionModel(dtDef, getAssociationsByDtDefinition(definitionSpace, dtDef), DomainUtil.createClassNameFromDtFunction(definitionSpace)))
				.collect(Collectors.toList());
	}

	private static List<StudioAssociationDefinition> getAssociationsByDtDefinition(final DefinitionSpace definitionSpace, final StudioDtDefinition studioDtDefinition) {
		return Stream.concat(definitionSpace.getAll(StudioAssociationSimpleDefinition.class).stream(), definitionSpace.getAll(StudioAssociationNNDefinition.class).stream())
				.filter(association -> association.getAssociationNodeA().getDtDefinition().getName().equals(studioDtDefinition.getName())
						|| association.getAssociationNodeB().getDtDefinition().getName().equals(studioDtDefinition.getName())) // concerns current dt
				.collect(Collectors.toList());
	}

	private static void generateDtResources(
			final DefinitionSpace definitionSpace,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		final String simpleClassName = "DtResources";
		final String resourcesTemplateName = "template/resources.ftl";
		final String propertiesTemplateName = "template/properties.ftl";

		/**
		 * Génération des ressources afférentes au DT.
		 */
		for (final Entry<String, Collection<StudioDtDefinition>> entry : DomainUtil.getDtDefinitionCollectionMap(definitionSpace).entrySet()) {
			final Collection<StudioDtDefinition> dtDefinitions = entry.getValue();
			Assertion.check().notNull(dtDefinitions);
			final String packageName = entry.getKey();

			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("packageName", packageName)
					.put("simpleClassName", simpleClassName)
					.put("dtDefinitions", toModels(definitionSpace, dtDefinitions))
					.build();

			FileGenerator.builder(mdaConfig)
					.withModel(model)
					.withFileName(simpleClassName + ".java")
					.withGenSubDir(targetSubDir)
					.withPackageName(packageName)
					.withTemplateName(DomainGeneratorPlugin.class, resourcesTemplateName)
					.build()
					.generateFile(mdaResultBuilder);

			FileGenerator.builder(mdaConfig)
					.withEncoding("ISO-8859-1")//pour les .properties on force l'ISO-8859-1 comme la norme l'impose
					.withModel(model)
					.withFileName(simpleClassName + ".properties")
					.withGenSubDir(targetSubDir)
					.withPackageName(packageName)
					.withTemplateName(DomainGeneratorPlugin.class, propertiesTemplateName)
					.build()
					.generateFile(mdaResultBuilder);
		}
	}

	private void generateJavaEnums(
			final DefinitionSpace metamodelRepository,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		final Map<String, Map<String, MasterDataValue>> staticMasterDataValues = metamodelRepository.getAll(StaticMasterData.class).stream().collect(Collectors.toMap(StaticMasterData::getEntityClassName, StaticMasterData::getValues));

		metamodelRepository.getAll(StudioDtDefinition.class)
				.stream()
				.filter(dtDefinition -> dtDefinition.getStereotype() == StudioStereotype.StaticMasterData)
				.forEach(dtDefintion -> generateJavaEnum(
						targetSubDir,
						mdaConfig,
						mdaResultBuilder,
						dtDefintion, staticMasterDataValues.getOrDefault(dtDefintion.getClassCanonicalName(), Collections.emptyMap())));
	}

	private static void generateJavaEnum(
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final StudioDtDefinition dtDefinition,
			final Map<String, MasterDataValue> values) {

		final MasterDataDefinitionModel masterDataDefinitionModel = new MasterDataDefinitionModel(dtDefinition, values);

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("entity", masterDataDefinitionModel)
				.build();

		FileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(masterDataDefinitionModel.getClassSimpleName() + "Enum.java")
				.withGenSubDir(targetSubDir)
				.withPackageName(dtDefinition.getPackageName())
				.withTemplateName(DomainGeneratorPlugin.class, "template/masterdata_enum.ftl")
				.build()
				.generateFile(mdaResultBuilder);

	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.java.targetSubDir", DEFAULT_TARGET_SUBDIR);
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.java";
	}
}
