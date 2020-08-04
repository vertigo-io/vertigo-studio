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
package io.vertigo.studio.plugins.mda.vertigo.task.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.plugins.mda.vertigo.task.model.TaskAttributeModel;
import io.vertigo.studio.plugins.mda.vertigo.task.model.TaskDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Task.
 *
 * @author sezratty
 */
public final class TaskTestGeneratorPlugin implements MdaGeneratorPlugin {

	/** {@inheritDoc} */
	@Override
	public void generate(final Notebook notebook, final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(mdaConfig)
				.isNotNull(mdaResultBuilder);
		//-----
		final String targetSubDir = mdaConfig.getAsString("vertigo.taskTest.targetSubDir");
		Assertion.check().isNotNull(targetSubDir);
		//---
		generatePaos(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
		generateDaos(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private static void generatePaos(
			final Notebook notebook,
			final String paosTargetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		//On liste des taches regroupées par Package.
		for (final Entry<String, List<TaskSketch>> entry : buildPackageMap(notebook).entrySet()) {
			final Collection<TaskSketch> taskSketchs = entry.getValue();
			if (!taskSketchs.isEmpty()) {

				final String packageName = entry.getKey();
				final String classSimpleName = getLastPackageName(packageName) + "PAO";

				generateAo(notebook, paosTargetSubDir, mdaConfig, mdaResultBuilder, taskSketchs, packageName,
						classSimpleName);
			}
		}

	}

	/**
	 * Génération de tous les DAOs.
	 */
	private static void generateDaos(
			final Notebook notebook,
			final String daosTargetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		for (final Entry<DtSketch, List<TaskSketch>> entry : builDtSketchMap(notebook).entrySet()) {
			final DtSketch dtDefinition = entry.getKey();
			if (dtDefinition.isPersistent()) {
				final String definitionPackageName = dtDefinition.getPackageName();
				final String packageNamePrefix = mdaConfig.getProjectPackageName() + ".domain";
				final String packageName = mdaConfig.getProjectPackageName() + ".dao" + definitionPackageName.substring(packageNamePrefix.length());

				final String classSimpleName = dtDefinition.getClassSimpleName() + "DAO";

				generateAo(notebook, daosTargetSubDir, mdaConfig, mdaResultBuilder, entry.getValue(), packageName,
						classSimpleName);
			}
		}
	}

	private static void generateAo(
			final Notebook notebook,
			final String aoTargetSubDir, final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder, final Collection<TaskSketch> taskDefinitionCollection,
			final String packageName, final String classSimpleName) {
		for (final TaskSketch taskDefinition : taskDefinitionCollection) {
			final TemplateAoTaskTest paoModel = new TemplateAoTaskTest(mdaConfig, taskDefinition, packageName, classSimpleName, mdaConfig.getAsString("vertigo.taskTest.baseTestClass"), DomainUtil.createClassNameFromDtFunction(notebook));
			generatePaoTaskTest(aoTargetSubDir, mdaConfig, mdaResultBuilder, paoModel);
		}
	}

	/**
	 * Retourne le nom du package feuille à partir d'un nom complet de package.
	 * exemple : org.company.sugar > sugar
	 * @param packageName Nom de package
	 * @return Nom du package feuille à partir d'un nom complet de package
	 */
	private static String getLastPackageName(final String packageName) {
		String lastPackageName = packageName;
		if (lastPackageName.indexOf('.') != -1) {
			lastPackageName = lastPackageName.substring(lastPackageName.lastIndexOf('.') + 1);
		}
		return StringUtil.first2UpperCase(lastPackageName);
	}

	private static void generatePaoTaskTest(final String targetSubDir, final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder, final TemplateAoTaskTest paoModel) {
		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("pao", paoModel)
				.build();

		final String testPackageName = paoModel.getTaskDefinition().getTestPackageName();
		final String fileName = paoModel.getTaskDefinition().getTestClassName() + ".java";

		/* Calcule le chemin du fichier à générer. */
		final String directoryPath = mdaConfig.getTargetGenDir() + targetSubDir + File.separatorChar + package2directory(testPackageName) + File.separatorChar;
		final String fullFilePath = directoryPath + fileName;

		/* Vérifie que le fichier n'existe pas déjà. */
		if (new File(fullFilePath).exists()) {
			/* Le fichier existe : on ne l'écrase pas. */
			return;
		}

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(fileName)
				.withGenSubDir(targetSubDir)
				.withPackageName(testPackageName)
				.withTemplateName(TaskTestGeneratorPlugin.class, "ao_task_test.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	private static String package2directory(final String packageName) {
		return packageName.replace('.', File.separatorChar).replace('\\', File.separatorChar);
	}

	/**
	 * Stratégie pour savoir si une tache est PAO ou DAO.
	 * Si la DT est non null DAO sinon PAO.
	 */
	private static String getDtDefinition(final TaskDefinitionModel templateTaskDefinition) {
		if (templateTaskDefinition.isOut()) {
			//si out on regarde si en sortie on a un DTO ou une DTC typé.
			final DomainSketch outDomain = templateTaskDefinition.getOutAttribute().getDomain();
			if (outDomain.getScope().isDataObject()) {
				return outDomain.getDtSketchName();
			}
			return null;
		}
		//there is no OUT param
		//We are searching igf there is an no-ambiguous IN param defined as a DataObject(DTO or DTC)
		final List<DomainSketch> candidates = templateTaskDefinition.getInAttributes()
				.stream()
				.map(TaskAttributeModel::getDomain)
				.filter(domain -> domain.getScope().isDataObject())
				.collect(Collectors.toList());
		//There MUST be only ONE candidate
		if (candidates.size() == 1) {
			return candidates.get(0).getDtSketchName();
		}
		//Ambiguosity => PAO
		return null;
	}

	private static Map<String, List<TaskSketch>> buildPackageMap(final Notebook notebook) {
		final Collection<TaskSketch> taskDefinitions = notebook.getAll(TaskSketch.class);
		final Map<String, List<TaskSketch>> taskDefinitionsMap = new LinkedHashMap<>();
		//---
		for (final TaskSketch taskDefinition : taskDefinitions) {
			final TaskDefinitionModel templateTaskDefinition = new TaskDefinitionModel(taskDefinition, DomainUtil.createClassNameFromDtFunction(notebook));
			final String dtDefinition = getDtDefinition(templateTaskDefinition);
			// Correction bug : task avec retour DtObject (non persistant) non générée
			//Les taches sont générées dans les pao
			// - si il n'esxiste pas de définition associées à la tache
			// - ou si la définition est considérée comme non persistante.
			final boolean pao = dtDefinition == null || !notebook.resolve(SketchKey.of(dtDefinition), DtSketch.class).isPersistent();
			if (pao) {
				//La tache est liée au package. (PAO)
				final List<TaskSketch> list = taskDefinitionsMap
						.computeIfAbsent(taskDefinition.getPackageName(), k -> new ArrayList<>());
				//on ajoute la tache aux taches du package.
				list.add(taskDefinition);
			}
		}
		return taskDefinitionsMap;

	}

	private static Map<DtSketch, List<TaskSketch>> builDtSketchMap(final Notebook notebook) {
		final Collection<TaskSketch> taskSketchs = notebook.getAll(TaskSketch.class);
		final Map<DtSketch, List<TaskSketch>> taskSketchsMap = new LinkedHashMap<>();

		//---
		//Par défaut, On crée pour chaque DT une liste vide des taches lui étant associées.
		final Collection<DtSketch> dtSketchs = notebook.getAll(DtSketch.class);
		for (final DtSketch dtSketch : dtSketchs) {
			taskSketchsMap.put(dtSketch, new ArrayList<TaskSketch>());
		}
		//---
		for (final TaskSketch taskSketch : taskSketchs) {
			final TaskDefinitionModel templateTaskDefinition = new TaskDefinitionModel(taskSketch, DomainUtil.createClassNameFromDtFunction(notebook));

			final String dtSketchName = getDtDefinition(templateTaskDefinition);
			final boolean dao = dtSketchName != null;
			if (dao) {
				//Dans le cas d'un DTO ou DTC en sortie on considère que la tache est liée au DAO.
				taskSketchsMap.get(notebook.resolve(SketchKey.of(dtSketchName), DtSketch.class)).add(taskSketch);
			}
		}
		return taskSketchsMap;

	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getAsString("vertigo.taskTest.targetSubDir");
		Assertion.check().isNotNull(targetSubDir);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.taskTest";
	}
}
