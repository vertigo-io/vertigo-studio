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
package io.vertigo.studio.plugins.generator.vertigo.webservice;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.MapBuilder;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.impl.generator.FileGenerator;
import io.vertigo.studio.impl.generator.GeneratorPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.webservices.WebServiceSketch;
import io.vertigo.studio.plugins.generator.vertigo.util.MdaUtil;
import io.vertigo.studio.plugins.generator.vertigo.webservice.model.WebServiceInitializerModelTs;
import io.vertigo.studio.plugins.generator.vertigo.webservice.model.WebServiceModelTs;

/**
 * Generation des objets relatifs au module Vega.
 * @author npiedeloup
 */
public final class WsTsGeneratorPlugin implements GeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "tsgen";

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
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.wsts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		generateRoute(notebook, targetSubDir, generatorConfig, generatorResultBuilder);
	}

	private static Collection<WebServiceSketch> getWebServiceSketchs(final Notebook notebook) {
		return notebook.getAll(WebServiceSketch.class);
	}

	private static void generateRoute(final Notebook notebook, final String targetSubDir, final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final Collection<WebServiceSketch> webServiceSketchs = getWebServiceSketchs(notebook);
		if (!webServiceSketchs.isEmpty()) {
			final Map<String, List<WebServiceModelTs>> webServicesPerFacades = new HashMap<>();
			for (final WebServiceSketch webServiceSketch : webServiceSketchs) {
				//final String facadeName = webServiceDefinition.getMethod().getDeclaringClass().getSimpleName().replaceAll("WebServices", "");
				final String facadeName = webServiceSketch.getGroupNameOpt().orElseGet(webServiceSketch::getModuleName);
				List<WebServiceModelTs> webServiceModels = webServicesPerFacades.get(facadeName);
				if (webServiceModels == null) {
					webServiceModels = new ArrayList<>();
					webServicesPerFacades.put(facadeName, webServiceModels);
				}
				webServiceModels.add(new WebServiceModelTs(webServiceSketch));
			}

			final Map<String, List<WebServiceInitializerModelTs>> facadeByPackage = new HashMap<>();
			for (final Map.Entry<String, List<WebServiceModelTs>> entry : webServicesPerFacades.entrySet()) {
				final String packageName = entry.getValue().get(0).getFunctionnalPackageName();
				final String simpleClassName = entry.getKey();
				final String jsFileNameWithoutExtension = JsFileNameUtil.convertCamelCaseToJsCase(simpleClassName);
				final Set<String> importList = new HashSet<>();
				final List<WebServiceModelTs> routeList = entry.getValue();
				for (final WebServiceModelTs route : routeList) {
					importList.addAll(route.getImportList());
				}

				if (!facadeByPackage.containsKey(packageName)) {
					facadeByPackage.put(packageName, new ArrayList<WebServiceInitializerModelTs>());
				}
				facadeByPackage.get(packageName).add(new WebServiceInitializerModelTs(jsFileNameWithoutExtension, simpleClassName));

				final Map<String, Object> model = new MapBuilder<String, Object>()
						.put("routes", entry.getValue())
						.put("importList", importList)
						.build();

				FileGenerator.builder(generatorConfig)
						.withModel(model)
						.withFileName(jsFileNameWithoutExtension + ".ts")
						.withGenSubDir(targetSubDir)
						.withPackageName(generatorConfig.getProjectPackageName() + ".ui." + packageName + ".services.generated")
						.withTemplateName(WsTsGeneratorPlugin.class, "template/routejsts.ftl")
						.build()
						.generateFile(generatorResultBuilder);

			}

			for (final Map.Entry<String, List<WebServiceInitializerModelTs>> entry : facadeByPackage.entrySet()) {
				final Map<String, Object> model = new MapBuilder<String, Object>()
						.put("serviceList", entry.getValue())
						.build();

				FileGenerator.builder(generatorConfig)
						.withModel(model)
						.withFileName("service-gen-initializer.ts")
						.withGenSubDir(targetSubDir)
						.withPackageName(generatorConfig.getProjectPackageName() + ".ui." + entry.getKey() + ".initializer.generated")
						.withTemplateName(WsTsGeneratorPlugin.class, "template/service-initializer.ftl")
						.build()
						.generateFile(generatorResultBuilder);

				FileGenerator.builder(generatorConfig)
						.withModel(model)
						.withFileName("service-type.ts")
						.withGenSubDir(targetSubDir)
						.withPackageName(generatorConfig.getProjectPackageName() + ".ui." + entry.getKey() + ".services.generated")
						.withTemplateName(WsTsGeneratorPlugin.class, "template/service-type.ftl")
						.build()
						.generateFile(generatorResultBuilder);
			}
		}
	}

	@Override
	public void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder) {
		final String targetSubDir = generatorConfig.getOrDefaultAsString("vertigo.wsts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(generatorConfig.getTargetGenDir() + targetSubDir), generatorResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.wsts";
	}
}
