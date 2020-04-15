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
package io.vertigo.studio.metamodel.vertigo.multi;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.core.AbstractTestCaseJU5;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;
import io.vertigo.studio.metamodel.domain.Domain;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;
import io.vertigo.studio.metamodel.vertigo.multi.data.DtDefinitions;

/**
 * Test de l'implémentation standard.
 *
 * @author npiedeloup
 */
public final class MultiResourcesEnvironmentManagerTest extends AbstractTestCaseJU5 {

	@Override
	protected NodeConfig buildNodeConfig() {
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
	public void testFirst() {
		final MetamodelRepository metamodelRepository = getApp().getComponentSpace().resolve(StudioMetamodelManager.class)
				.parseResources(Collections.singletonList(new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/multi/data/execution.kpr")));
		final Domain doString = metamodelRepository.resolve("DoString", Domain.class);
		Assertions.assertNotNull(doString);
	}

	@Test
	public void testMergedResources() {
		final MetamodelRepository metamodelRepository = getApp().getComponentSpace().resolve(StudioMetamodelManager.class)
				.parseResources(Arrays.asList(
						new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/multi/data/execution.kpr"),
						new MetamodelResource("classes", DtDefinitions.class.getCanonicalName())));

		final Domain doString = metamodelRepository.resolve("DoString", Domain.class);
		Assertions.assertNotNull(doString);
		final StudioDtDefinition dtItem = metamodelRepository.resolve("StDtItem", StudioDtDefinition.class);
		Assertions.assertNotNull(dtItem);
	}

}
