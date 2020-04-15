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

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.AbstractTestCaseJU5;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class SqlServerGeneratorTest extends AbstractTestCaseJU5 {

	@Override
	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new StudioFeatures()
						.withMasterData()
						.withMetamodel()
						.withVertigoMetamodel()
						.withMda(
								Param.of("projectPackageName", "io.vertigo.studio"),
								Param.of("targetGenDir", "target/"))
						.withSqlDomainGenerator(
								Param.of("targetSubDir", "databasegenh2"),
								Param.of("baseCible", "PostgreSql"),
								Param.of("generateDrop", "false"),
								Param.of("tableSpaceData", "TBL_DATA"),
								Param.of("tableSpaceIndex", "TBL_INDEX"),
								Param.of("generateMasterData", "false"))
						.build())
				.build();
	}

	@Inject
	private StudioMetamodelManager studioMetamodelManager;
	@Inject
	private MdaManager mdaManager;

	/**
	 * Lancement du test.
	 */
	@Test
	public void testGenerate() {
		final List<MetamodelResource> resources = Arrays.asList(
				new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/data/model.kpr"),
				new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/data/tasks.kpr"));
		mdaManager.generate(studioMetamodelManager.parseResources(resources));
	}

}
