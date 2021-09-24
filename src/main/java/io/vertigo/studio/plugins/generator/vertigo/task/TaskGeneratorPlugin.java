/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.MapBuilder;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.plugins.generator.vertigo.task.model.DAOModel;
import io.vertigo.studio.plugins.generator.vertigo.task.model.PAOModel;
import io.vertigo.studio.plugins.generator.vertigo.task.model.TaskAttributeModel;
import io.vertigo.studio.plugins.generator.vertigo.task.model.TaskModel;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Task.
 *
 * @author pchretien, mlaroche
 */
public final class TaskGeneratorPlugin implements GeneratorPlugin {

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
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.task.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		generatePaos(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
		generateDaos(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private static void generatePaos(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		//On liste des taches regroupées par Package.
		for (final Entry<String, List<TaskSketch>> entry : buildPackageMap(notebook).entrySet()) {
			final Collection<TaskSketch> taskSketchs = entry.getValue();
			if (!taskSketchs.isEmpty()) {
				final String packageName = entry.getKey();
				generatePao(notebook, targetSubDir, generatorConfig, generatorResultBuilder, taskSketchs, packageName);
			}
		}
	}

	/**
	 * Génération de tous les DAOs.
	 */
	private static void generateDaos(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		for (final Entry<DtSketch, List<TaskSketch>> entry : builDtSketchsMap(notebook).entrySet()) {
			final DtSketch dtSketch = entry.getKey();
			if (dtSketch.isPersistent()) {
				//Si DAO est persitant on génère son CRUD.
				generateDao(notebook, targetSubDir, generatorConfig, generatorResultBuilder, dtSketch, entry.getValue());
			}
		}
	}

	/**
	 * Génération d'un DAO c'est à dire des taches afférentes à un objet.
	 */
	private static void generateDao(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final DtSketch dtDefinition,
			final Collection<TaskSketch> taskDefinitions) {
		final DAOModel daoModel = new DAOModel(generatorConfig, dtDefinition, taskDefinitions, DomainUtil.createClassNameFromDtFunction(notebook));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("dao", daoModel)
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(daoModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(daoModel.getPackageName())
				.withTemplateName(TaskGeneratorPlugin.class, "template/dao.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	/**
	 *  Génération d'un PAO c'est à dire des taches afférentes à un package.
	 */
	private static void generatePao(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final Collection<TaskSketch> taskSketches,
			final String packageName) {
		final PAOModel paoModel = new PAOModel(generatorConfig, taskSketches, packageName, DomainUtil.createClassNameFromDtFunction(notebook));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("pao", paoModel)
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(paoModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(paoModel.getPackageName())
				.withTemplateName(TaskGeneratorPlugin.class, "template/pao.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	/**
	 * Stratégie pour savoir si une tache est PAO ou DAO.
	 * Si la DT est non null DAO sinon PAO.
	 */
	private static SketchKey getDtSketchKey(final TaskModel taskModel) {
		if (taskModel.isOut()) {
			//si out on regarde si en sortie on a un DTO ou une DTC typé.
			final DomainSketch outDomainSketch = taskModel.getOutAttribute().getDomain();
			if (outDomainSketch.getScope().isDataObject()) {
				return outDomainSketch.getDtSketchKey();
			}
			return null;
		}
		//there is no OUT param
		//We are searching igf there is an no-ambiguous IN param defined as a DataObject(DTO or DTC)
		final List<DomainSketch> candidates = taskModel.getInAttributes()
				.stream()
				.map(TaskAttributeModel::getDomain)
				.filter(smartType -> smartType.getScope().isDataObject())
				.collect(Collectors.toList());
		//There MUST be only ONE candidate
		if (candidates.size() == 1) {
			return candidates.get(0).getDtSketchKey();
		}
		//Ambiguosity => PAO
		return null;
	}

	private static Map<String, List<TaskSketch>> buildPackageMap(final Notebook notebook) {
		final Collection<TaskSketch> taskSketchs = notebook.getAll(TaskSketch.class);
		final Map<String, List<TaskSketch>> taskSketchsMap = new LinkedHashMap<>();
		//---
		for (final TaskSketch taskSketch : taskSketchs) {
			final TaskModel taskModel = new TaskModel(taskSketch, DomainUtil.createClassNameFromDtFunction(notebook));
			final SketchKey dtSketchKey = getDtSketchKey(taskModel);
			// Correction bug : task avec retour DtObject (non persistant) non générée
			//Les taches sont générées dans les pao
			// - si il n'esxiste pas de définition associées à la tache
			// - ou si la définition est considérée comme non persistante.
			final boolean pao = dtSketchKey == null || !notebook.resolve(dtSketchKey.getName(), DtSketch.class).isPersistent();
			if (pao) {
				//La tache est liée au package. (PAO)
				final List<TaskSketch> list = taskSketchsMap
						.computeIfAbsent(taskSketch.getPackageName(), k -> new ArrayList<>());
				//on ajoute la tache aux taches du package.
				list.add(taskSketch);
			}
		}
		return taskSketchsMap;

	}

	private static Map<DtSketch, List<TaskSketch>> builDtSketchsMap(final Notebook notebook) {
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
			final TaskModel taskModel = new TaskModel(taskSketch, DomainUtil.createClassNameFromDtFunction(notebook));

			final SketchKey dtSketchKey = getDtSketchKey(taskModel);
			final boolean dao = dtSketchKey != null;
			if (dao) {
				//Dans le cas d'un DTO ou DTC en sortie on considère que la tache est liée au DAO.
				taskSketchsMap.get(notebook.resolve(dtSketchKey.getName(), DtSketch.class)).add(taskSketch);
			}
		}
		return taskSketchsMap;

	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.task.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.task";
	}
}
