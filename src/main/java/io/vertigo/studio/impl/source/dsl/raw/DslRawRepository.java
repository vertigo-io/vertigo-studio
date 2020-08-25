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
package io.vertigo.studio.impl.source.dsl.raw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;

/**
 * Espace de nommage.
 * Les éléments de la grammaire à savoir les définitions sont ajoutées à la volée.
 * Les définitions doivent respecter une métagrammaire définie préalablement dans DynamicNameSpace.
 *
 * @author  pchretien
 */
public final class DslRawRepository {

	/***
	 * On retient les définitions dans l'ordre pour
	 * créer les fichiers toujours de la même façon.
	 */
	private final Map<DslRawKey, DslRaw> rawsByRawKeys = new LinkedHashMap<>();
	private final List<DslRaw> partialRaws = new ArrayList<>();

	private final DslSketchFactory sketchFactory;
	private final DslGrammar grammar;

	/**
	 * Constructor.
	 * @param sketchFactory DynamicDefinitionHandler
	 */
	public DslRawRepository(final DslSketchFactory sketchFactory) {
		Assertion.check().isNotNull(sketchFactory);
		//-----
		this.sketchFactory = sketchFactory;
		grammar = sketchFactory.getGrammar();
	}

	/**
	 * @return Grammar
	 */
	public DslGrammar getGrammar() {
		return grammar;
	}

	/**
	 * Returns true if a definition to which the specified name is mapped.
	 * @param sketchName name of the definitionClé de la définition
	 * @return Si la définition a déjà été enregistrée
	 */
	public boolean contains(final DslRawKey rawKey) {
		return rawsByRawKeys.containsKey(rawKey);
	}

	/**
	 * Récupération d'une définition par sa clé
	 *  -Soit la clé n'existe pas
	 *  -Soit la clé existe mais sans aucune définition
	 *  -Soit la clé raméne une définition.
	 *
	 * @param sketchName Name of the definition
	 * @return DynamicDefinition Définition correspondante ou null.
	 */
	public DslRaw getRaw(final DslRawKey rawKey) {
		Assertion.check().isTrue(rawsByRawKeys.containsKey(rawKey), "Aucune clé enregistrée pour :{0} parmi {1}", rawKey, rawsByRawKeys.keySet());
		//-----
		final DslRaw raw = rawsByRawKeys.get(rawKey);
		//-----
		Assertion.check().isNotNull(raw, "Clé trouvée mais pas de définition enregistrée trouvée pour {0}", rawKey);
		return raw;
	}

	/**
	 * Résolution des références de définitions.
	 * @param notebook Space where all the models are stored
	 * @return a list of DefinitionSuppliers
	 */
	public Stream<Sketch> solve(final Notebook notebook) {
		mergePartials();

		final List<DslRaw> sortedDslSketches = DslRawSolver.solve(notebook, this);
		return createSketchStream(notebook, sortedDslSketches);
	}

	private void mergePartials() {
		//parts of definitions are merged
		for (final DslRaw partial : partialRaws) {
			final DslRaw merged = DslRaw.builder(partial.getKey(), partial.getEntity())
					.merge(getRaw(partial.getKey()))
					.merge(partial).build();
			rawsByRawKeys.put(partial.getKey(), merged);
		}
	}

	private Stream<Sketch> createSketchStream(final Notebook notebook, final List<DslRaw> sortedRaws) {
		return sortedRaws
				.stream()
				.filter(raw -> !raw.getEntity().isProvided()) // provided definitions are excluded
				.flatMap(raw -> createSketches(notebook, raw).stream());
	}

	private List<Sketch> createSketches(final Notebook notebook, final DslRaw raw) {
		DslRawValidator.check(raw);
		//The definition identified as root are not registered.
		return sketchFactory.createSketches(notebook, raw);
	}

	/**
	 * Adds a sketch.
	 * @param raw sketch
	 */
	public void addRaw(final DslRaw raw) {
		Assertion.check().isNotNull(raw);
		//---
		final DslRaw previousRaw = rawsByRawKeys.put(raw.getKey(), raw);
		Assertion.check().isNull(previousRaw, "this sketch '{0}' has already be registered", raw.getKey());
		//---
		sketchFactory.onNewRaw(raw)
				.forEach(this::addRaw);
	}

	/**
	 * adds a partial sketch.
	 * @param partial the part of a sketch
	 */
	public void addPartialSketch(final DslRaw partial) {
		Assertion.check().isNotNull(partial);
		//---
		partialRaws.add(partial);
	}

	/**
	 *  @return Liste des clés orphelines.
	 */
	Set<DslRawKey> getOrphanDefinitionKeys() {
		return rawsByRawKeys.entrySet()
				.stream()
				.filter(entry -> entry.getValue() == null) //select orphans
				.map(Entry::getKey)
				.collect(Collectors.toSet());
	}

	/**
	 * @return Liste des définitions complètes
	 */
	Collection<DslRaw> getRaws() {
		return Collections.unmodifiableCollection(rawsByRawKeys.values());
	}
}
