/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.mermaid;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.association.AssociationNNSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSimpleSketch;
import io.vertigo.studio.notebook.domain.association.AssociationSketch;
import io.vertigo.studio.plugins.generator.mermaid.model.MermaidDtModel;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;

/**
 * Génération des objets relatifs au module Domain.
 *
 * @author pchretien, mlaroche
 */
public final class MermaidGeneratorPlugin implements GeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "javagen/mermaid/";
	private static final String UNNAMED_MODULE = "app";

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

		final String targetSubDir = generatorConfig.getOrDefaultAsString("mermaid.targetSubDir", DEFAULT_TARGET_SUBDIR);
		final String mermaidDestFileName = generatorConfig.getOrDefaultAsString("mermaid.destFileName", "mermaid-" + generatorConfig.getProjectPackageName().replace('.', '-'));

		final List<MermaidDtModel> mermaidDtModels = notebook.getAll(DtSketch.class)
				.stream()
				.filter(DtSketch::isPersistent)
				//.filter(dtSketch -> dtSketch.getPackageName().contains("basemanagement"))
				.map(dtSketch -> new MermaidDtModel(dtSketch, getAssociationsByDtDefinition(notebook, dtSketch), DomainUtil.createClassNameFromDtFunction(notebook)))
				.collect(Collectors.toList());

		final Map<String, List<MermaidDtModel>> mermaidDtModelsByFeature = mermaidDtModels
				.stream()
				.collect(Collectors.groupingBy(mermaidDtModel -> extractFeatureFromPackageName(generatorConfig.getProjectPackageName(), mermaidDtModel.getDtSketch().getPackageName())));

		final Map<String, List<MermaidDtModel>> mermaidDtModelsByPackage = mermaidDtModelsByFeature.getOrDefault(UNNAMED_MODULE, Collections.emptyList())
				.stream()
				.collect(Collectors.groupingBy(mermaidDtModel -> mermaidDtModel.getDtSketch().getPackageName()));

		mermaidDtModelsByFeature.remove(UNNAMED_MODULE);

		final Map<String, Object> model = new MapBuilder<String, Object>()
				.put("dtSketchsByFeature", mermaidDtModelsByFeature)
				.put("dtSketchsByPackage", mermaidDtModelsByPackage)
				.build();

		FileGenerator.builder(generatorConfig)
				.withModel(model)
				.withFileName(mermaidDestFileName + ".html")
				.withGenSubDir(targetSubDir)
				.withPackageName("")
				.withTemplateName(MermaidGeneratorPlugin.class, "template/mermaid.ftl")
				.build()
				.generateFile(generatorResultBuilder);

	}

	private static List<AssociationSketch> getAssociationsByDtDefinition(final Notebook notebook, final DtSketch dtSketch) {
		return Stream
				.concat(notebook.getAll(AssociationSimpleSketch.class).stream(), notebook.getAll(AssociationNNSketch.class).stream())
				.filter(association -> association.getAssociationNodeA().getDtSketch().getKey().equals(dtSketch.getKey())
						|| association.getAssociationNodeB().getDtSketch().getKey().equals(dtSketch.getKey())) // concerns current dt
				.collect(Collectors.toList());
	}

	private static String extractFeatureFromPackageName(final String projectPackageName, final String dtPackageName) {
		//we need to find the featureName, aka between projectpackageName and .domain
		final String featureName = dtPackageName.substring(projectPackageName.length(), dtPackageName.indexOf(".domain"));
		if (!StringUtil.isBlank(featureName)) {
			Assertion.check().isTrue(featureName.lastIndexOf('.') == 0, "The feature {0} must not contain any dot", featureName);
			return featureName.substring(1);
		}
		return UNNAMED_MODULE;
	}

	private static String extractFirstPackageFromPackageName(final String projectPackageName, final String dtPackageName) {
		//we need to find the first package name after .domain
		final String packageSuffix = dtPackageName.substring(dtPackageName.indexOf(".domain") + ".domain".length());
		if (!StringUtil.isBlank(packageSuffix)) {
			if (packageSuffix.indexOf('.', 1) >= 0) {// there are subpackages
				return packageSuffix.substring(1, packageSuffix.indexOf('.', 1)); // strip the first dot
			}
			return packageSuffix.substring(1);
		}
		return projectPackageName;
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("mermaid.targetSubDir", DEFAULT_TARGET_SUBDIR);
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "mermaid";
	}
}
