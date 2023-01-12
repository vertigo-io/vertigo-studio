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
package io.vertigo.studio.plugins.generator.vertigo.domain.java;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.MapBuilder;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.notebook.domain.masterdata.MasterDataValue;
import io.vertigo.studio.notebook.domain.masterdata.StaticMasterDataSketch;
import io.vertigo.studio.plugins.generator.vertigo.domain.java.model.MethodAnnotationsModel;
import io.vertigo.studio.plugins.generator.vertigo.domain.java.model.StudioDtModel;
import io.vertigo.studio.plugins.generator.vertigo.domain.java.model.masterdata.MasterDataModel;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

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
			final Notebook notebook,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(generatorConfig)
				.isNotNull(generatorResultBuilder);
		//-----

		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.java.targetSubDir", DEFAULT_TARGET_SUBDIR);
		/* Génération des ressources afférentes au DT. */
		if (generatorConfig.getOrDefaultAsBoolean("vertigo.domain.java.generateDtResources", Boolean.TRUE)) {// true by default
			generateDtResources(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		}

		/* Génération de la lgeneratee référençant toutes des définitions. */
		if (generatorConfig.getOrDefaultAsBoolean("vertigo.domain.java.generateDtDefinitions", Boolean.TRUE)) {// true by default
			generateDtDefinitions(notebook, targetSubDir, generatorConfig, generatorResultBuilder, generatorConfig.getOrDefaultAsString("vertigo.domain.java.dictionaryClassName", "DtDefinitions"));
		}

		/* Générations des DTO. */
		generateDtObjects(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		generateJavaEnums(notebook, targetSubDir, generatorConfig, generatorResultBuilder);

	}

	private static void generateDtDefinitions(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final String dictionaryClassName) {

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("packageName", generatorConfig.getProjectPackageName() + ".domain")
				.put("classSimpleName", dictionaryClassName)
				.put("dtDefinitions", toModels(notebook, DomainUtil.getDtSketchs(notebook)))
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(dictionaryClassName + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(generatorConfig.getProjectPackageName() + ".domain")
				.withTemplateName(DomainGeneratorPlugin.class, "template/dtdefinitions.ftl")
				.build()
				.generateFile(generatorResultBuilder);

	}

	private static void generateDtObjects(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		for (final DtSketch dtSketch : DomainUtil.getDtSketchs(notebook)) {
			generateDtObject(notebook, targetSubDir, generatorConfig, generatorResultBuilder, dtSketch, getAssociationsByDtDefinition(notebook, dtSketch));
		}
	}

	private static void generateDtObject(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final DtSketch dtSketch,
			final List<? extends AssociationSketch> associations) {
		final StudioDtModel dtDefinitionModel = new StudioDtModel(dtSketch, associations, DomainUtil.createClassNameFromDtFunction(notebook));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("dtDefinition", dtDefinitionModel)
				.put("annotations", new MethodAnnotationsModel())
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(dtDefinitionModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(dtDefinitionModel.getPackageName())
				.withTemplateName(DomainGeneratorPlugin.class, "template/dto.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	private static List<StudioDtModel> toModels(final Notebook notebook, final Collection<DtSketch> dtDefinitions) {
		return dtDefinitions
				.stream()
				.map(dtDef -> new StudioDtModel(dtDef, getAssociationsByDtDefinition(notebook, dtDef), DomainUtil.createClassNameFromDtFunction(notebook)))
				.collect(Collectors.toList());
	}

	private static List<AssociationSketch> getAssociationsByDtDefinition(final Notebook notebook, final DtSketch dtSketch) {
		return Stream
				.concat(notebook.getAll(AssociationSimpleSketch.class).stream(), notebook.getAll(AssociationNNSketch.class).stream())
				.filter(association -> association.getAssociationNodeA().getDtSketch().getKey().equals(dtSketch.getKey())
						|| association.getAssociationNodeB().getDtSketch().getKey().equals(dtSketch.getKey())) // concerns current dt
				.collect(Collectors.toList());
	}

	private static void generateDtResources(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		final String simpleClassName = "DtResources";
		final String resourcesTemplateName = "template/resources.ftl";
		final String propertiesTemplateName = "template/properties.ftl";

		/**
		 * Génération des ressources afférentes au DT.
		 */
		for (final Entry<String, Collection<DtSketch>> entry : DomainUtil.getDtSketchCollectionMap(notebook).entrySet()) {
			final Collection<DtSketch> dtDefinitions = entry.getValue();
			Assertion.check().isNotNull(dtDefinitions);
			final String packageName = entry.getKey();

			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("packageName", packageName)
					.put("simpleClassName", simpleClassName)
					.put("dtDefinitions", toModels(notebook, dtDefinitions))
					.build();

			FileGenerator.builder(generatorConfig)
					.withModel(model)
					.withFileName(simpleClassName + ".java")
					.withGenSubDir(targetSubDir)
					.withPackageName(packageName)
					.withTemplateName(DomainGeneratorPlugin.class, resourcesTemplateName)
					.build()
					.generateFile(generatorResultBuilder);

			FileGenerator.builder(generatorConfig)
					.withEncoding("UTF-8")//for .properties we force  UTF-8 which is the new standard for Java 9+
					.withModel(model)
					.withFileName(simpleClassName + ".properties")
					.withGenSubDir(targetSubDir)
					.withPackageName(packageName)
					.withTemplateName(DomainGeneratorPlugin.class, propertiesTemplateName)
					.build()
					.generateFile(generatorResultBuilder);
		}
	}

	private void generateJavaEnums(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		final Map<String, Map<String, MasterDataValue>> staticMasterDataValues = notebook.getAll(StaticMasterDataSketch.class)
				.stream().collect(Collectors.toMap(StaticMasterDataSketch::getEntityClassName, StaticMasterDataSketch::getValues));

		notebook.getAll(DtSketch.class)
				.stream()
				.filter(dtDefinition -> dtDefinition.getStereotype() == StudioStereotype.StaticMasterData)
				.forEach(dtSketch -> generateJavaEnum(
						targetSubDir,
						generatorConfig,
						generatorResultBuilder,
						dtSketch, staticMasterDataValues.getOrDefault(dtSketch.getClassCanonicalName(), Collections.emptyMap())));
	}

	private static void generateJavaEnum(
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final DtSketch dtSketch,
			final Map<String, MasterDataValue> values) {

		final MasterDataModel masterDataDefinitionModel = new MasterDataModel(dtSketch, values);

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("entity", masterDataDefinitionModel)
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(masterDataDefinitionModel.getClassSimpleName() + "Enum.java")
				.withGenSubDir(targetSubDir)
				.withPackageName(dtSketch.getPackageName())
				.withTemplateName(DomainGeneratorPlugin.class, "template/masterdata_enum.ftl")
				.build()
				.generateFile(generatorResultBuilder);

	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.java.targetSubDir", DEFAULT_TARGET_SUBDIR);
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.java";
	}
}
