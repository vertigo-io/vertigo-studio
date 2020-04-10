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

import org.junit.jupiter.api.Test;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.metamodel.vertigo.data.DtDefinitions;
import io.vertigo.studio.plugins.metamodel.vertigo.StudioDefinitionProvider;
import io.vertigo.studio.tools.NameSpace2Java;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class TaskTestsGeneratorTest {

	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new StudioFeatures()
						.withMasterData()
						.withMda(
								Param.of("projectPackageName", "io.vertigo.studio"),
								Param.of("targetGenDir", "target/"))
						.withTaskTestsGenerator(
								Param.of("targetSubDir", "javagen"),
								Param.of("baseTestClass", "io.vertigo.studio.data.tasktest.DaoTestClass"))
						.build())
				.addModule(ModuleConfig.builder("myApp")
						.addDefinitionProvider(DefinitionProviderConfig.builder(StudioDefinitionProvider.class)
								.addDefinitionResource("kpr", "io/vertigo/studio/metamodel/vertigo/data/model.kpr")
								.addDefinitionResource("kpr", "io/vertigo/studio/metamodel/vertigo/data/tasks.kpr")
								.addDefinitionResource("classes", DtDefinitions.class.getName())
								.build())
						.build())
				.build();
	}

	/**
	 * Lancement du test.
	 */
	@Test
	public void testGenerate() {
		NameSpace2Java.main(buildNodeConfig());
	}

}
