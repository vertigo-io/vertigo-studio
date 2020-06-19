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

import io.vertigo.core.lang.BasicType;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.datamodel.impl.smarttype.formatter.FormatterDefault;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;
import io.vertigo.studio.metamodel.domain.Domain;
import io.vertigo.studio.metamodel.domain.FormatterDefinition;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.vertigo.java.data.DtDefinitions;

/**
 * Test de l'implémentation standard.
 *
 * @author dchallas
 */
public final class JavaEnvironmentManagerTest {

	private MetamodelRepository metamodelRepository;
	private AutoCloseableApp app;

	@BeforeEach
	public final void setUp() {
		app = new AutoCloseableApp(buildNodeConfig());
		DIInjector.injectMembers(this, app.getComponentSpace());
		//---
		final List<MetamodelResource> resources = Arrays.asList(
				new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/java/data/execution.kpr"),
				new MetamodelResource("classes", DtDefinitions.class.getName()));
		metamodelRepository = app.getComponentSpace().resolve(StudioMetamodelManager.class).parseResources(resources);
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

	@Test
	public void testDefaultFormatter() {
		final FormatterDefinition formatter = metamodelRepository.resolve("FmtDefault", FormatterDefinition.class);
		Assertions.assertEquals(FormatterDefault.class.getName(), formatter.getFormatterClassName());
	}

	@Test
	public void testDomain() {
		final io.vertigo.studio.metamodel.domain.Domain domain = metamodelRepository.resolve("DoId", Domain.class);
		Assertions.assertEquals(BasicType.Long, domain.getDataType());
		Assertions.assertEquals(FormatterDefault.class.getName(), domain.getFormatterDefinition().getFormatterClassName());
	}

	@Test
	public void testCommand() {
		final StudioDtDefinition dtDefinition = metamodelRepository.resolve("StDtCommand", StudioDtDefinition.class);
		Assertions.assertTrue(dtDefinition.isPersistent());
		Assertions.assertEquals("io.vertigo.studio.metamodel.vertigo.java.data.domain.Command", dtDefinition.getClassCanonicalName());
		Assertions.assertEquals("io.vertigo.studio.metamodel.vertigo.java.data.domain", dtDefinition.getPackageName());
		Assertions.assertEquals("Command", dtDefinition.getClassSimpleName());
	}

	@Test
	public void testCityFragment() {
		final StudioDtDefinition dtDefinition = metamodelRepository.resolve("StDtCityFragment", StudioDtDefinition.class);
		Assertions.assertFalse(dtDefinition.isPersistent());
		Assertions.assertTrue(dtDefinition.getFragment().isPresent());
		Assertions.assertTrue("City".equals(dtDefinition.getFragment().get().getClassSimpleName()));
		Assertions.assertEquals("io.vertigo.studio.metamodel.vertigo.java.data.domain.CityFragment", dtDefinition.getClassCanonicalName());
		Assertions.assertEquals("io.vertigo.studio.metamodel.vertigo.java.data.domain", dtDefinition.getPackageName());
		Assertions.assertEquals("CityFragment", dtDefinition.getClassSimpleName());
		Assertions.assertTrue("StDtCity".equals(dtDefinition.getField("citId").getFkDtDefinitionName()));
	}

	//	@Test
	//	public void testCreateFamille() {
	//		final Famille famille = new Famille();
	//		famille.setFamId(45L);
	//		famille.setLibelle("Armes");
	//
	//		Assertions.assertEquals(45L, famille.getFamId().longValue());
	//		Assertions.assertEquals("Armes", famille.getLibelle());
	//		Assertions.assertEquals("Armes[45]", famille.getDescription());
	//
	//		//--Vérification des appels dynamiques--
	//		final DtDefinition dtFamille = DtObjectUtil.findDtDefinition(Famille.class);
	//
	//		final DtField libelleDtField = dtFamille.getField("LIBELLE");
	//		Assertions.assertEquals("Armes", libelleDtField.getDataAccessor().getValue(famille));
	//		//-cas du id
	//		final DtField idDtField = dtFamille.getField("FAM_ID");
	//		Assertions.assertEquals(45L, idDtField.getDataAccessor().getValue(famille));
	//		//-cas du computed
	//		final DtField descriptionDtField = dtFamille.getField("DESCRIPTION");
	//		Assertions.assertEquals("Armes[45]", descriptionDtField.getDataAccessor().getValue(famille));
	//	}
}