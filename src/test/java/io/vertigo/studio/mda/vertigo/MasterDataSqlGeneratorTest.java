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

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class MasterDataSqlGeneratorTest {

	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new StudioFeatures()
						.withMetamodel()
						.withVertigoMetamodel()
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
			final List<MetamodelResource> resources = Arrays.asList(
					new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/data/model.kpr"),
					new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/data/tasks.kpr"),
					new MetamodelResource("staticMasterData", "io/vertigo/studio/metamodel/vertigo/data/masterdata/testJsonMasterDataValues.json"),
					new MetamodelResource("staticMasterData", "io/vertigo/studio/metamodel/vertigo/data/masterdata/testJsonMasterDataValues2.json"));
			final StudioMetamodelManager studioMetamodelManager = studioApp.getComponentSpace().resolve(StudioMetamodelManager.class);
			final MdaManager mdaManager = studioApp.getComponentSpace().resolve(MdaManager.class);

			final Properties mdaProperties = new Properties();
			mdaProperties.put("vertigo.domain.sql", "true");
			mdaProperties.put("vertigo.domain.sql.targetSubDir", "databasegenMasterdata");
			mdaProperties.put("vertigo.domain.sql.baseCible", "PostgreSql");
			mdaProperties.put("vertigo.domain.sql.generateDrop", "false");
			mdaProperties.put("vertigo.domain.sql.generateMasterData", "true");

			mdaManager.generate(studioMetamodelManager.parseResources(resources), MdaConfig.of("target/", "io.vertigo.studio", mdaProperties));
		}

		try (AutoCloseableApp app = new AutoCloseableApp(SqlTestConfigurator.config())) {
			DataBaseScriptUtil.execSqlScript("target/databasegenMasterdata/crebas.sql", app);
			DataBaseScriptUtil.execSqlScript("target/databasegenMasterdata/init_masterdata_command_type.sql", app);
			DataBaseScriptUtil.execSqlScript("target/databasegenMasterdata/init_masterdata_motor_type.sql", app);
		}
	}

}
