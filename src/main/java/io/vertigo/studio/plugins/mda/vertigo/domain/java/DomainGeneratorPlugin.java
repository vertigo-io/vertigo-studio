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
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.notebook.domain.masterdata.MasterDataValue;
import io.vertigo.studio.notebook.domain.masterdata.StaticMasterDataSketch;
import io.vertigo.studio.plugins.mda.vertigo.domain.java.model.MethodAnnotationsModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.java.model.StudioDtModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.java.model.masterdata.MasterDataModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Domain.
 *
 * @author pchretien, mlaroche
 */
public final class DomainGeneratorPlugin implements MdaGeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "javagen";

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

		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.java.targetSubDir", DEFAULT_TARGET_SUBDIR);
		/* Génération des ressources afférentes au DT. */
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.java.generateDtResources", Boolean.TRUE)) {// true by default
			generateDtResources(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
		}

		/* Génération de la lgeneratee référençant toutes des définitions. */
		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.java.generateDtDefinitions", Boolean.TRUE)) {// true by default
			generateDtDefinitions(notebook, targetSubDir, mdaConfig, mdaResultBuilder, mdaConfig.getOrDefaultAsString("vertigo.domain.java.dictionaryClassName", "DtDefinitions"));
		}

		/* Générations des DTO. */
		generateDtObjects(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
		generateJavaEnums(notebook, targetSubDir, mdaConfig, mdaResultBuilder);

	}

	private static void generateDtDefinitions(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final String dictionaryClassName) {

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("packageName", mdaConfig.getProjectPackageName() + ".domain")
				.put("classSimpleName", dictionaryClassName)
				.put("dtDefinitions", toModels(notebook, DomainUtil.getDtSketchs(notebook)))
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(dictionaryClassName + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(mdaConfig.getProjectPackageName() + ".domain")
				.withTemplateName(DomainGeneratorPlugin.class, "template/dtdefinitions.ftl")
				.build()
				.generateFile(mdaResultBuilder);

	}

	private static void generateDtObjects(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		for (final DtSketch dtSketch : DomainUtil.getDtSketchs(notebook)) {
			generateDtObject(notebook, targetSubDir, mdaConfig, mdaResultBuilder, dtSketch, getAssociationsByDtDefinition(notebook, dtSketch));
		}
	}

	private static void generateDtObject(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final DtSketch dtSketch,
			final List<? extends AssociationSketch> associations) {
		final StudioDtModel dtDefinitionModel = new StudioDtModel(dtSketch, associations, DomainUtil.createClassNameFromDtFunction(notebook));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("dtDefinition", dtDefinitionModel)
				.put("annotations", new MethodAnnotationsModel())
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(dtDefinitionModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(dtDefinitionModel.getPackageName())
				.withTemplateName(DomainGeneratorPlugin.class, "template/dto.ftl")
				.build()
				.generateFile(mdaResultBuilder);
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
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
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

			MdaFileGenerator.builder(mdaConfig)
					.withModel(model)
					.withFileName(simpleClassName + ".java")
					.withGenSubDir(targetSubDir)
					.withPackageName(packageName)
					.withTemplateName(DomainGeneratorPlugin.class, resourcesTemplateName)
					.build()
					.generateFile(mdaResultBuilder);

			MdaFileGenerator.builder(mdaConfig)
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
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		final Map<String, Map<String, MasterDataValue>> staticMasterDataValues = notebook.getAll(StaticMasterDataSketch.class)
				.stream().collect(Collectors.toMap(StaticMasterDataSketch::getEntityClassName, StaticMasterDataSketch::getValues));

		notebook.getAll(DtSketch.class)
				.stream()
				.filter(dtDefinition -> dtDefinition.getStereotype() == StudioStereotype.StaticMasterData)
				.forEach(dtSketch -> generateJavaEnum(
						targetSubDir,
						mdaConfig,
						mdaResultBuilder,
						dtSketch, staticMasterDataValues.getOrDefault(dtSketch.getClassCanonicalName(), Collections.emptyMap())));
	}

	private static void generateJavaEnum(
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final DtSketch dtSketch,
			final Map<String, MasterDataValue> values) {

		final MasterDataModel masterDataDefinitionModel = new MasterDataModel(dtSketch, values);

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("entity", masterDataDefinitionModel)
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(masterDataDefinitionModel.getClassSimpleName() + "Enum.java")
				.withGenSubDir(targetSubDir)
				.withPackageName(dtSketch.getPackageName())
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
