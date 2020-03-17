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
package io.vertigo.studio.plugins.mda.task.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.Home;
import io.vertigo.core.param.ParamValue;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.core.util.StringUtil;
import io.vertigo.dynamo.domain.metamodel.Domain;
import io.vertigo.dynamo.domain.metamodel.StudioDtDefinition;
import io.vertigo.dynamo.task.metamodel.StudioTaskDefinition;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.plugins.mda.FileGenerator;
import io.vertigo.studio.plugins.mda.FileGeneratorConfig;
import io.vertigo.studio.plugins.mda.task.model.TaskAttributeModel;
import io.vertigo.studio.plugins.mda.task.model.TaskDefinitionModel;
import io.vertigo.studio.plugins.mda.util.MdaUtil;

/**
 * Génération des objets relatifs au module Task.
 *
 * @author sezratty
 */
public final class TaskTestGeneratorPlugin implements GeneratorPlugin {

	private final String targetSubDir;
	private final String baseTestClass;

	/**
	 * Constructeur.
	 * @param targetSubDir Repertoire de generation des fichiers de ce plugin
	 * @param baseTestClass Nom canonique de la classe de base pour les tests.
	 */
	@Inject
	public TaskTestGeneratorPlugin(
			@ParamValue("targetSubDir") final String targetSubDir,
			@ParamValue("baseTestClass") final String baseTestClass) {
		//-----
		this.targetSubDir = targetSubDir;
		this.baseTestClass = baseTestClass;
	}

