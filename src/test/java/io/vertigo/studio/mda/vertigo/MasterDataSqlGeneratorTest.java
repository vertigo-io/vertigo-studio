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
package io.vertigo.studio.mda.vertigo;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.source.NotebookSourceManager;
import io.vertigo.studio.source.NotebookSource;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class MasterDataSqlGeneratorTest {

	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
				.addModule(new CommonsFeatures().build())
				.addModule(new StudioFeatures()
						.withSource()
						.withVertigoSource()
						.withMda()
						.withVertigoMda()
						.build())
				.build();
	}

	/**
	 * Lancement du test.
	 */
	@Test
	public void testGenerate() {
		try (AutoCloseableApp studioApp = new AutoCloseableApp(buildNodeConfig())) {
			final List<NotebookSource> resources = List.of(
					NotebookSource.of("kpr", "io/vertigo/studio/metamodel/vertigo/data/model.kpr"),
					NotebookSource.of("kpr", "io/vertigo/studio/metamodel/vertigo/data/tasks.kpr"),
					NotebookSource.of("staticMasterData", "io/vertigo/studio/metamodel/vertigo/data/masterdata/testJsonMasterDataValues.json"),
					NotebookSource.of("staticMasterData", "io/vertigo/studio/metamodel/vertigo/data/masterdata/testJsonMasterDataValues2.json"));
			final NotebookSourceManager notebookSourceManager = studioApp.getComponentSpace().resolve(NotebookSourceManager.class);
			final MdaManager mdaManager = studioApp.getComponentSpace().resolve(MdaManager.class);

			final MdaConfig mdaConfig = MdaConfig.builder("io.vertigo.studio")
					.withTargetGenDir("target/")
					.addProperty("vertigo.domain.sql", "true")
					.addProperty("vertigo.domain.sql.targetSubDir", "databasegenMasterdata")
					.addProperty("vertigo.domain.sql.baseCible", "PostgreSql")
					.addProperty("vertigo.domain.sql.generateDrop", "false")
					.addProperty("vertigo.domain.sql.generateMasterData", "true")
					.build();

			mdaManager.generate(notebookSourceManager.read(resources), mdaConfig);
		}

		try (AutoCloseableApp app = new AutoCloseableApp(SqlTestConfigurator.config())) {
			DataBaseScriptUtil.execSqlScript("target/databasegenMasterdata/crebas.sql", app);
			DataBaseScriptUtil.execSqlScript("target/databasegenMasterdata/init_masterdata_command_type.sql", app);
			DataBaseScriptUtil.execSqlScript("target/databasegenMasterdata/init_masterdata_motor_type.sql", app);
		}
	}

}
