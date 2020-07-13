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
package io.vertigo.studio.plugins.mda.vertigo.file;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.file.FileInfoSketch;
import io.vertigo.studio.plugins.mda.vertigo.file.model.FileInfoDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module File.
 *
 * @author npiedeloup
 */
public final class FileInfoGeneratorPlugin implements MdaGeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "javagen";

	/** {@inheritDoc} */
	@Override
	public void generate(
			final Notebook notebook,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(mdaConfig)
				.isNotNull(mdaResultBuilder);
		//-----
		/* Générations des FI. */
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.file.targetSubDir", DEFAULT_TARGET_SUBDIR);
		generateFileInfos(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	private static void generateFileInfos(
			final Notebook notebook,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		final Collection<FileInfoSketch> fileInfoDefinitions = notebook.getAll(FileInfoSketch.class);
		for (final FileInfoSketch fileInfoDefinition : fileInfoDefinitions) {
			generateFileInfo(targetSubDir, mdaConfig, mdaResultBuilder, fileInfoDefinition);
		}
	}

	private static void generateFileInfo(
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final FileInfoSketch fileInfoDefinition) {
		final FileInfoDefinitionModel fileInfoDefinitionModel = new FileInfoDefinitionModel(fileInfoDefinition);

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("fiDefinition", fileInfoDefinitionModel)
				.put("packageName", mdaConfig.getProjectPackageName() + ".fileinfo")
				.build();

		MdaFileGenerator.builder(mdaConfig)
				.withModel(model)
				.withFileName(fileInfoDefinitionModel.getClassSimpleName() + ".java")
				.withGenSubDir(targetSubDir)
				.withPackageName(mdaConfig.getProjectPackageName() + ".fileinfo")
				.withTemplateName(FileInfoGeneratorPlugin.class, "template/fileInfo.ftl")
				.build()
				.generateFile(mdaResultBuilder);
	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.file.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.file";
	}
}
