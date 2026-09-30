/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2026, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.source.vertigo.sortdesc;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test of the default sort direction (sortFieldDesc) declared in KSP.
 */
public final class SortFieldDescSketchTest {
	private AutoCloseableNode node;

	@BeforeEach
	public void setUp() {
		node = new AutoCloseableNode(buildNodeConfig());
	}

	@AfterEach
	public void tearDown() {
		if (node != null) {
			node.close();
		}
	}

	private static NodeConfig buildNodeConfig() {
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

	private Notebook readModel() {
		final SourceManager sourceManager = node.getComponentSpace().resolve(SourceManager.class);
		return sourceManager.read(List.of(Source.of("kpr", "io/vertigo/studio/source/vertigo/sortdesc/data/execution.kpr")));
	}

	private Notebook readOrphanModel() {
		final SourceManager sourceManager = node.getComponentSpace().resolve(SourceManager.class);
		return sourceManager.read(List.of(Source.of("kpr", "io/vertigo/studio/source/vertigo/sortdesc/data/orphan.kpr")));
	}

	@Test
	public void testSortDescFromKsp() {
		final DtSketch dtSortDesc = readModel().resolve("DtSortDesc", DtSketch.class);
		Assertions.assertTrue(dtSortDesc.getSortField().isPresent());
		Assertions.assertEquals("label", dtSortDesc.getSortField().get().getName());
		Assertions.assertEquals(Boolean.TRUE, dtSortDesc.getSortDesc().orElse(null));
	}

	@Test
	public void testSortAscByDefault() {
		final DtSketch dtSortAsc = readModel().resolve("DtSortAsc", DtSketch.class);
		Assertions.assertTrue(dtSortAsc.getSortField().isPresent());
		Assertions.assertEquals(Boolean.FALSE, dtSortAsc.getSortDesc().orElse(null));
	}

	@Test
	public void testNoSortFieldAtAll() {
		final DtSketch dtNoSort = readModel().resolve("DtNoSort", DtSketch.class);
		Assertions.assertFalse(dtNoSort.getSortField().isPresent());
		Assertions.assertFalse(dtNoSort.getSortDesc().isPresent());
	}

	@Test
	public void testSortDescWithoutSortFieldThrows() {
		Assertions.assertThrows(IllegalStateException.class, () -> readOrphanModel());
	}

	@Test
	public void testFragmentInheritsSortDesc() {
		final DtSketch fragment = readModel().resolve("DtSortDescFragment", DtSketch.class);
		Assertions.assertTrue(fragment.getSortField().isPresent());
		Assertions.assertEquals(Boolean.TRUE, fragment.getSortDesc().orElse(null));
	}

	@Test
	public void testFragmentOverrideWithoutDescIsAsc() {
		final DtSketch fragment = readModel().resolve("DtSortAscOverride", DtSketch.class);
		Assertions.assertTrue(fragment.getSortField().isPresent());
		Assertions.assertEquals(Boolean.FALSE, fragment.getSortDesc().orElse(null));
	}
}
