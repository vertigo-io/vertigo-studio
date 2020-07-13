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
package io.vertigo.studio.plugins.source.vertigo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.param.ParamValue;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.source.NotebookSourceReaderPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslDefinition;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslDefinitionRepository;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.loaders.Loader;
import io.vertigo.studio.plugins.source.vertigo.loaders.eaxmi.core.EAXmiLoader;
import io.vertigo.studio.plugins.source.vertigo.loaders.java.AnnotationLoader;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.KprLoader;
import io.vertigo.studio.plugins.source.vertigo.loaders.poweramc.core.OOMLoader;
import io.vertigo.studio.plugins.source.vertigo.registries.DynamoDynamicRegistry;
import io.vertigo.studio.source.NotebookSource;

/**

 * Environnement permettant de charger le Modèle.
 * Le Modèle peut être chargé de multiples façon :
 * - par lecture d'un fichier oom (poweramc),
 * - par lecture des annotations java présentes sur les beans,
 * - par lecture de fichiers ksp regoupés dans un projet kpr,
 * - ....
 *  Ces modes de chargement sont extensibles.
 *
 * @author pchretien, mlaroche
 */
public final class StudioSourceReaderPlugin implements NotebookSourceReaderPlugin {

	private final Map<String, Loader> loadersByType;

	/**
	 * Constructeur injectable.
	 * @param resourceManager the component for finding resources
	 * @param encodingOpt the encoding to use for reading ksp files
	 */
	@Inject
	public StudioSourceReaderPlugin(
			final ResourceManager resourceManager,
			@ParamValue("encoding") final Optional<String> encodingOpt,
			@ParamValue("constFieldName") final Optional<Boolean> constFieldName) {
		loadersByType = new MapBuilder<String, Loader>()
				.put("kpr", new KprLoader(resourceManager, encodingOpt))
				.put("oom", new OOMLoader(constFieldName.orElse(true), resourceManager))
				.put("xmi", new EAXmiLoader(constFieldName.orElse(true), resourceManager))
				.put("classes", new AnnotationLoader())
				.unmodifiable()
				.build();
	}

	@Override
	public List<SketchSupplier> parseResources(final List<NotebookSource> resources, final Notebook notebook) {
		//Création du repositoy des instances le la grammaire (=> model)
		final DynamicRegistry dynamoDynamicRegistry = new DynamoDynamicRegistry();
		final DslDefinitionRepository dslDefinitionRepository = new DslDefinitionRepository(dynamoDynamicRegistry);

		//--Enregistrement des types primitifs
		for (final DslDefinition dslDefinition : dynamoDynamicRegistry.getGrammar().getRootDefinitions()) {
			dslDefinitionRepository.addDefinition(dslDefinition);
		}
		for (final NotebookSource resource : resources) {
			final Loader loaderPlugin = loadersByType.get(resource.getType());
			Assertion.check().isNotNull(loaderPlugin, "This resource {0} can not be parse by these loaders : {1}", resource, loadersByType.keySet());
			loaderPlugin.load(resource.getPath(), dslDefinitionRepository);
		}

		return dslDefinitionRepository.solve(notebook);
	}

	@Override
	public Set<String> getHandledSourceTypes() {
		return Set.of("kpr", "oom", "xmi", "classes");
	}

}
