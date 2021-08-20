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
package io.vertigo.studio.plugins.generator.vertigo.domain.ksp;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.plugins.generator.vertigo.domain.ksp.model.KspAssociationNNModel;
import io.vertigo.studio.plugins.generator.vertigo.domain.ksp.model.KspAssociationSimpleModel;
import io.vertigo.studio.plugins.generator.vertigo.domain.ksp.model.KspDtDefinitionModel;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Generate crebas.sql.
 *
 * @author mlaroche
 */
public final class KspGeneratorPlugin implements GeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "kspgen";

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
		generateKspModel(notebook, generatorConfig, generatorResultBuilder);
	}

	private void generateKspModel(
			final Notebook notebook,
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder) {

		final Map<String, List<DtSketch>> sketckesByPackage = notebook.getAll(DtSketch.class)
				.stream()
				.collect(Collectors.groupingBy(DtSketch::getPackageName));

		final Collection<AssociationSimpleSketch> collectionSimpleAll = notebook.getAll(AssociationSimpleSketch.class);
		final Collection<AssociationNNSketch> collectionNNAll = notebook.getAll(AssociationNNSketch.class);

		sketckesByPackage.entrySet().stream()
				.forEach(entry -> generateFile(generatorConfig, generatorResultBuilder,
						entry.getKey(),
						entry.getValue().stream().map(KspDtDefinitionModel::new).collect(Collectors.toList()),
						filterAssociationSimple(collectionSimpleAll, entry.getKey()),
						filterAssociationNN(collectionNNAll, entry.getKey())));

	}

	private static Collection<KspAssociationSimpleModel> filterAssociationSimple(
			final Collection<AssociationSimpleSketch> collectionSimpleAll,
			final String packageName) {
		return collectionSimpleAll
				.stream()
				.filter(a -> packageName.equals(a.getAssociationNodeA().getDtSketch().getPackageName()))
				.map(KspAssociationSimpleModel::new)
				.collect(Collectors.toList());
	}

	private static Collection<KspAssociationNNModel> filterAssociationNN(
			final Collection<AssociationNNSketch> collectionNNAll,
			final String packageName) {
		return collectionNNAll
				.stream()
				.filter(a -> packageName.equals(a.getAssociationNodeA().getDtSketch().getPackageName()))
				.map(KspAssociationNNModel::new)
				.collect(Collectors.toList());
	}

	private static void generateFile(
			final GeneratorConfig generatorConfig,
			final GeneratorResultBuilder generatorResultBuilder,
			final String packageName,
			final List<KspDtDefinitionModel> dtSketchs,
			final Collection<KspAssociationSimpleModel> associationSimpleSketchs,
			final Collection<KspAssociationNNModel> associationNNSketchs) {

		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.ksp.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		final MapBuilder<String, Object> modelBuilder = new MapBuilder<String, Object>()
				.put("packageName", packageName)
				.put("dtSketchs", dtSketchs)
				.put("simpleAssociations", associationSimpleSketchs)
				.put("nnAssociations", associationNNSketchs);

		final Map<String, Object> model = modelBuilder.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName("model.ksp")
				.withGenSubDir(targetSubDir)
				.withPackageName(packageName)
				.withTemplateName(KspGeneratorPlugin.class, "template/ksp.ftl")
				.build()
				.generateFile(generatorResultBuilder);
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.domain.ksp.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.domain.ksp";
	}

}