	/** {@inheritDoc} */
	@Override
	public void generate(final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		Assertion.checkNotNull(fileGeneratorConfig);
		Assertion.checkNotNull(mdaResultBuilder);
		//-----

		generatePaos(targetSubDir, fileGeneratorConfig, mdaResultBuilder);
		generateDaos(targetSubDir, fileGeneratorConfig, mdaResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private void generatePaos(
			final String paosTargetSubDir,
			final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder) {

		//On liste des taches regroupées par Package.
		for (final Entry<String, List<StudioTaskDefinition>> entry : buildPackageMap().entrySet()) {
			final Collection<StudioTaskDefinition> taskDefinitionCollection = entry.getValue();
			if (!taskDefinitionCollection.isEmpty()) {

				final String packageName = entry.getKey();
				final String classSimpleName = getLastPackageName(packageName) + "PAO";

				generateAo(paosTargetSubDir, fileGeneratorConfig, mdaResultBuilder, taskDefinitionCollection, packageName,
						classSimpleName);
			}
		}

	}

	/**
	 * Génération de tous les DAOs.
	 */
	private void generateDaos(
			final String daosTargetSubDir,
			final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder) {

		for (final Entry<StudioDtDefinition, List<StudioTaskDefinition>> entry : builDtDefinitiondMap().entrySet()) {
			final StudioDtDefinition dtDefinition = entry.getKey();
			if (dtDefinition.isPersistent()) {
				final String definitionPackageName = dtDefinition.getPackageName();
				final String packageNamePrefix = fileGeneratorConfig.getProjectPackageName() + ".domain";
				final String packageName = fileGeneratorConfig.getProjectPackageName() + ".dao" + definitionPackageName.substring(packageNamePrefix.length());

				final String classSimpleName = dtDefinition.getClassSimpleName() + "DAO";

				generateAo(daosTargetSubDir, fileGeneratorConfig, mdaResultBuilder, entry.getValue(), packageName,
						classSimpleName);
			}
		}
	}

	private void generateAo(final String aoTargetSubDir, final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder, final Collection<StudioTaskDefinition> taskDefinitionCollection,
			final String packageName, final String classSimpleName) {
		final List<TemplateTestClass> testClasses = new ArrayList<>();
		for (final StudioTaskDefinition taskDefinition : taskDefinitionCollection) {
			final TemplateAoTaskTest paoModel = new TemplateAoTaskTest(fileGeneratorConfig, taskDefinition, packageName, classSimpleName, baseTestClass);
			final TemplateTestClass testClass = new TemplateTestClass(paoModel.getTaskDefinition().getTestPackageName(), paoModel.getTaskDefinition().getTestClassName());
			testClasses.add(testClass);

			generatePaoTaskTest(aoTargetSubDir, fileGeneratorConfig, mdaResultBuilder, paoModel);
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

	private static void generatePaoTaskTest(final String targetSubDir, final FileGeneratorConfig fileGeneratorConfig,
			final MdaResultBuilder mdaResultBuilder, final TemplateAoTaskTest paoModel) {
		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("pao", paoModel)
				.build();

		final String testPackageName = paoModel.getTaskDefinition().getTestPackageName();
		final String fileName = paoModel.getTaskDefinition().getTestClassName() + ".java";

		/* Calcule le chemin du fichier à générer. */
		final String directoryPath = fileGeneratorConfig.getTargetGenDir() + targetSubDir + File.separatorChar + package2directory(testPackageName) + File.separatorChar;
		final String fullFilePath = directoryPath + fileName;

		/* Vérifie que le fichier n'existe pas déjà. */
		if (new File(fullFilePath).exists()) {
			/* Le fichier existe : on ne l'écrase pas. */
			return;
		}

		FileGenerator.builder(fileGeneratorConfig)
				.withModel(model)
				.withFileName(fileName)
				.withGenSubDir(targetSubDir)
				.withPackageName(testPackageName)
				.withTemplateName("task/test/ao_task_test.ftl")
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
	private static StudioDtDefinition getDtDefinition(final TaskDefinitionModel templateTaskDefinition) {
		if (templateTaskDefinition.isOut()) {
			//si out on regarde si en sortie on a un DTO ou une DTC typé.
			final Domain outDomain = templateTaskDefinition.getOutAttribute().getDomain();
			if (outDomain.getScope().isDataObject()) {
				return outDomain.getDtDefinition();
			}
			return null;
		}
		//there is no OUT param
		//We are searching igf there is an no-ambiguous IN param defined as a DataObject(DTO or DTC)
		final List<Domain> candidates = templateTaskDefinition.getInAttributes()
				.stream()
				.map(TaskAttributeModel::getDomain)
				.filter(domain -> domain.getScope().isDataObject())
				.collect(Collectors.toList());
		//There MUST be only ONE candidate
		if (candidates.size() == 1) {
			return candidates.get(0).getDtDefinition();
		}
		//Ambiguosity => PAO
		return null;
	}

	private static Map<String, List<StudioTaskDefinition>> buildPackageMap() {
		final Collection<StudioTaskDefinition> taskDefinitions = Home.getApp().getDefinitionSpace().getAll(StudioTaskDefinition.class);
		final Map<String, List<StudioTaskDefinition>> taskDefinitionsMap = new LinkedHashMap<>();
		//---
		for (final StudioTaskDefinition taskDefinition : taskDefinitions) {
			final TaskDefinitionModel templateTaskDefinition = new TaskDefinitionModel(taskDefinition);
			final StudioDtDefinition dtDefinition = getDtDefinition(templateTaskDefinition);
			// Correction bug : task avec retour DtObject (non persistant) non générée
			//Les taches sont générées dans les pao
			// - si il n'esxiste pas de définition associées à la tache
			// - ou si la définition est considérée comme non persistante.
			final boolean pao = dtDefinition == null || !dtDefinition.isPersistent();
			if (pao) {
				//La tache est liée au package. (PAO)
				final List<StudioTaskDefinition> list = taskDefinitionsMap
						.computeIfAbsent(taskDefinition.getPackageName(), k -> new ArrayList<>());
				//on ajoute la tache aux taches du package.
				list.add(taskDefinition);
			}
		}
		return taskDefinitionsMap;

	}

	private static Map<StudioDtDefinition, List<StudioTaskDefinition>> builDtDefinitiondMap() {
		final Collection<StudioTaskDefinition> taskDefinitions = Home.getApp().getDefinitionSpace().getAll(StudioTaskDefinition.class);
		final Map<StudioDtDefinition, List<StudioTaskDefinition>> taskDefinitionsMap = new LinkedHashMap<>();

		//---
		//Par défaut, On crée pour chaque DT une liste vide des taches lui étant associées.
		final Collection<StudioDtDefinition> dtDefinitions = Home.getApp().getDefinitionSpace().getAll(StudioDtDefinition.class);
		for (final StudioDtDefinition dtDefinition : dtDefinitions) {
			taskDefinitionsMap.put(dtDefinition, new ArrayList<StudioTaskDefinition>());
		}
		//---
		for (final StudioTaskDefinition taskDefinition : taskDefinitions) {
			final TaskDefinitionModel templateTaskDefinition = new TaskDefinitionModel(taskDefinition);

			final StudioDtDefinition dtDefinition = getDtDefinition(templateTaskDefinition);
			final boolean dao = dtDefinition != null;
			if (dao) {
				//Dans le cas d'un DTO ou DTC en sortie on considère que la tache est liée au DAO.
				taskDefinitionsMap.get(dtDefinition).add(taskDefinition);
			}
		}
		return taskDefinitionsMap;

	}

	@Override
	public void clean(final FileGeneratorConfig fileGeneratorConfig, final MdaResultBuilder mdaResultBuilder) {
		MdaUtil.deleteFiles(new File(fileGeneratorConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}
}
