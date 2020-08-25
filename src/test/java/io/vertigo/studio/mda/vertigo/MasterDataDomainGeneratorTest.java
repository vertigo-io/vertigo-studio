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

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class MasterDataDomainGeneratorTest {
	private AutoCloseableNode node;

	@Inject
	private SourceManager sourceManager;
	@Inject
	private MdaManager mdaManager;

	@BeforeEach
	public final void setUp() {
		node = new AutoCloseableNode(buildNodeConfig());
		DIInjector.injectMembers(this, node.getComponentSpace());
	}

	@AfterEach
	public final void tearDown() {
		if (node != null) {
			node.close();
		}
	}

	private NodeConfig buildNodeConfig() {
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
		final List<Source> resources = List.of(
				Source.of("kpr", "io/vertigo/studio/metamodel/vertigo/data/model.kpr"),
				Source.of("kpr", "io/vertigo/studio/metamodel/vertigo/data/tasks.kpr"),
				Source.of("staticMasterData", "io/vertigo/studio/metamodel/vertigo/data/masterdata/testJsonMasterDataValues.json"),
				Source.of("staticMasterData", "io/vertigo/studio/metamodel/vertigo/data/masterdata/testJsonMasterDataValues2.json"));

		final MdaConfig mdaConfig = MdaConfig.builder("io.vertigo.studio")
				.withTargetGenDir("target/")
				.addProperty("vertigo.domain.java", "true")
				.build();

		final Notebook notebook = sourceManager.read(resources);
		mdaManager.generate(notebook, mdaConfig);
	}

}
