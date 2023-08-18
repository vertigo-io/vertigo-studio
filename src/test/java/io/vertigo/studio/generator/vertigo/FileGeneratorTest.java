/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.generator.vertigo;

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
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorManager;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class FileGeneratorTest {
	private AutoCloseableNode node;

	@Inject
	private SourceManager sourceManager;
	@Inject
	private GeneratorManager generatorManager;

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
		final List<Source> resources = List.of(
				Source.of("kpr", "io/vertigo/studio/source/vertigo/data/model.kpr"),
				Source.of("kpr", "io/vertigo/studio/source/vertigo/data/tasks.kpr"));

		final GeneratorConfig generatorConfig = GeneratorConfig.builder("io.vertigo.studio")
				.withTargetGenDir("target/")
				.addProperty("vertigo.file", "true")
				.build();

		final Notebook notebook = sourceManager.read(resources);
		generatorManager.generate(notebook, generatorConfig);
	}

}
