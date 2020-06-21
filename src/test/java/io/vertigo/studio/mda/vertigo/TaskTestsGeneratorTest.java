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
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;
import io.vertigo.studio.metamodel.vertigo.data.DtDefinitions;

/**
 * Test la génération à partir des oom et ksp.
 * @author dchallas
 */
public class TaskTestsGeneratorTest {
	private AutoCloseableApp app;

	@BeforeEach
	public final void setUp() {
		app = new AutoCloseableApp(buildNodeConfig());
		DIInjector.injectMembers(this, app.getComponentSpace());
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
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures().build())
				.addModule(new StudioFeatures()
						.withMetamodel()
						.withVertigoMetamodel()
						.withMda()
						.withVertigoMda()
						.build())
				.build();
	}

	@Inject
	private StudioMetamodelManager studioMetamodelManager;
	@Inject
	private MdaManager mdaManager;

	/**
	 * Lancement du test.
	 */
	@Test
	public void testGenerate() {
		final List<MetamodelResource> resources = List.of(
				MetamodelResource.of("kpr", "io/vertigo/studio/metamodel/vertigo/data/model.kpr"),
				MetamodelResource.of("kpr", "io/vertigo/studio/metamodel/vertigo/data/tasks.kpr"),
				MetamodelResource.of("classes", DtDefinitions.class.getName()));

		final MdaConfig mdaConfig = MdaConfig.builder("io.vertigo.studio")
				.withTargetGenDir("target/")
				.addProperty("vertigo.taskTest", "true")
				.addProperty("vertigo.taskTest.targetSubDir", "javagen")
				.addProperty("vertigo.taskTest.baseTestClass", "io.vertigo.studio.data.tasktest.DaoTestClass")
				.build();

		mdaManager.generate(studioMetamodelManager.parseResources(resources), mdaConfig);
	}

}
