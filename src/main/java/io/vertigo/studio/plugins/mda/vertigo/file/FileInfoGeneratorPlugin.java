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
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.FileGenerator;
import io.vertigo.studio.impl.mda.GeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.file.StudioFileInfo;
import io.vertigo.studio.plugins.mda.vertigo.file.model.FileInfoDefinitionModel;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;

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
			final DefinitionSpace definitionSpace,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		Assertion.check()
				.notNull(mdaConfig)
				.notNull(mdaResultBuilder);
		//-----
		/* Générations des FI. */
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.file.targetSubDir", DEFAULT_TARGET_SUBDIR);
		generateFileInfos(definitionSpace, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	private static void generateFileInfos(
			final DefinitionSpace definitionSpace,
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder) {
		final Collection<StudioFileInfo> fileInfoDefinitions = definitionSpace.getAll(StudioFileInfo.class);
		for (final StudioFileInfo fileInfoDefinition : fileInfoDefinitions) {
			generateFileInfo(targetSubDir, mdaConfig, mdaResultBuilder, fileInfoDefinition);
		}
	}

	private static void generateFileInfo(
			final String targetSubDir,
			final MdaConfig mdaConfig,
			final MdaResultBuilder mdaResultBuilder,
			final StudioFileInfo fileInfoDefinition) {
		final FileInfoDefinitionModel fileInfoDefinitionModel = new FileInfoDefinitionModel(fileInfoDefinition);

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("fiDefinition", fileInfoDefinitionModel)
				.put("packageName", mdaConfig.getProjectPackageName() + ".fileinfo")
				.build();

		FileGenerator.builder(mdaConfig)
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
