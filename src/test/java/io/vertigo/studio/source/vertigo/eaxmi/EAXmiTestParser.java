/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.source.vertigo.eaxmi;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test de lecture d'un OOM.
 *
 * @author pchretien, mlaroche
 */
public class EAXmiTestParser {
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
				Source.of("xmi", "io/vertigo/studio/source/vertigo/eaxmi/data/associations.xml"),
				Source.of("kpr", "io/vertigo/studio/source/vertigo/eaxmi/data/domain.kpr"));
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

	/*
	 * Conventions de nommage utilisées pour les tests ci dessous.
	 * - Relation de A vers B
	 * - Cardinalité notée 	1 ou n
	 * - Navigabilité notée v
	 */
	private AssociationSimpleSketch getAssociationSimpleDefinition(final SketchKey key) {
		return notebook.resolve(key.getName(), AssociationSimpleSketch.class);
	}

	private AssociationNNSketch getAssociationNNDefinition(final SketchKey key) {
		return notebook.resolve(key.getName(), AssociationNNSketch.class);
	}

	/**
	 * Test d'une relation A1 - Bnv.
	 */
	@Test
	public void testAssoctationA1Bnv() {
		final AssociationSimpleSketch association = getAssociationSimpleDefinition(SketchKey.of("AChaChi1"));
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
		final AssociationSimpleSketch association = getAssociationSimpleDefinition(SketchKey.of("AChaChi2"));
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
	public void testAssoctationSimpleA1vBn() {
		final AssociationSimpleSketch association = getAssociationSimpleDefinition(SketchKey.of("AChaChi3"));
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
	public void testAssoctationSimpleAnB1v() {
		final AssociationSimpleSketch association = getAssociationSimpleDefinition(SketchKey.of("AChaChi4"));
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
	public void testAssoctationSimpleAnvB1() {
		final AssociationSimpleSketch association = getAssociationSimpleDefinition(SketchKey.of("AChaChi5"));
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
		final AssociationSimpleSketch association = getAssociationSimpleDefinition(SketchKey.of("AChaChi6"));
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
		final AssociationNNSketch association = getAssociationNNDefinition(SketchKey.of("AnnChaChi7"));
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
		final AssociationNNSketch association = getAssociationNNDefinition(SketchKey.of("AnnChaChi8"));
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
		final AssociationNNSketch association = getAssociationNNDefinition(SketchKey.of("AnnChaChi9"));
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
		final AssociationNNSketch association = getAssociationNNDefinition(SketchKey.of("AnnChaChi10"));
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
