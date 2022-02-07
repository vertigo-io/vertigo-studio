/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.source.vertigo.data.tasktest;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.commons.transaction.VTransactionManager;
import io.vertigo.commons.transaction.VTransactionWritable;
import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.LogConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.database.DatabaseFeatures;
import io.vertigo.database.sql.SqlManager;
import io.vertigo.database.sql.connection.SqlConnection;
import io.vertigo.datamodel.DataModelFeatures;
import io.vertigo.datamodel.impl.smarttype.ModelDefinitionProvider;
import io.vertigo.datastore.DataStoreFeatures;
import io.vertigo.studio.dao.DaoPAO;
import io.vertigo.studio.dao.car.CarDAO;
import io.vertigo.studio.generator.vertigo.DataBaseScriptUtil;
import io.vertigo.studio.plugins.generator.vertigo.task.test.TaskTestDaoChecker;
import io.vertigo.studio.plugins.generator.vertigo.task.test.TaskTestDummyGenerator;
import io.vertigo.studio.plugins.generator.vertigo.task.test.TaskTestDummyGeneratorBasic;

public class DaoTestClass {

	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private ResourceManager resourceManager;
	@Inject
	private SqlManager sqlManager;

	private VTransactionWritable currentTransaction;
	private AutoCloseableNode node;

	@BeforeEach
	public final void setUp() {
		node = new AutoCloseableNode(buildNodeConfig());
		DIInjector.injectMembers(this, node.getComponentSpace());
		//---
		execSqlScript("io/vertigo/studio/source/vertigo/data/sql/crebas.sql");
		currentTransaction = transactionManager.createCurrentTransaction();

	}

	@AfterEach
	public final void tearDown() {
		if (node != null) {
			currentTransaction.rollback();
			node.close();
		}
	}

	private NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.addPlugin(LocalResourceResolverPlugin.class)
						.withLogConfig(new LogConfig("/log4j.xml"))
						.build())
				.addModule(new CommonsFeatures()
						.withScript()
						.withJaninoScript()
						.build())
				.addModule(new DatabaseFeatures()
						.withSqlDataBase()
						.withC3p0(
								Param.of("dataBaseClass", "io.vertigo.database.impl.sql.vendor.h2.H2DataBase"),
								Param.of("jdbcDriver", "org.h2.Driver"),
								Param.of("jdbcUrl", "jdbc:h2:mem:database;NON_KEYWORDS=YEAR"))
						.build())
				.addModule(new DataModelFeatures().build())
				.addModule(new DataStoreFeatures()
						.withCache()
						.withMemoryCache()
						.withEntityStore()
						.withSqlEntityStore(
								Param.of("sequencePrefix", "SEQ_"))
						.build())
				.addModule(ModuleConfig.builder("dao")
						// to use this class for actual test target/javagen must contains those two dao classes and target/javagen must be included as a source folder
						.addDefinitionProvider(DefinitionProviderConfig.builder(ModelDefinitionProvider.class)
								.addDefinitionResource("smarttypes", DaoTestSmartTypes.class.getCanonicalName())
								.addDefinitionResource("dtobjects", io.vertigo.studio.domain.DtDefinitions.class.getCanonicalName())
								.build())
						.addComponent(CarDAO.class)
						.addComponent(DaoPAO.class)
						.build())
				.build();
	}

	private void execSqlScript(final String sqlScript) {
		final SqlConnection connection = sqlManager.getConnectionProvider(SqlManager.MAIN_CONNECTION_PROVIDER_NAME).obtainConnection();
		DataBaseScriptUtil.execSqlScript(connection, sqlScript, resourceManager, sqlManager);
	}

	private final TaskTestDummyGenerator dum = new TaskTestDummyGeneratorBasic();
	private final TaskTestDaoChecker check = new TaskTestDaoChecker();

	public TaskTestDummyGenerator dum() {
		return dum;
	}

	public TaskTestDaoChecker check() {
		return check;
	}
}
