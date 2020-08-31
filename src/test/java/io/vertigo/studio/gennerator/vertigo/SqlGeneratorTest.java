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
package io.vertigo.studio.gennerator.vertigo;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorManager;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class SqlGeneratorTest {

	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
				.addModule(new StudioFeatures()
						.withSource()
						.withVertigoSource()
						.withGenerator()
						.withVertigoMda()
						.build())
				.build();
	}

	/**
	 * Lancement du test.
	 */
	@Test
	public void testGenerate() {
		try (AutoCloseableNode studioApp = new AutoCloseableNode(buildNodeConfig())) {
			final List<Source> resources = List.of(
					Source.of("kpr", "io/vertigo/studio/source/vertigo/data/model.kpr"),
					Source.of("kpr", "io/vertigo/studio/source/vertigo/data/tasks.kpr"));
			final SourceManager sourceManager = studioApp.getComponentSpace().resolve(SourceManager.class);
			final GeneratorManager generatorManager = studioApp.getComponentSpace().resolve(GeneratorManager.class);

			final GeneratorConfig generatorConfig = GeneratorConfig.builder("io.vertigo.studio")
					.withTargetGenDir("target/")
					.addProperty("vertigo.domain.sql", "true")
					.addProperty("vertigo.domain.sql.targetSubDir", "databasegenh2")
					.addProperty("vertigo.domain.sql.baseCible", "PostgreSql")
					.addProperty("vertigo.domain.sql.generateDrop", "false")
					.addProperty("vertigo.domain.sql.generateMasterData", "false")
					.build();

			final Notebook notebook = sourceManager.read(resources);
			generatorManager.generate(notebook, generatorConfig);
		}

		try (AutoCloseableNode node = new AutoCloseableNode(SqlTestConfigurator.config())) {
			DataBaseScriptUtil.execSqlScript("target/databasegenh2/crebas.sql", node);
		}
	}

}