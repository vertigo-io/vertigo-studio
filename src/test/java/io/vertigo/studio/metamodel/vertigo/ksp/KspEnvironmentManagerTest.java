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
package io.vertigo.studio.metamodel.vertigo.ksp;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;

/**
 * Test ksp model.
 *
 * @author mlaroche
 */
public final class KspEnvironmentManagerTest {
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
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new StudioFeatures()
						.withMetamodel()
						.withVertigoMetamodel()
						.build())
				.build();
	}

	@Test
	public void testDomain() {
		app.getComponentSpace().resolve(StudioMetamodelManager.class)
				.parseResources(Collections.singletonList(new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/ksp/data/execution.kpr")));
	}

	@Test
	public void testWrongNavigability() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			app.getComponentSpace().resolve(StudioMetamodelManager.class)
					.parseResources(Collections.singletonList(new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/ksp/data/execution-forbidden.kpr")));
		});
	}

	@Test
	public void testNonPossibleAssociation() {
		Assertions.assertThrows(IllegalStateException.class, () -> {
			app.getComponentSpace().resolve(StudioMetamodelManager.class)
					.parseResources(Collections.singletonList(new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/ksp/data/execution-forbidden2.kpr")));
		});
	}

}
