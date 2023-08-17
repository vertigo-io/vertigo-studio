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
package io.vertigo.studio.source.vertigo.java;

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
import io.vertigo.studio.source.vertigo.java.data.DtDefinitions;

/**
 * Test de l'implémentation standard.
 *
 * @author dchallas
 */
public final class JavaEnvironmentManagerTest {
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
				Source.of("kpr", "io/vertigo/studio/source/vertigo/java/data/execution.kpr"),
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

	@Test
	public void testDomain() {
		final io.vertigo.studio.notebook.domain.DomainSketch domainSketch = notebook.resolve("DoId", DomainSketch.class);
		Assertions.assertEquals(BasicType.Long, domainSketch.getDataType());
	}

	@Test
	public void testCommand() {
		final DtSketch dtSketch = notebook.resolve("DtCommand", DtSketch.class);
		Assertions.assertTrue(dtSketch.isPersistent());
		Assertions.assertEquals("io.vertigo.studio.source.vertigo.java.data.domain.Command", dtSketch.getClassCanonicalName());
		Assertions.assertEquals("io.vertigo.studio.source.vertigo.java.data.domain", dtSketch.getPackageName());
		Assertions.assertEquals("Command", dtSketch.getClassSimpleName());
	}

	@Test
	public void testCityFragment() {
		final DtSketch dtDefinition = notebook.resolve("DtCityFragment", DtSketch.class);
		Assertions.assertFalse(dtDefinition.isPersistent());
		Assertions.assertTrue(dtDefinition.getFragment().isPresent());
		Assertions.assertTrue("City".equals(dtDefinition.getFragment().get().getClassSimpleName()));
		Assertions.assertEquals("io.vertigo.studio.source.vertigo.java.data.domain.CityFragment", dtDefinition.getClassCanonicalName());
		Assertions.assertEquals("io.vertigo.studio.source.vertigo.java.data.domain", dtDefinition.getPackageName());
		Assertions.assertEquals("CityFragment", dtDefinition.getClassSimpleName());
		Assertions.assertTrue("DtCity".equals(dtDefinition.getField("citId").getFkDtSketchName()));
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
