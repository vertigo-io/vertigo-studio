/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.file;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.MapBuilder;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.file.FileInfoSketch;
import io.vertigo.studio.plugins.generator.vertigo.file.model.FileInfoModel;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module File.
 *
 * @author npiedeloup
 */
public final class FileInfoGeneratorPlugin implements GeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "javagen";

	/** {@inheritDoc} */
	@Override
	public void generate(
			final Notebook notebook,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(generatorConfig)
				.isNotNull(generatorResultBuilder);
		//-----
		/* Générations des FI. */
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.file.targetSubDir", DEFAULT_TARGET_SUBDIR);
		generateFileInfos(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
	}

	private static void generateFileInfos(
			final Notebook notebook,
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {
		final Collection<FileInfoSketch> fileInfoSketchs = notebook.getAll(FileInfoSketch.class);
		for (final FileInfoSketch fileInfoSketch : fileInfoSketchs) {
			generateFileInfo(targetSubDir, generatorConfig, generatorResultBuilder, fileInfoSketch);
		}
	}

	private static void generateFileInfo(
			final String targetSubDir,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final FileInfoSketch fileInfoSketch) {
		final FileInfoModel fileInfoModel = new FileInfoModel(fileInfoSketch);

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("fiDefinition", fileInfoModel)
				.put("packageName", generatorConfig.getProjectPackageName() + ".fileinfo")
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(fileInfoModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(generatorConfig.getProjectPackageName() + ".fileinfo")
				.withTemplateName(FileInfoGeneratorPlugin.class, "template/fileInfo.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.file.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.file";
	}
}
