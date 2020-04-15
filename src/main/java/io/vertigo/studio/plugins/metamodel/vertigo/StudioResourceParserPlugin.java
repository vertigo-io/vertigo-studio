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
package io.vertigo.studio.plugins.metamodel.vertigo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.core.param.ParamValue;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.core.util.MapBuilder;
import io.vertigo.studio.impl.metamodel.MetamodelResourceParserPlugin;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.dynamic.DslDefinition;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.dynamic.DslDefinitionRepository;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.metamodel.vertigo.loaders.Loader;
import io.vertigo.studio.plugins.metamodel.vertigo.loaders.eaxmi.core.EAXmiLoader;
import io.vertigo.studio.plugins.metamodel.vertigo.loaders.java.AnnotationLoader;
import io.vertigo.studio.plugins.metamodel.vertigo.loaders.kpr.KprLoader;
import io.vertigo.studio.plugins.metamodel.vertigo.loaders.poweramc.core.OOMLoader;
import io.vertigo.studio.plugins.metamodel.vertigo.registries.DynamoDynamicRegistry;

/**

 * Environnement permettant de charger le Modèle.
 * Le Modèle peut être chargé de multiples façon :
 * - par lecture d'un fichier oom (poweramc),
 * - par lecture des annotations java présentes sur les beans,
 * - par lecture de fichiers ksp regoupés dans un projet kpr,
 * - ....
 *  Ces modes de chargement sont extensibles.
 *
 * @author pchretien
 */
public class StudioResourceParserPlugin implements MetamodelResourceParserPlugin {

	private final Map<String, Loader> loadersByType;

	/**
	 * Constructeur injectable.
	 * @param resourceManager the component for finding resources
	 * @param encoding the encoding to use for reading ksp files
	 */
	@Inject
	public StudioResourceParserPlugin(final ResourceManager resourceManager, @ParamValue("encoding") final Optional<String> encoding, @ParamValue("constFieldName") final Optional<Boolean> constFieldName) {
		loadersByType = new MapBuilder<String, Loader>()
				.put("kpr", new KprLoader(resourceManager, encoding))
				.put("oom", new OOMLoader(constFieldName.orElse(true), resourceManager))
				.put("xmi", new EAXmiLoader(constFieldName.orElse(true), resourceManager))
				.put("classes", new AnnotationLoader())
				.unmodifiable()
				.build();
	}

	@Override
	public List<DefinitionSupplier> parseResources(final List<MetamodelResource> resources, final MetamodelRepository metamodelRepository) {
		//Création du repositoy des instances le la grammaire (=> model)
		final DynamicRegistry dynamoDynamicRegistry = new DynamoDynamicRegistry();
		final DslDefinitionRepository dslDefinitionRepository = new DslDefinitionRepository(dynamoDynamicRegistry);

		//--Enregistrement des types primitifs
		for (final DslDefinition dslDefinition : dynamoDynamicRegistry.getGrammar().getRootDefinitions()) {
			dslDefinitionRepository.addDefinition(dslDefinition);
		}
		for (final MetamodelResource resource : resources) {
			final Loader loaderPlugin = loadersByType.get(resource.getType());
			Assertion.checkNotNull(loaderPlugin, "This resource {0} can not be parse by these loaders : {1}", resource, loadersByType.keySet());
			loaderPlugin.load(resource.getPath(), dslDefinitionRepository);
		}

		return dslDefinitionRepository.solve(metamodelRepository);
	}

	@Override
	public List<String> getHandledResourceTypes() {
		return Arrays.asList("kpr", "oom", "xmi", "classes");
	}

}
