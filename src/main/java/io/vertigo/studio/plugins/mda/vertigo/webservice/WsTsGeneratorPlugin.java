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
package io.vertigo.studio.plugins.mda.vertigo.webservice;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.mda.MdaFileGenerator;
import io.vertigo.studio.impl.mda.MdaGeneratorPlugin;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.webservices.WebServiceSketch;
import io.vertigo.studio.plugins.mda.vertigo.util.MdaUtil;
import io.vertigo.studio.plugins.mda.vertigo.webservice.model.WebServiceDefinitionModelTs;
import io.vertigo.studio.plugins.mda.vertigo.webservice.model.WebServiceInitializerModelTs;

/**
 * Generation des objets relatifs au module Vega.
 * @author npiedeloup
 */
public final class WsTsGeneratorPlugin implements MdaGeneratorPlugin {

	private static final String DEFAULT_TARGET_SUBDIR = "tsgen";

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
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.wsts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		generateRoute(notebook, targetSubDir, mdaConfig, mdaResultBuilder);
	}

	private static Collection<WebServiceSketch> getWebServiceDefinitions(final Notebook notebook) {
		return notebook.getAll(WebServiceSketch.class);
	}

	private static void generateRoute(final Notebook notebook, final String targetSubDir, final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final Collection<WebServiceSketch> webServiceDefinitions = getWebServiceDefinitions(notebook);
		if (!webServiceDefinitions.isEmpty()) {
			final Map<String, List<WebServiceDefinitionModelTs>> webServicesPerFacades = new HashMap<>();
			for (final WebServiceSketch webServiceDefinition : webServiceDefinitions) {
				//final String facadeName = webServiceDefinition.getMethod().getDeclaringClass().getSimpleName().replaceAll("WebServices", "");
				final String facadeName = webServiceDefinition.getGroupNameOpt().orElseGet(() -> webServiceDefinition.getModuleName());
				List<WebServiceDefinitionModelTs> facadeWebServiceDefinitions = webServicesPerFacades.get(facadeName);
				if (facadeWebServiceDefinitions == null) {
					facadeWebServiceDefinitions = new ArrayList<>();
					webServicesPerFacades.put(facadeName, facadeWebServiceDefinitions);
				}
				facadeWebServiceDefinitions.add(new WebServiceDefinitionModelTs(webServiceDefinition));
			}

			final Map<String, List<WebServiceInitializerModelTs>> facadeByPackage = new HashMap<>();
			for (final Map.Entry<String, List<WebServiceDefinitionModelTs>> entry : webServicesPerFacades.entrySet()) {
				final String packageName = entry.getValue().get(0).getFunctionnalPackageName();
				final String simpleClassName = entry.getKey();
				final String jsFileNameWithoutExtension = JsFileNameUtil.convertCamelCaseToJsCase(simpleClassName);
				final Set<String> importList = new HashSet<>();
				final List<WebServiceDefinitionModelTs> routeList = entry.getValue();
				for (final WebServiceDefinitionModelTs route : routeList) {
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

				MdaFileGenerator.builder(mdaConfig)
						.withModel(model)
						.withFileName(jsFileNameWithoutExtension + ".ts")
						.withGenSubDir(targetSubDir)
						.withPackageName(mdaConfig.getProjectPackageName() + ".ui." + packageName + ".services.generated")
						.withTemplateName(WsTsGeneratorPlugin.class, "template/routejsts.ftl")
						.build()
						.generateFile(mdaResultBuilder);

			}

			for (final Map.Entry<String, List<WebServiceInitializerModelTs>> entry : facadeByPackage.entrySet()) {
				final Map<String, Object> model = new MapBuilder<String, Object>()
						.put("serviceList", entry.getValue())
						.build();

				MdaFileGenerator.builder(mdaConfig)
						.withModel(model)
						.withFileName("service-gen-initializer.ts")
						.withGenSubDir(targetSubDir)
						.withPackageName(mdaConfig.getProjectPackageName() + ".ui." + entry.getKey() + ".initializer.generated")
						.withTemplateName(WsTsGeneratorPlugin.class, "template/service-initializer.ftl")
						.build()
						.generateFile(mdaResultBuilder);

				MdaFileGenerator.builder(mdaConfig)
						.withModel(model)
						.withFileName("service-type.ts")
						.withGenSubDir(targetSubDir)
						.withPackageName(mdaConfig.getProjectPackageName() + ".ui." + entry.getKey() + ".services.generated")
						.withTemplateName(WsTsGeneratorPlugin.class, "template/service-type.ftl")
						.build()
						.generateFile(mdaResultBuilder);
			}
		}
	}

	@Override
	public void clean(final MdaConfig mdaConfig, final MdaResultBuilder mdaResultBuilder) {
		final String targetSubDir = mdaConfig.getOrDefaultAsString("vertigo.wsts.targetSubDir", DEFAULT_TARGET_SUBDIR);
		//---
		MdaUtil.deleteFiles(new File(mdaConfig.getTargetGenDir() + targetSubDir), mdaResultBuilder);
	}

	@Override
	public String getOutputType() {
		return "vertigo.wsts";
	}
}
