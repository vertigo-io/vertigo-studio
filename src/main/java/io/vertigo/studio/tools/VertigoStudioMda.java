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
import io.vertigo.core.node.AutoCloseableNode;
import io.vertigo.core.node.config.BootConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.local.LocalResourceResolverPlugin;
import io.vertigo.core.plugins.resource.url.URLResourceResolverPlugin;
import io.vertigo.studio.StudioFeatures;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.mda.MdaResult;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.NotebookConfig;
import io.vertigo.studio.source.NotebookSourceManager;

/**
 * Génération des fichiers Java et SQL à patrir de fichiers template freemarker.
 *
 * @author mlaroche, pchretien
 */
public final class VertigoStudioMda {

	public static void main(final String[] args) {
		Assertion.check()
				.isTrue(args.length == 1, "expected the studio json config");
		//--
		final String studioProjectConfigJson = args[0];
		doMain(studioProjectConfigJson);
	}

	private static void doMain(final String studioProjectConfigJson) {
		final NotebookConfig notebookConfig = loadStudioProjectConfig(studioProjectConfigJson);
		//---
		final MdaResult mdaResult = exec(notebookConfig);
		//---
		mdaResult.displayResultMessage(System.out);
	}

	private static MdaResult exec(final NotebookConfig notebookConfig) {
		try (final AutoCloseableNode studioApp = new AutoCloseableNode(buildNodeConfig())) {
			final NotebookSourceManager notebookSourceManager = studioApp.getComponentSpace().resolve(NotebookSourceManager.class);
			final MdaManager mdaManager = studioApp.getComponentSpace().resolve(MdaManager.class);
			//-----
			final MdaConfig mdaConfig = notebookConfig.getMdaConfig();
			mdaManager.clean(mdaConfig);
			final Notebook notebook = notebookSourceManager.read(notebookConfig.getMetamodelResources());
			return mdaManager.generate(notebook, mdaConfig);
		}
	}

	private static NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.withBoot(BootConfig.builder()
						.withLocales("fr_FR")
						.addPlugin(ClassPathResourceResolverPlugin.class)
						.addPlugin(URLResourceResolverPlugin.class)
						.addPlugin(LocalResourceResolverPlugin.class)
						.build())
				.addModule(new CommonsFeatures().build())
				// ---StudioFeature
				.addModule(new StudioFeatures()
						.withSource()
						.withVertigoSource()
						.withMda()
						.withVertigoMda()
						.build())
				.build();

	}

	private static NotebookConfig loadStudioProjectConfig(final String configFileUrl) {
		try {
			return StudioConfigJsonParser.parseJson(new URL(configFileUrl));
		} catch (IOException | URISyntaxException e) {
			throw WrappedException.wrap(e);
		}
	}

}
