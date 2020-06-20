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
package io.vertigo.studio.metamodel.vertigo.java;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.domain.StudioStereotype;
import io.vertigo.studio.metamodel.vertigo.java.data.DtDefinitions;

/**
 * Test de lecture de class Java.
 *
 * @author npiedeloup
 */
public final class JavaParserStereotypesTest {

	private DefinitionSpace definitionSpace;
	private AutoCloseableApp app;

	@BeforeEach
	public final void setUp() {
		app = new AutoCloseableApp(buildNodeConfig());
		DIInjector.injectMembers(this, app.getComponentSpace());
		//--
		final List<MetamodelResource> resources = Arrays.asList(
				new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/java/data/execution.kpr"),
				new MetamodelResource("classes", DtDefinitions.class.getName()));
		definitionSpace = app.getComponentSpace().resolve(StudioMetamodelManager.class).parseResources(resources);
	}

	@AfterEach
	public final void tearDown() {
		if (app != null) {
			app.close();
		}
	}

	private NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new StudioFeatures()
						.withMetamodel()
						.withVertigoMetamodel()
						.build())
				.build();
	}

	private StudioDtDefinition getDtDefinition(final String urn) {
		return definitionSpace.resolve(urn, StudioDtDefinition.class);
	}

	/**
	 * Test du stereotype MasterData
	 */
	@Test
	public void testStereotypeMasterData() {
		final StudioDtDefinition dtDefinitionCity = getDtDefinition("StDtCity");
		Assertions.assertNotNull(dtDefinitionCity);
		Assertions.assertEquals(StudioStereotype.MasterData, dtDefinitionCity.getStereotype());

		final StudioDtDefinition dtDefinitionCommandType = getDtDefinition("StDtCommandType");
		Assertions.assertNotNull(dtDefinitionCommandType);
		Assertions.assertEquals(StudioStereotype.StaticMasterData, dtDefinitionCommandType.getStereotype());
	}

	/**
	 * Test du stereotype keyConcept
	 */
	@Test
	public void testStereotypeKeyConcept() {
		final StudioDtDefinition dtDefinitionCommand = getDtDefinition("StDtCommand");
		Assertions.assertNotNull(dtDefinitionCommand);
		Assertions.assertEquals(StudioStereotype.KeyConcept, dtDefinitionCommand.getStereotype());

	}

	/**
	 * Test du stereotype Data
	 */
	@Test
	public void testStereotypeEntity() {
		final StudioDtDefinition dtDefinitionAttachment = getDtDefinition("StDtAttachment");
		Assertions.assertNotNull(dtDefinitionAttachment);
		Assertions.assertEquals(StudioStereotype.Entity, dtDefinitionAttachment.getStereotype());

		final StudioDtDefinition dtDefinitionCommandValidation = getDtDefinition("StDtCommandValidation");
		Assertions.assertNotNull(dtDefinitionCommandValidation);
		Assertions.assertEquals(StudioStereotype.Entity, dtDefinitionCommandValidation.getStereotype());
	}

	@Test
	public void testStereotypeData() {
		final StudioDtDefinition dtDefinitionAttachment = getDtDefinition("StDtCommandCriteria");
		Assertions.assertNotNull(dtDefinitionAttachment);
		Assertions.assertEquals(StudioStereotype.ValueObject, dtDefinitionAttachment.getStereotype());

	}
}
