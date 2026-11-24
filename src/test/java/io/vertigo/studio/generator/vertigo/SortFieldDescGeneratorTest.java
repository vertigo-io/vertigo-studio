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
package io.vertigo.studio.generator.vertigo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorManager;
import io.vertigo.studio.generator.GeneratorResult;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.source.SourceManager;

/**
 * Test of the @SortField annotation generation with the default direction (desc).
 */
public final class SortFieldDescGeneratorTest {

	@TempDir
	Path targetGenDir;

	@Test
	public void testSortFieldDescGenerated() throws IOException {
		final String content = generate("javagen/io/vertigo/studio/domain/sortdesc/SortDesc.java");
		Assertions.assertTrue(content.contains("@io.vertigo.datamodel.data.stereotype.SortField(desc = true)"),
				"@SortField must be generated with (desc = true)");
	}

	@Test
	public void testSortFieldGeneratedPlain() throws IOException {
		final String content = generate("javagen/io/vertigo/studio/domain/sortdesc/SortAsc.java");
		Assertions.assertTrue(content.contains("@io.vertigo.datamodel.data.stereotype.SortField"),
				"@SortField must be generated");
		Assertions.assertFalse(content.contains("desc = true"),
				"@SortField must stay plain when sortFieldDesc is not set (backward compatible)");
	}

	@Test
	public void testNoSortFieldGenerated() throws IOException {
		final String content = generate("javagen/io/vertigo/studio/domain/sortdesc/NoSort.java");
		Assertions.assertFalse(content.contains("@io.vertigo.datamodel.data.stereotype.SortField"),
				"No @SortField must be generated when no sortField is declared");
	}

	private String generate(final String expectedRelativePath) throws IOException {
		try (AutoCloseableNode studioApp = new AutoCloseableNode(buildNodeConfig())) {
			final SourceManager sourceManager = studioApp.getComponentSpace().resolve(SourceManager.class);
			final GeneratorManager generatorManager = studioApp.getComponentSpace().resolve(GeneratorManager.class);

			final List<Source> resources = List.of(
					Source.of("kpr", "io/vertigo/studio/source/vertigo/sortdesc/data/generation.kpr"));

			final GeneratorConfig generatorConfig = GeneratorConfig.builder("io.vertigo.studio")
					.withTargetGenDir(targetGenDir.toString() + "/")
					.addProperty("vertigo.domain.java", "true")
					.build();

			final Notebook notebook = sourceManager.read(resources);
			final GeneratorResult result = generatorManager.generate(notebook, generatorConfig);

			Assertions.assertEquals(0, result.errorFiles(), "Generation must not fail");

			final Path generatedFile = targetGenDir.resolve(expectedRelativePath);
			Assertions.assertTrue(Files.exists(generatedFile), "Generated file must exist at " + generatedFile);
			return Files.readString(generatedFile);
		}
	}

	private static NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.build())
				.addModule(new StudioFeatures()
						.withSource()
						.withVertigoSource()
						.withGenerator()
						.withVertigoMda()
						.build())
				.build();
	}
}
