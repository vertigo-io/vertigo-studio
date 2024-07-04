/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.task.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.MapBuilder;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.plugins.generator.vertigo.task.model.TaskAttributeModel;
import io.vertigo.studio.plugins.generator.vertigo.task.model.TaskModel;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Task.
 *
 * @author sezratty
 */
public final class TaskTestGeneratorPlugin implements GeneratorPlugin {

	/** {@inheritDoc} */
	@Override
	public void generate(final Notebook notebook, final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(generatorConfig)
				.isNotNull(generatorResultBuilder);
		//-----
		final String targetSubDir = generatorConfig.getAsString("vertigo.taskTest.targetSubDir");
		Assertion.check().isNotNull(targetSubDir);
		//---
		generatePaos(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		generateDaos(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private static void generatePaos(
			final Notebook notebook,
			final String paosTargetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {

		//On liste des taches regroupées par Package.
		for (final Entry<String, List<TaskSketch>> entry : buildPackageMap(notebook, generatorConfig).entrySet()) {
			final Collection<TaskSketch> taskSketchs = entry.getValue();
			if (!taskSketchs.isEmpty()) {

				final String packageName = entry.getKey();
				final String classSimpleName = getLastPackageName(packageName) + "PAO";

				generateAo(notebook, paosTargetSubDir, generatorConfig, generatorResultBuilder, taskSketchs, packageName,
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
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {

		for (final Entry<DtSketch, List<TaskSketch>> entry : builDtSketchMap(notebook, generatorConfig).entrySet()) {
			final DtSketch dtSketch = entry.getKey();
			final String packageNamePrefix = generatorConfig.getProjectPackageName();
			final String dtPackageName = dtSketch.getPackageName();
			if (dtPackageName.startsWith(packageNamePrefix) && dtSketch.isPersistent()) {
				// ---
				Assertion.check()
						.isTrue(dtPackageName.substring(packageNamePrefix.length()).contains(".domain"), "Package name {0}, must contains the modifier .domain", dtPackageName);
				// ---
				//we need to find the featureName, aka between projectpackageName and .domain
				final String featureName = dtPackageName.substring(packageNamePrefix.length(), dtPackageName.indexOf(".domain"));
				if (!StringUtil.isBlank(featureName)) {
					Assertion.check().isTrue(featureName.lastIndexOf('.') == 0, "The feature {0} must not contain any dot", featureName.substring(1));
				}
				// the subpackage is what's behind the .domain
				final String subpackage = dtPackageName.substring(dtPackageName.indexOf(".domain") + ".domain".length());
				// breaking change -> need to redefine what's the desired folder structure in javagen...

				//On construit le nom du package à partir du package de la DT et de la feature.
				final String packageName = generatorConfig.getProjectPackageName() + featureName + ".dao" + subpackage;
				final String classSimpleName = dtSketch.getClassSimpleName() + "DAO";

				generateAo(notebook, daosTargetSubDir, generatorConfig, generatorResultBuilder, entry.getValue(), packageName,
						classSimpleName);
			}
		}
	}

	private static void generateAo(
			final Notebook notebook,
			final String aoTargetSubDir, final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder, final Collection<TaskSketch> tasSketches,
			final String packageName, final String classSimpleName) {
		for (final TaskSketch taskSketch : tasSketches) {
			final TemplateAoTaskTest paoModel = new TemplateAoTaskTest(generatorConfig, taskSketch, packageName, classSimpleName, generatorConfig.getAsString("vertigo.taskTest.baseTestClass"), DomainUtil.createClassNameFromDtFunction(notebook));
			generatePaoTaskTest(aoTargetSubDir, generatorConfig, generatorResultBuilder, paoModel);
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

	private static void generatePaoTaskTest(final String targetSubDir, final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder, final TemplateAoTaskTest paoModel) {
		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("pao", paoModel)
				.build();

		final String testPackageName = paoModel.getTaskDefinition().getTestPackageName();
		final String fileName = paoModel.getTaskDefinition().getTestClassName() + ".java";

		/* Calcule le chemin du fichier à générer. */
		final String directoryPath = generatorConfig.getTargetGenDir() + targetSubDir + File.separatorChar + package2directory(testPackageName) + File.separatorChar;
		final String fullFilePath = directoryPath + fileName;

		/* Vérifie que le fichier n'existe pas déjà. */
		if (new File(fullFilePath).exists()) {
			/* Le fichier existe : on ne l'écrase pas. */
			return;
		}

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(fileName)
				.withGenSubDir(targetSubDir)
				.withPackageName(testPackageName)
				.withTemplateName(TaskTestGeneratorPlugin.class, "ao_task_test.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	private static String package2directory(final String packageName) {
		return packageName.replace('.', File.separatorChar).replace('\\', File.separatorChar);
	}

	/**
	 * Stratégie pour savoir si une tache est PAO ou DAO.
	 * Si la DT est non null DAO sinon PAO.
	 */
	private static SketchKey getDtSketchKey(final TaskModel taskModel) {
		if (taskModel.isOut()) {
			//si out on regarde si en sortie on a un DTO ou une DTC typé.
			final DomainSketch outDomain = taskModel.getOutAttribute().getDomain();
			if (outDomain.getScope().isDataObject()) {
				return outDomain.getDtSketchKey();
			}
			return null;
		}
		//there is no OUT param
		//We are searching igf there is an no-ambiguous IN param defined as a DataObject(DTO or DTC)
		final List<DomainSketch> candidates = taskModel.getInAttributes()
				.stream()
				.map(TaskAttributeModel::getDomain)
				.filter(domain -> domain.getScope().isDataObject())
				.toList();
		//There MUST be only ONE candidate
		if (candidates.size() == 1) {
			return candidates.get(0).getDtSketchKey();
		}
		//Ambiguosity => PAO
		return null;
	}

	private static Map<String, List<TaskSketch>> buildPackageMap(final Notebook notebook, final GeneratorConfig generatorConfig) {
		final Collection<TaskSketch> taskSketches = notebook.getAll(TaskSketch.class);
		final Map<String, List<TaskSketch>> taskSketchesMap = new LinkedHashMap<>();
		//---
		for (final TaskSketch taskSketch : taskSketches) {
			if (taskSketch.getPackageName().startsWith(generatorConfig.getProjectPackageName())) {
				final TaskModel templateTaskDefinition = new TaskModel(taskSketch, DomainUtil.createClassNameFromDtFunction(notebook));
				final SketchKey dtSketchKey = getDtSketchKey(templateTaskDefinition);
				// Correction bug : task avec retour DtObject (non persistant) non générée
				//Les taches sont générées dans les pao
				// - si il n'esxiste pas de définition associées à la tache
				// - ou si la définition est considérée comme non persistante.
				final boolean pao = dtSketchKey == null || !notebook.resolve(dtSketchKey.getName(), DtSketch.class).isPersistent();
				if (pao) {
					//La tache est liée au package. (PAO)
					final List<TaskSketch> list = taskSketchesMap
							.computeIfAbsent(taskSketch.getPackageName(), k -> new ArrayList<>());
					//on ajoute la tache aux taches du package.
					list.add(taskSketch);
				}
			}
		}
		return taskSketchesMap;

	}

	private static Map<DtSketch, List<TaskSketch>> builDtSketchMap(final Notebook notebook, final GeneratorConfig generatorConfig) {
		final Collection<TaskSketch> taskSketchs = notebook.getAll(TaskSketch.class);
		final Map<DtSketch, List<TaskSketch>> taskSketchsMap = new LinkedHashMap<>();

		//---
		//Par défaut, On crée pour chaque DT une liste vide des taches lui étant associées.
		final Collection<DtSketch> dtSketchs = notebook.getAll(DtSketch.class);
		for (final DtSketch dtSketch : dtSketchs) {
			if (dtSketch.getPackageName().startsWith(generatorConfig.getProjectPackageName())) {
				taskSketchsMap.put(dtSketch, new ArrayList<TaskSketch>());
			}
		}
		//---
		for (final TaskSketch taskSketch : taskSketchs) {
			if (taskSketch.getPackageName().startsWith(generatorConfig.getProjectPackageName())) {
				final TaskModel templateTaskDefinition = new TaskModel(taskSketch, DomainUtil.createClassNameFromDtFunction(notebook));

				final SketchKey dtSketchKey = getDtSketchKey(templateTaskDefinition);
				final boolean dao = dtSketchKey != null;
				if (dao) {
					//Dans le cas d'un DTO ou DTC en sortie on considère que la tache est liée au DAO.
					taskSketchsMap.computeIfPresent(notebook.resolve(dtSketchKey.getName(), DtSketch.class), (sketch, tasks) -> {
						tasks.add(taskSketch);
						return tasks;
					});
				}
			}
		}
		return taskSketchsMap;

	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getAsString("vertigo.taskTest.targetSubDir");
		Assertion.check().isNotNull(targetSubDir);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.taskTest";
	}
}
