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
package io.vertigo.studio.plugins.mda.vertigo.task;

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
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.plugins.mda.vertigo.task.model.DAOModel;
import io.vertigo.studio.plugins.mda.vertigo.task.model.PAOModel;
import io.vertigo.studio.plugins.mda.vertigo.task.model.TaskAttributeModel;
import io.vertigo.studio.plugins.mda.vertigo.task.model.TaskModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Task.
 *
 * @author pchretien, mlaroche
 */
public final class TaskGeneratorPlugin implements MdaGeneratorPlugin {

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
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.task.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		generatePaos(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
		generateDaos(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private static void generatePaos(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		//On liste des taches regroupées par Package.
		for (final Entry<String, List<TaskSketch>> entry : buildPackageMap(notebook).entrySet()) {
			final Collection<TaskSketch> taskSketchs = entry.getValue();
			if (!taskSketchs.isEmpty()) {
				final String packageName = entry.getKey();
				generatePao(notebook, targetSubDir, mdaConfig, mdaResultBuilder, taskSketchs, packageName);
			}
		}
	}

	/**
	 * Génération de tous les DAOs.
	 */
	private static void generateDaos(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		for (final Entry<DtSketch, List<TaskSketch>> entry : builDtSketchsMap(notebook).entrySet()) {
			final DtSketch dtDefinition = entry.getKey();
			if (dtDefinition.isPersistent()) {
				//Si DAO est persitant on génère son CRUD.
				generateDao(notebook, targetSubDir, mdaConfig, mdaResultBuilder, dtDefinition, entry.getValue());
			}
		}
	}

	/**
	 * Génération d'un DAO c'est à dire des taches afférentes à un objet.
	 */
	private static void generateDao(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final DtSketch dtDefinition,
			final Collection<TaskSketch> taskDefinitions) {
		final DAOModel daoModel = new DAOModel(mdaConfig, dtDefinition, taskDefinitions, DomainUtil.createClassNameFromDtFunction(notebook));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("dao", daoModel)
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(daoModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(daoModel.getPackageName())
				.withTemplateName(TaskGeneratorPlugin.class, "template/dao.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	/**
	 *  Génération d'un PAO c'est à dire des taches afférentes à un package.
	 */
	private static void generatePao(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final Collection<TaskSketch> taskDefinitionCollection,
			final String packageName) {
		final PAOModel paoModel = new PAOModel(mdaConfig, taskDefinitionCollection, packageName, DomainUtil.createClassNameFromDtFunction(notebook));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("pao", paoModel)
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(paoModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(paoModel.getPackageName())
				.withTemplateName(TaskGeneratorPlugin.class, "template/pao.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	/**
	 * Stratégie pour savoir si une tache est PAO ou DAO.
	 * Si la DT est non null DAO sinon PAO.
	 */
	private static SketchKey getDtDefinition(final TaskModel templateTaskDefinition) {
		if (templateTaskDefinition.isOut()) {
			//si out on regarde si en sortie on a un DTO ou une DTC typé.
			final DomainSketch outDomain = templateTaskDefinition.getOutAttribute().getDomain();
			if (outDomain.getScope().isDataObject()) {
				return outDomain.getDtSketchKey();
			}
			return null;
		}
		//there is no OUT param
		//We are searching igf there is an no-ambiguous IN param defined as a DataObject(DTO or DTC)
		final List<DomainSketch> candidates = templateTaskDefinition.getInAttributes()
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
			final TaskModel templateTaskDefinition = new TaskModel(taskSketch, DomainUtil.createClassNameFromDtFunction(notebook));
			final SketchKey dtSketchKey = getDtDefinition(templateTaskDefinition);
			// Correction bug : task avec retour DtObject (non persistant) non générée
			//Les taches sont générées dans les pao
			// - si il n'esxiste pas de définition associées à la tache
			// - ou si la définition est considérée comme non persistante.
			final boolean pao = dtSketchKey == null || !notebook.resolve(dtSketchKey, DtSketch.class).isPersistent();
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
			final TaskModel templateTaskDefinition = new TaskModel(taskSketch, DomainUtil.createClassNameFromDtFunction(notebook));

			final SketchKey dtSketchKey = getDtDefinition(templateTaskDefinition);
			final boolean dao = dtSketchKey != null;
			if (dao) {
				//Dans le cas d'un DTO ou DTC en sortie on considère que la tache est liée au DAO.
				taskSketchsMap.get(notebook.resolve(dtSketchKey, DtSketch.class)).add(taskSketch);
			}
		}
		return taskSketchsMap;

	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.task.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.task";
	}
}
