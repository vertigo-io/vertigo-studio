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
package io.vertigo.studio.tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import io.vertigo.commons.CommonsFeatures;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.core.plugins.resource.url.URLResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.mda.MdaResult;
import io.vertigo.studio.metamodel.StudioMetamodelManager;

/**
 * Génération des fichiers Java et SQL à patrir de fichiers template freemarker.
 *
 * @author mlaroche, pchretien
 */
public final class VertigoStudioMda {

	public static void main(final String[] args) {
		Assertion.check()
				.argument(args.length == 1, "expected the studio json config");
		//--
		final String studioProjectConfigJson = args[0];
		doMain(studioProjectConfigJson);
	}

	private static void doMain(final String studioProjectConfigJson) {
		final StudioProjectConfig studioProjectConfig = loadStudioProjectConfig(studioProjectConfigJson);
		//---
		MdaResult mdaResult = exec(studioProjectConfig);
		//---
		mdaResult.displayResultMessage(System.out);
	}

	private static MdaResult exec(StudioProjectConfig studioProjectConfig) {
		try (final AutoCloseableApp studioApp = new AutoCloseableApp(buildNodeConfig())) {
			final StudioMetamodelManager studioMetamodelManager = studioApp.getComponentSpace().resolve(StudioMetamodelManager.class);
			final MdaManager mdaManager = studioApp.getComponentSpace().resolve(MdaManager.class);
			//-----
			MdaConfig mdaConfig = studioProjectConfig.getMdaConfig();
			mdaManager.clean(mdaConfig);
			DefinitionSpace definitionSpace = studioMetamodelManager.parseResources(studioProjectConfig.getMetamodelResources());
			return mdaManager.generate(definitionSpace, mdaConfig);
		}
	}

	private static NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(URLResourceResolverPlugin.class)
				.addPlugin(LocalResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures().build())
				// ---StudioFeature
				.addModule(new StudioFeatures()
						.withMetamodel()
						.withVertigoMetamodel()
						.withMda()
						.withVertigoMda()
						.build())
				.build();

	}

	private static StudioProjectConfig loadStudioProjectConfig(final String configFileUrl) {
		try {
			return StudioConfigJsonParser.parseJson(new URL(configFileUrl));
		} catch (IOException | URISyntaxException e) {
			throw WrappedException.wrap(e);
		}
	}

}
