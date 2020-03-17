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
package io.vertigo.dynamo.environment.oom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.core.AbstractTestCaseJU5;
import io.vertigo.core.node.config.DefinitionProviderConfig;
import io.vertigo.core.node.config.ModuleConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.domain.metamodel.association.StudioAssociationNNDefinition;
import io.vertigo.dynamo.domain.metamodel.association.StudioAssociationSimpleDefinition;
import io.vertigo.dynamo.plugins.environment.StudioDefinitionProvider;

/**
 * Test de lecture d'un OOM.
 *
 * @author pchretien
 */
public class OOMParserTest extends AbstractTestCaseJU5 {
	@Override
	protected NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(ModuleConfig.builder("myApp")
						.addDefinitionProvider(DefinitionProviderConfig.builder(StudioDefinitionProvider.class)
								.addDefinitionResource("kpr", "io/vertigo/dynamo/environment/oom/data/domain.kpr")
								.addDefinitionResource("oom", "io/vertigo/dynamo/environment/oom/data/Associations.oom")
								.build())
						.build())
				.build();
	}

	/*
	 * Conventions de nommage utilisées pour les tests ci dessous.
	 * - Relation de A vers B
	 * - Cardinalité notée 	1 ou n
	 * - Navigabilité notée v
	 */
	private StudioAssociationSimpleDefinition getAssociationSimpleDefinition(final String urn) {
		return getApp().getDefinitionSpace().resolve(urn, StudioAssociationSimpleDefinition.class);
	}

	private StudioAssociationNNDefinition getAssociationNNDefinition(final String urn) {
		return getApp().getDefinitionSpace().resolve(urn, StudioAssociationNNDefinition.class);
	}

	/**
	* Test d'une relation A1 - Bnv.
	*/
	@Test
	public void testAssoctationA1Bnv() {
		final StudioAssociationSimpleDefinition association = getAssociationSimpleDefinition("StAChaChi1");
		Assertions.assertNotNull(association);
		/* "0..1" */
		Assertions.assertFalse(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R1A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R1B", association.getAssociationNodeB().getRole());

		Assertions.assertFalse(association.getAssociationNodeA().isNavigable());
		Assertions.assertTrue(association.getAssociationNodeB().isNavigable());

	}

	/**
	 * Test d'une relation A1v - Bnv.
	 */
	@Test
	public void testAssoctationA1vBnv() {
		final StudioAssociationSimpleDefinition association = getAssociationSimpleDefinition("StAChaChi2");
		/* "0..1" */
		Assertions.assertFalse(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R2A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R2B", association.getAssociationNodeB().getRole());

		Assertions.assertTrue(association.getAssociationNodeA().isNavigable());
		Assertions.assertTrue(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation A1v - Bn.
	 */
	@Test
	public void testAssoctationA1vBn() {
		final StudioAssociationSimpleDefinition association = getAssociationSimpleDefinition("StAChaChi3");
		/* "0..1" */
		Assertions.assertFalse(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R3A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R3B", association.getAssociationNodeB().getRole());

		Assertions.assertTrue(association.getAssociationNodeA().isNavigable());
		Assertions.assertFalse(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation An - B1v.
	 */
	@Test
	public void testAssoctationAnB1v() {
		final StudioAssociationSimpleDefinition association = getAssociationSimpleDefinition("StAChaChi4");
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..1" */
		Assertions.assertFalse(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R4A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R4B", association.getAssociationNodeB().getRole());

		Assertions.assertFalse(association.getAssociationNodeA().isNavigable());
		Assertions.assertTrue(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation Anv - B1.
	 */
	@Test
	public void testAssoctationAnvB1() {
		final StudioAssociationSimpleDefinition association = getAssociationSimpleDefinition("StAChaChi5");
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertFalse(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R5A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R5B", association.getAssociationNodeB().getRole());

		Assertions.assertTrue(association.getAssociationNodeA().isNavigable());
		Assertions.assertFalse(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation Anv - B1v.
	 */
	@Test
	public void testAssoctationAnvB1v() {
		final StudioAssociationSimpleDefinition association = getAssociationSimpleDefinition("StAChaChi6");
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..1" */
		Assertions.assertFalse(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R6A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R6B", association.getAssociationNodeB().getRole());

		Assertions.assertTrue(association.getAssociationNodeA().isNavigable());
		Assertions.assertTrue(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation An - Bnv.
	 */
	@Test
	public void testAssoctationAnBnv() {
		final StudioAssociationNNDefinition association = getAssociationNNDefinition("StAnnChaChi7");
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R7A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R7B", association.getAssociationNodeB().getRole());

		Assertions.assertFalse(association.getAssociationNodeA().isNavigable());
		Assertions.assertTrue(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation Anv - Bnv.
	 */
	@Test
	public void testAssoctationAnvBnv() {
		final StudioAssociationNNDefinition association = getAssociationNNDefinition("StAnnChaChi8");
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R8A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R8B", association.getAssociationNodeB().getRole());

		Assertions.assertTrue(association.getAssociationNodeA().isNavigable());
		Assertions.assertTrue(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation An - Bn.
	 */
	@Test
	public void testAssoctationAnBn() {
		final StudioAssociationNNDefinition association = getAssociationNNDefinition("StAnnChaChi9");
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R9A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R9B", association.getAssociationNodeB().getRole());

		Assertions.assertFalse(association.getAssociationNodeA().isNavigable());
		Assertions.assertFalse(association.getAssociationNodeB().isNavigable());
	}

	/**
	 * Test d'une relation Anv - Bn.
	 */
	@Test
	public void testAssoctationAnvBn() {
		final StudioAssociationNNDefinition association = getAssociationNNDefinition("StAnnChaChi10");
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeA().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeA().isNotNull());
		/* "0..*" */
		Assertions.assertTrue(association.getAssociationNodeB().isMultiple());
		Assertions.assertFalse(association.getAssociationNodeB().isNotNull());

		Assertions.assertEquals("R10A", association.getAssociationNodeA().getRole());
		Assertions.assertEquals("R10B", association.getAssociationNodeB().getRole());

		Assertions.assertTrue(association.getAssociationNodeA().isNavigable());
		Assertions.assertFalse(association.getAssociationNodeB().isNavigable());
	}
}
