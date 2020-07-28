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
package io.vertigo.studio.metamodel.vertigo.oom;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.source.NotebookSource;
import io.vertigo.studio.source.NotebookSourceManager;

/**
 * Test de lecture d'un OOM.
 *
 * @author npiedeloup
 */
public final class OOMParserStereotypesTest {
	private Notebook notebook;
	private AutoCloseableNode node;

	@BeforeEach
	public final void setUp() {
		node = new AutoCloseableNode(buildNodeConfig());
		DIInjector.injectMembers(this, node.getComponentSpace());
		//---
		final List<NotebookSource> resources = List.of(
				NotebookSource.of("kpr", "io/vertigo/studio/metamodel/vertigo/oom/data/domain.kpr"),
				NotebookSource.of("oom", "io/vertigo/studio/metamodel/vertigo/oom/data/Stereotypes.oom"));
		notebook = node.getComponentSpace().resolve(NotebookSourceManager.class).read(resources);
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

	private DtSketch getDtDefinition(final String urn) {
		return notebook.resolve(urn, DtSketch.class);
	}

	/**
	 * Test du stereotype MasterData
	 */
	@Test
	public void testStereotypeMasterData() {
		final DtSketch dtDefinitionCity = getDtDefinition("DtCity");
		Assertions.assertNotNull(dtDefinitionCity);
		Assertions.assertEquals(StudioStereotype.MasterData, dtDefinitionCity.getStereotype());

		final DtSketch dtDefinitionCommandType = getDtDefinition("DtCommandType");
		Assertions.assertNotNull(dtDefinitionCommandType);
		Assertions.assertEquals(StudioStereotype.StaticMasterData, dtDefinitionCommandType.getStereotype());
	}

	/**
	 * Test du stereotype keyConcept
	 */
	@Test
	public void testStereotypeKeyConcept() {
		final DtSketch dtDefinitionCommand = getDtDefinition("DtCommand");
		Assertions.assertNotNull(dtDefinitionCommand);
		Assertions.assertEquals(StudioStereotype.KeyConcept, dtDefinitionCommand.getStereotype());

	}

	/**
	 * Test du stereotype Data
	 */
	@Test
	public void testStereotypeData() {
		final DtSketch dtDefinitionAttachment = getDtDefinition("DtAttachment");
		Assertions.assertNotNull(dtDefinitionAttachment);
		Assertions.assertEquals(StudioStereotype.Entity, dtDefinitionAttachment.getStereotype());

		final DtSketch dtDefinitionCommandValidation = getDtDefinition("DtCommandValidation");
		Assertions.assertNotNull(dtDefinitionCommandValidation);
		Assertions.assertEquals(StudioStereotype.Entity, dtDefinitionCommandValidation.getStereotype());
	}
}
