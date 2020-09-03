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
package io.vertigo.studio.plugins.source.vertigo.factories;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslSketchFactory;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.plugins.source.vertigo.factories.domain.DomainSketchFactory;
import io.vertigo.studio.plugins.source.vertigo.factories.file.FileSketchFactory;
import io.vertigo.studio.plugins.source.vertigo.factories.search.SearchSketchFactory;
import io.vertigo.studio.plugins.source.vertigo.factories.task.TaskSketchFactory;

/**
 * @author pchretien, mlaroche
 */
public final class DynamoSketchFactory implements DslSketchFactory {
	private final List<DslSketchFactory> sketchFactories;
	private final DslGrammar dslGrammar;

	/**
	 * Constructor.
	 */
	public DynamoSketchFactory() {
		sketchFactories = List.of(
				new DomainSketchFactory(),
				new TaskSketchFactory(),
				new SearchSketchFactory(),
				new FileSketchFactory());
		dslGrammar = createGrammar();
	}

	private DslGrammar createGrammar() {
		return new DslGrammar() {

			@Override
			public List<DslEntity> getEntities() {
				return sketchFactories
						.stream()
						.flatMap(sketchFactory -> sketchFactory.getGrammar().getEntities().stream())
						.collect(Collectors.toList());
			}

			@Override
			public List<DslRaw> getRootRaws() {
				return sketchFactories
						.stream()
						.flatMap(sketchFactory -> sketchFactory.getGrammar().getRootRaws().stream())
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
	public List<DslRaw> onNewRaw(final DslRaw dslDefinition) {
		//Les entités du noyaux ne sont pas à gérer par des managers spécifiques.
		if (!dslDefinition.getEntity().isProvided()) {
			return lookUpSketchFactory(dslDefinition)
					.onNewRaw(dslDefinition);
		}
		return Collections.emptyList();
	}

	/** {@inheritDoc} */
	@Override
	public List<Sketch> createSketches(final Notebook notebook, final DslRaw raw) {
		try {
			// perf: ifs ordonnés en gros par fréquence sur les projets
			return lookUpSketchFactory(raw)
					.createSketches(notebook, raw);
		} catch (final Exception e) {
			//on catch tout (notament les assertions) car c'est ici qu'on indique l'URI de la définition posant problème
			throw WrappedException.wrap(e, "An error occurred during the creation of the following definition : {0}", raw.getKey());
		}
	}

	private DslSketchFactory lookUpSketchFactory(final DslRaw raw) {
		//On regarde si la grammaire contient la métaDefinition.
		return sketchFactories
				.stream()
				.filter(sketchFactory -> sketchFactory.getGrammar().getEntities().contains(raw.getEntity()))
				.findFirst()
				//Si on n'a pas trouvé de définition c'est qu'il manque la registry.
				.orElseThrow(() -> new IllegalArgumentException(raw.getEntity().getName() + " " + raw.getKey() + " non traitée. Il manque une DynamicRegistry ad hoc."));
	}
}
