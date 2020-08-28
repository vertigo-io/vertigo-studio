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
package io.vertigo.studio.source.vertigo.java;

import java.util.List;

import javax.inject.Inject;

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
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.StudioStereotype;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;
import io.vertigo.studio.source.vertigo.java.data.DtDefinitions;

/**
 * Test de lecture de class Java.
 *
 * @author npiedeloup
 */
public final class JavaParserStereotypesTest {
	private Notebook notebook;
	private AutoCloseableNode node;
	@Inject
	private SourceManager sourceManager;

	@BeforeEach
	public final void setUp() {
		node = new AutoCloseableNode(buildNodeConfig());
		DIInjector.injectMembers(this, node.getComponentSpace());
		//--
		final List<Source> resources = List.of(
				Source.of("kpr", "io/vertigo/studio/metamodel/vertigo/java/data/execution.kpr"),
				Source.of("classes", DtDefinitions.class.getName()));
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

	private DtSketch getDtSketch(final SketchKey key) {
		return notebook.resolve(key.getName(), DtSketch.class);
	}

	/**
	 * Test du stereotype MasterData
	 */
	@Test
	public void testStereotypeMasterData() {
		final DtSketch dtSketchCity = getDtSketch(SketchKey.of("DtCity"));
		Assertions.assertNotNull(dtSketchCity);
		Assertions.assertEquals(StudioStereotype.MasterData, dtSketchCity.getStereotype());

		final DtSketch dtDefinitionCommandType = getDtSketch(SketchKey.of("DtCommandType"));
		Assertions.assertNotNull(dtDefinitionCommandType);
		Assertions.assertEquals(StudioStereotype.StaticMasterData, dtDefinitionCommandType.getStereotype());
	}

	/**
	 * Test du stereotype keyConcept
	 */
	@Test
	public void testStereotypeKeyConcept() {
		final DtSketch dtDefinitionCommand = getDtSketch(SketchKey.of("DtCommand"));
		Assertions.assertNotNull(dtDefinitionCommand);
		Assertions.assertEquals(StudioStereotype.KeyConcept, dtDefinitionCommand.getStereotype());

	}

	/**
	 * Test du stereotype Data
	 */
	@Test
	public void testStereotypeEntity() {
		final DtSketch dtDefinitionAttachment = getDtSketch(SketchKey.of("DtAttachment"));
		Assertions.assertNotNull(dtDefinitionAttachment);
		Assertions.assertEquals(StudioStereotype.Entity, dtDefinitionAttachment.getStereotype());

		final DtSketch dtDefinitionCommandValidation = getDtSketch(SketchKey.of("DtCommandValidation"));
		Assertions.assertNotNull(dtDefinitionCommandValidation);
		Assertions.assertEquals(StudioStereotype.Entity, dtDefinitionCommandValidation.getStereotype());
	}

	@Test
	public void testStereotypeData() {
		final DtSketch dtDefinitionAttachment = getDtSketch(SketchKey.of("DtCommandCriteria"));
		Assertions.assertNotNull(dtDefinitionAttachment);
		Assertions.assertEquals(StudioStereotype.ValueObject, dtDefinitionAttachment.getStereotype());

	}
}
