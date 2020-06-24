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
package io.vertigo.studio.plugins.mda.vertigo.domain.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioStereotype;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationNNDefinition;
import io.vertigo.studio.metamodel.domain.association.StudioAssociationSimpleDefinition;
import io.vertigo.studio.metamodel.domain.masterdata.MasterDataValue;
import io.vertigo.studio.metamodel.domain.masterdata.StaticMasterData;
import io.vertigo.studio.plugins.mda.vertigo.domain.sql.model.SqlMasterDataDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.sql.model.SqlMethodModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.sql.model.SqlStudioAssociationNNModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.sql.model.SqlStudioAssociationSimpleModel;
import io.vertigo.studio.plugins.mda.vertigo.domain.sql.model.SqlStudioDtDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Generate crebas.sql.
 *
 * @author pchretien, mlaroche, gpierre-nicolas
 */
public final class SqlGeneratorPlugin implements MdaGeneratorPlugin {

	private static final String DEFAULT_DATA_SPACE = "main";
	private static final String DEFAULT_TARGET_SUBDIR = "sqlgen";

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
		generateSql(definitionSpace, mdaConfig, mdaResultBuilder);

		if (mdaConfig.getOrDefaultAsBoolean("vertigo.domain.sql.generateMasterData", false)) {
			generateMasterDataInserts(definitionSpace, mdaConfig, mdaResultBuilder);
		}
	}

	private static void generateMasterDataInserts(
			final DefinitionSpace definitionSpace,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		final Map<String, Map<String, MasterDataValue>> staticMasterDataValues = definitionSpace.getAll(StaticMasterData.class).stream().collect(Collectors.toMap(StaticMasterData::getEntityClassName, StaticMasterData::getValues));

		final List<SqlMasterDataDefinitionModel> sqlMasterDataDefinitionModels = definitionSpace.getAll(StudioDtDefinition.class)
				.stream()
				.filter(dtDefinition -> dtDefinition.getStereotype() == StudioStereotype.StaticMasterData)
				.map(dtDefinition -> new SqlMasterDataDefinitionModel(dtDefinition, staticMasterDataValues.getOrDefault(dtDefinition.getClassCanonicalName(), Collections.emptyMap())))
				.collect(Collectors.toList());

		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.sql.targetSubDir", DEFAULT_TARGET_SUBDIR);

		for (final SqlMasterDataDefinitionModel sqlMasterDataDefinitionModel : sqlMasterDataDefinitionModels) {
			final Map<String, Object> model = new MapBuilder<String, Object>()
					.put("masterdata", sqlMasterDataDefinitionModel)
					.build();

			MdaFileGenerator.builder(mdaConfig)
					.withModel(model)
					.withFileName("init_masterdata_" + sqlMasterDataDefinitionModel.getDefinition().getLocalName().toLowerCase() + ".sql")
					.withGenSubDir(targetSubDir)
					.withPackageName("")
					.withTemplateName(SqlGeneratorPlugin.class, "template/init_masterdata.ftl")
					.build()
					.generateFile(mdaResultBuilder);
		}

	}

	private void generateSql(
			final DefinitionSpace definitionSpace,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {

		final Map<String, List<SqlStudioDtDefinitionModel>> mapListDtDef = new HashMap<>();
		for (final StudioDtDefinition dtDefinition : DomainUtil.sortDefinitionCollection(DomainUtil.getDtDefinitions(definitionSpace))) {
			if (dtDefinition.isPersistent()) {
				final SqlStudioDtDefinitionModel templateDef = new SqlStudioDtDefinitionModel(dtDefinition);
				final String dataSpace = dtDefinition.getDataSpace();
				final List<SqlStudioDtDefinitionModel> listDtDef = obtainListDtDefinitionPerDataSpace(mapListDtDef, dataSpace);
				listDtDef.add(templateDef);
			}
		}
		//
		final Collection<StudioAssociationSimpleDefinition> collectionSimpleAll = DomainUtil.getSimpleAssociations(definitionSpace);
		final Collection<StudioAssociationNNDefinition> collectionNNAll = DomainUtil.getNNAssociations(definitionSpace);
		//
		for (final Entry<String, List<SqlStudioDtDefinitionModel>> entry : mapListDtDef.entrySet()) {
			final String dataSpace = entry.getKey();
			final Collection<SqlStudioAssociationSimpleModel> associationSimpleDefinitions = filterAssociationSimple(collectionSimpleAll, dataSpace);
			final Collection<SqlStudioAssociationNNModel> associationNNDefinitions = filterAssociationNN(collectionNNAll, dataSpace);

			generateSqlByDataSpace(
					mdaConfig,
					mdaResultBuilder,
					associationSimpleDefinitions,
					associationNNDefinitions,
					dataSpace,
					entry.getValue());
		}
	}

	private void generateSqlByDataSpace(
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final Collection<SqlStudioAssociationSimpleModel> associationSimpleDefinitions,
			final Collection<SqlStudioAssociationNNModel> associationNNDefinitions,
			final String dataSpace,
			final List<SqlStudioDtDefinitionModel> dtDefinitions) {
		final StringBuilder filename = new StringBuilder()
				.append("crebas");
		if (!StringUtil.isBlank(dataSpace) && !DEFAULT_DATA_SPACE.equals(dataSpace)) {
			filename.append('_').append(dataSpace);
		}
		filename.append(".sql");
		generateFile(
				mdaConfig,
				mdaResultBuilder,
				dtDefinitions,
				associationSimpleDefinitions,
				associationNNDefinitions,
				filename.toString());
	}

	private static List<SqlStudioDtDefinitionModel> obtainListDtDefinitionPerDataSpace(final Map<String, List<SqlStudioDtDefinitionModel>> mapListDtDef, final String dataSpace) {
		return mapListDtDef.computeIfAbsent(dataSpace, k -> new ArrayList<>());
	}

	private static Collection<SqlStudioAssociationSimpleModel> filterAssociationSimple(
			final Collection<StudioAssociationSimpleDefinition> collectionSimpleAll,
			final String dataSpace) {
		return collectionSimpleAll.stream()
				.filter(a -> dataSpace.equals(a.getAssociationNodeA().getDtDefinition().getDataSpace()))
				.filter(a -> a.getAssociationNodeA().getDtDefinition().isPersistent() && a.getAssociationNodeB().getDtDefinition().isPersistent())
				.map(a -> new SqlStudioAssociationSimpleModel(a))
				.collect(Collectors.toList());
	}

	private static Collection<SqlStudioAssociationNNModel> filterAssociationNN(
			final Collection<StudioAssociationNNDefinition> collectionNNAll,
			final String dataSpace) {
		return collectionNNAll.stream()
				.filter(a -> dataSpace.equals(a.getAssociationNodeA().getDtDefinition().getDataSpace()))
				.filter(a -> a.getAssociationNodeA().getDtDefinition().isPersistent() && a.getAssociationNodeB().getDtDefinition().isPersistent())
				.map(a -> new SqlStudioAssociationNNModel(a))
				.collect(Collectors.toList());
	}

	private static void generateFile(
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final List<SqlStudioDtDefinitionModel> dtDefinitionModels,
			final Collection<SqlStudioAssociationSimpleModel> associationSimpleDefinitions,
			final Collection<SqlStudioAssociationNNModel> associationNNDefinitions,
			final String fileName) {

		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.sql.targetSubDir", DEFAULT_TARGET_SUBDIR);
		final String baseCible = mdaConfig.getAsString("vertigo.domain.sql.baseCible");
		//---
		final MapBuilder<String, Object> modelBuilder = new MapBuilder<String, Object>()
				.put("sql", new SqlMethodModel())
				.put("dtDefinitions", dtDefinitionModels)
				.put("simpleAssociations", associationSimpleDefinitions)
				.put("nnAssociations", associationNNDefinitions)
				.put("basecible", baseCible)
				.put("drop", mdaConfig.getOrDefaultAsBoolean("vertigo.domain.sql.generateDrop", false))
				// Oracle limite le nom des entités (index) à 30 charactères. Il faut alors tronquer les noms composés.
				.put("truncateNames", isOracle(baseCible));
		if (mdaConfig.getAsString("vertigo.domain.sql.tableSpaceData") != null) {
			modelBuilder.put("tableSpaceData", mdaConfig.getAsString("vertigo.domain.sql.tableSpaceData"));
		}
		if (mdaConfig.getAsString("vertigo.domain.sql.tableSpaceIndex") != null) {
			modelBuilder.put("tableSpaceIndex", mdaConfig.getAsString("vertigo.domain.sql.tableSpaceIndex"));
		}

		final Map<String, Object> model = modelBuilder.build();
		final String templatName = isSqlServer(baseCible) ? "template/sqlserver.ftl" : "template/sql.ftl";

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(fileName)
				.withGenSubDir(targetSubDir)
				.withPackageName("")
				.withTemplateName(SqlGeneratorPlugin.class, templatName)
				.build()
				.generateFile(mdaResultBuilder);
	}

	private static boolean isSqlServer(final String baseCible) {
		return "sqlserver".equalsIgnoreCase(baseCible) || "sql server".equalsIgnoreCase(baseCible);
	}

	private static boolean isOracle(final String baseCible) {
		return "Oracle".equalsIgnoreCase(baseCible);
	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.domain.sql.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.sql";
	}

}
