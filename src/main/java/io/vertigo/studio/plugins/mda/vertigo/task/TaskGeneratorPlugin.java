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
import io.vertigo.studio.impl.mda.FileGenerator;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.domain.Domain;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.task.StudioTaskDefinition;
import io.vertigo.studio.plugins.mda.vertigo.task.model.DAOModel;
import io.vertigo.studio.plugins.mda.vertigo.task.model.PAOModel;
import io.vertigo.studio.plugins.mda.vertigo.task.model.TaskAttributeModel;
import io.vertigo.studio.plugins.mda.vertigo.task.model.TaskDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

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
			final MetamodelRepository metamodelRepository,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		Assertion.check()
				.notNull(mdaConfig)
				.notNull(mdaResultBuilder);
		//-----
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.task.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		generatePaos(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder);
		generateDaos(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	/**
	 * Génération de tous les PAOs.
	 */
	private static void generatePaos(
			final MetamodelRepository metamodelRepository,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		//On liste des taches regroupées par Package.
		for (final Entry<String, List<StudioTaskDefinition>> entry : buildPackageMap(metamodelRepository).entrySet()) {
			final Collection<StudioTaskDefinition> taskDefinitionCollection = entry.getValue();
			if (!taskDefinitionCollection.isEmpty()) {
				final String packageName = entry.getKey();
				generatePao(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder, taskDefinitionCollection, packageName);
			}
		}
	}

	/**
	 * Génération de tous les DAOs.
	 */
	private static void generateDaos(
			final MetamodelRepository metamodelRepository,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		for (final Entry<StudioDtDefinition, List<StudioTaskDefinition>> entry : builDtDefinitiondMap(metamodelRepository).entrySet()) {
			final StudioDtDefinition dtDefinition = entry.getKey();
			if (dtDefinition.isPersistent()) {
				//Si DAO est persitant on génère son CRUD.
				generateDao(metamodelRepository, targetSubDir, mdaConfig, mdaResultBuilder, dtDefinition, entry.getValue());
			}
		}
	}

	/**
	 * Génération d'un DAO c'est à dire des taches afférentes à un objet.
	 */
	private static void generateDao(
			final MetamodelRepository metamodelRepository,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final StudioDtDefinition dtDefinition,
			final Collection<StudioTaskDefinition> taskDefinitions) {
		final DAOModel daoModel = new DAOModel(mdaConfig, dtDefinition, taskDefinitions, DomainUtil.createClassNameFromDtFunction(metamodelRepository));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("dao", daoModel)
				.build();

		FileGenerator.builder(mdaConfig)
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
			final MetamodelRepository metamodelRepository,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final Collection<StudioTaskDefinition> taskDefinitionCollection,
			final String packageName) {
		final PAOModel paoModel = new PAOModel(mdaConfig, taskDefinitionCollection, packageName, DomainUtil.createClassNameFromDtFunction(metamodelRepository));

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("pao", paoModel)
				.build();

		FileGenerator.builder(mdaConfig)
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
	private static String getDtDefinition(final TaskDefinitionModel templateTaskDefinition) {
		if (templateTaskDefinition.isOut()) {
			//si out on regarde si en sortie on a un DTO ou une DTC typé.
			final Domain outDomain = templateTaskDefinition.getOutAttribute().getDomain();
			if (outDomain.getScope().isDataObject()) {
				return outDomain.getDtDefinitionName();
			}
			return null;
		}
		//there is no OUT param
		//We are searching igf there is an no-ambiguous IN param defined as a DataObject(DTO or DTC)
		final List<Domain> candidates = templateTaskDefinition.getInAttributes()
				.stream()
				.map(TaskAttributeModel::getDomain)
				.filter(smartType -> smartType.getScope().isDataObject())
				.collect(Collectors.toList());
		//There MUST be only ONE candidate
		if (candidates.size() == 1) {
			return candidates.get(0).getDtDefinitionName();
		}
		//Ambiguosity => PAO
		return null;
	}

	private static Map<String, List<StudioTaskDefinition>> buildPackageMap(final MetamodelRepository metamodelRepository) {
		final Collection<StudioTaskDefinition> taskDefinitions = metamodelRepository.getAll(StudioTaskDefinition.class);
		final Map<String, List<StudioTaskDefinition>> taskDefinitionsMap = new LinkedHashMap<>();
		//---
		for (final StudioTaskDefinition taskDefinition : taskDefinitions) {
			final TaskDefinitionModel templateTaskDefinition = new TaskDefinitionModel(taskDefinition, DomainUtil.createClassNameFromDtFunction(metamodelRepository));
			final String dtDefinition = getDtDefinition(templateTaskDefinition);
			// Correction bug : task avec retour DtObject (non persistant) non générée
			//Les taches sont générées dans les pao
			// - si il n'esxiste pas de définition associées à la tache
			// - ou si la définition est considérée comme non persistante.
			final boolean pao = dtDefinition == null || !metamodelRepository.resolve("St" + dtDefinition, StudioDtDefinition.class).isPersistent();
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

	private static Map<StudioDtDefinition, List<StudioTaskDefinition>> builDtDefinitiondMap(final MetamodelRepository metamodelRepository) {
		final Collection<StudioTaskDefinition> taskDefinitions = metamodelRepository.getAll(StudioTaskDefinition.class);
		final Map<StudioDtDefinition, List<StudioTaskDefinition>> taskDefinitionsMap = new LinkedHashMap<>();

		//---
		//Par défaut, On crée pour chaque DT une liste vide des taches lui étant associées.
		final Collection<StudioDtDefinition> dtDefinitions = metamodelRepository.getAll(StudioDtDefinition.class);
		for (final StudioDtDefinition dtDefinition : dtDefinitions) {
			taskDefinitionsMap.put(dtDefinition, new ArrayList<StudioTaskDefinition>());
		}
		//---
		for (final StudioTaskDefinition taskDefinition : taskDefinitions) {
			final TaskDefinitionModel templateTaskDefinition = new TaskDefinitionModel(taskDefinition, DomainUtil.createClassNameFromDtFunction(metamodelRepository));

			final String dtDefinition = getDtDefinition(templateTaskDefinition);
			final boolean dao = dtDefinition != null;
			if (dao) {
				//Dans le cas d'un DTO ou DTC en sortie on considère que la tache est liée au DAO.
				taskDefinitionsMap.get(metamodelRepository.resolve("St" + dtDefinition, StudioDtDefinition.class)).add(taskDefinition);
			}
		}
		return taskDefinitionsMap;

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
