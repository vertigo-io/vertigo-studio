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
package io.vertigo.studio.plugins.source.vertigo.registries;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketch;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;
import io.vertigo.studio.plugins.source.vertigo.registries.domain.DomainDynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.registries.file.FileDynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.registries.search.SearchDynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.registries.task.TaskDynamicRegistry;

/**
 * @author pchretien, mlaroche
 */
public final class DynamoDynamicRegistry implements DynamicRegistry {
	private final List<DynamicRegistry> dynamicRegistries;
	private final DslGrammar dslGrammar;

	/**
	 * Constructor.
	 */
	public DynamoDynamicRegistry() {
		dynamicRegistries = List.of(
				new DomainDynamicRegistry(),
				new TaskDynamicRegistry(),
				new SearchDynamicRegistry(),
				new FileDynamicRegistry());
		dslGrammar = createGrammar();
	}

	private DslGrammar createGrammar() {
		return new DslGrammar() {

			@Override
			public List<DslEntity> getEntities() {
				return dynamicRegistries
						.stream()
						.flatMap(dynamicRegistry -> dynamicRegistry.getGrammar().getEntities().stream())
						.collect(Collectors.toList());
			}

			@Override
			public List<DslSketch> getRootDefinitions() {
				return dynamicRegistries
						.stream()
						.flatMap(dynamicRegistry -> dynamicRegistry.getGrammar().getRootDefinitions().stream())
						.collect(Collectors.toList());
			}
		};
	}

	/** {@inheritDoc} */
	@Override
	public DslGrammar getGrammar() {
		return dslGrammar;
	}

	/** {@inheritDoc} */
	@Override
	public List<DslSketch> onNewSketch(final DslSketch dslDefinition) {
		//Les entités du noyaux ne sont pas à gérer par des managers spécifiques.
		if (!dslDefinition.getEntity().isProvided()) {
			return lookUpDynamicRegistry(dslDefinition)
					.onNewSketch(dslDefinition);
		}
		return Collections.emptyList();
	}

	/** {@inheritDoc} */
	@Override
	public SketchSupplier supplyModel(final DslSketch dslDefinition) {
		try {
			// perf: ifs ordonnés en gros par fréquence sur les projets
			return lookUpDynamicRegistry(dslDefinition)
					.supplyModel(dslDefinition);
		} catch (final Exception e) {
			//on catch tout (notament les assertions) car c'est ici qu'on indique l'URI de la définition posant problème
			throw WrappedException.wrap(e, "An error occurred during the creation of the following definition : {0}", dslDefinition.getName());
		}
	}

	private DynamicRegistry lookUpDynamicRegistry(final DslSketch dslDefinition) {
		//On regarde si la grammaire contient la métaDefinition.
		return dynamicRegistries
				.stream()
				.filter(dynamicRegistry -> dynamicRegistry.getGrammar().getEntities().contains(dslDefinition.getEntity()))
				.findFirst()
				//Si on n'a pas trouvé de définition c'est qu'il manque la registry.
				.orElseThrow(() -> new IllegalArgumentException(dslDefinition.getEntity().getName() + " " + dslDefinition.getName() + " non traitée. Il manque une DynamicRegistry ad hoc."));
	}
}
