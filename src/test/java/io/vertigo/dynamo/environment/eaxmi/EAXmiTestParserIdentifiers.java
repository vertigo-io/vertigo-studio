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
package io.vertigo.dynamo.environment.eaxmi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.core.AbstractTestCaseJU5;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.domain.metamodel.StudioDtDefinition;
import io.vertigo.dynamo.plugins.environment.StudioDefinitionProvider;

/**
 * Test de lecture d'un OOM.
 *
 * @author npiedeloup
 */
public final class EAXmiTestParserIdentifiers extends AbstractTestCaseJU5 {
	@Override
	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(ModuleConfig.builder("myApp")
						.addDefinitionProvider(DefinitionProviderConfig.builder(StudioDefinitionProvider.class)
								.addDefinitionResource("xmi", "io/vertigo/dynamo/environment/eaxmi/data/demo.xml")
								.addDefinitionResource("kpr", "io/vertigo/dynamo/environment/eaxmi/data/domain.kpr")
								.build())
						.build())
				.build();
	}

	private StudioDtDefinition getDtDefinition(final String urn) {
		return getApp().getDefinitionSpace()
				.resolve(urn, StudioDtDefinition.class);
	}

	@Test
	public void testIdentifiersVsPrimaryKey() {
		final StudioDtDefinition loginDefinition = getDtDefinition("StDtLogin");
		Assertions.assertTrue(loginDefinition.getIdField().isPresent());
	}
}
