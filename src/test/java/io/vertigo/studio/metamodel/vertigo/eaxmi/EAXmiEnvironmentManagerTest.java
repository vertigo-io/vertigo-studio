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
package io.vertigo.studio.metamodel.vertigo.eaxmi;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.lang.BasicType;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.datamodel.impl.smarttype.constraint.ConstraintRegex;
import io.vertigo.datamodel.impl.smarttype.formatter.FormatterDefault;
import io.vertigo.datamodel.impl.smarttype.formatter.FormatterNumber;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.ConstraintSketch;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.FormatterSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.source.NotebookSource;
import io.vertigo.studio.source.NotebookSourceManager;

/**
 * Test de l'implémentation standard.
 *
 * @author pchretien, mlaroche
 */
public final class EAXmiEnvironmentManagerTest {
	private Notebook notebook;
	private AutoCloseableApp app;

	@BeforeEach
	public final void setUp() {
		app = new AutoCloseableApp(buildNodeConfig());
		DIInjector.injectMembers(this, app.getComponentSpace());
		//---
		final List<NotebookSource> resources = List.of(
				NotebookSource.of("xmi", "io/vertigo/studio/metamodel/vertigo/eaxmi/data/demo.xml"),
				NotebookSource.of("kpr", "io/vertigo/studio/metamodel/vertigo/eaxmi/data/domain.kpr"));
		notebook = app.getComponentSpace().resolve(NotebookSourceManager.class).read(resources);
	}

	@AfterEach
	public final void tearDown() {
		if (app != null) {
			app.close();
		}
	}

	private NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
				.addModule(new StudioFeatures()
						.withMetamodel()
						.withVertigoMetamodel()
						.build())
				.build();
	}

	@Test
	public void testConstraint() {
		final ConstraintSketch constraint = notebook.resolve("CkTelephone", ConstraintSketch.class);
		Assertions.assertEquals(ConstraintRegex.class.getName(), constraint.getConstraintClassName());

	}

	@Test
	public void testDefaultFormatter() {
		final FormatterSketch formatter = notebook.resolve("FmtDefault", FormatterSketch.class);
		Assertions.assertEquals(FormatterDefault.class.getName(), formatter.getFormatterClassName());
	}

	@Test
	public void testFormatter() {
		final FormatterSketch formatter = notebook.resolve("FmtTaux", FormatterSketch.class);
		Assertions.assertEquals(FormatterNumber.class.getName(), formatter.getFormatterClassName());
	}

	@Test
	public void testDomain() {
		final io.vertigo.studio.notebook.domain.DomainSketch domainSketch = notebook.resolve("DoEmail", DomainSketch.class);
		Assertions.assertEquals(BasicType.String, domainSketch.getDataType());
		Assertions.assertEquals(FormatterDefault.class.getName(), domainSketch.getFormatterDefinition().getFormatterClassName());
	}

	@Test
	public void testDtDefinition() {
		final DtSketch dtDefinition = notebook.resolve("StDtFamille", DtSketch.class);
		Assertions.assertEquals("io.vertigo.dynamock.domain.famille.Famille", dtDefinition.getClassCanonicalName());
		Assertions.assertTrue(dtDefinition.isPersistent());
		Assertions.assertEquals("io.vertigo.dynamock.domain.famille", dtDefinition.getPackageName());
	}
}
