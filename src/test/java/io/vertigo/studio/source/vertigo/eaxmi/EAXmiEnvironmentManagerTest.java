/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.source.vertigo.eaxmi;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.lang.BasicType;
import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test de l'implémentation standard.
 *
 * @author pchretien, mlaroche
 */
public final class EAXmiEnvironmentManagerTest {
	private Notebook notebook;
	private AutoCloseableNode node;

	@Inject
	private SourceManager sourceManager;

	@BeforeEach
	public final void setUp() {
		node = new AutoCloseableNode(buildNodeConfig());
		DIInjector.injectMembers(this, node.getComponentSpace());
		//---
		final List<Source> resources = List.of(
				Source.of("xmi", "io/vertigo/studio/source/vertigo/eaxmi/data/demo.xml"),
				Source.of("kpr", "io/vertigo/studio/source/vertigo/eaxmi/data/domain.kpr"));
		notebook = sourceManager.read(resources);
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
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
				.addModule(new StudioFeatures()
						.withSource()
						.withVertigoSource()
						.build())
				.build();
	}

	@Test
	public void testDomain() {
		final io.vertigo.studio.notebook.domain.DomainSketch domainSketch = notebook.resolve("DoEmail", DomainSketch.class);
		Assertions.assertEquals(BasicType.String, domainSketch.getDataType());
	}

	@Test
	public void testDtDefinition() {
		final DtSketch dtDefinition = notebook.resolve("DtFamille", DtSketch.class);
		Assertions.assertEquals("io.vertigo.dynamock.domain.famille.Famille", dtDefinition.getClassCanonicalName());
		Assertions.assertTrue(dtDefinition.isPersistent());
		Assertions.assertEquals("io.vertigo.dynamock.domain.famille", dtDefinition.getPackageName());
	}
}
