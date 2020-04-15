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

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.core.AbstractTestCaseJU5;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;
import io.vertigo.studio.metamodel.domain.StudioDtDefinition;

/**
 * Test de lecture d'un OOM.
 *
 * @author npiedeloup
 */
public final class EAXmiTestParserIdentifiers extends AbstractTestCaseJU5 {

	private MetamodelRepository metamodelRepository;

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

	@Override
	protected void doSetUp() throws Exception {
		final List<MetamodelResource> resources = Arrays.asList(
				new MetamodelResource("xmi", "io/vertigo/studio/metamodel/vertigo/eaxmi/data/demo.xml"),
				new MetamodelResource("kpr", "io/vertigo/studio/metamodel/vertigo/eaxmi/data/domain.kpr"));
		metamodelRepository = getApp().getComponentSpace().resolve(StudioMetamodelManager.class).parseResources(resources);
	}

	private StudioDtDefinition getDtDefinition(final String urn) {
		return metamodelRepository
				.resolve(urn, StudioDtDefinition.class);
	}

	@Test
	public void testIdentifiersVsPrimaryKey() {
		final StudioDtDefinition loginDefinition = getDtDefinition("StDtLogin");
		Assertions.assertTrue(loginDefinition.getIdField().isPresent());
	}
}
