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
package io.vertigo.studio.plugins.source.vertigo.dsl.dynamic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;

/**
 * Espace de nommage.
 * Les éléments de la grammaire à savoir les définitions sont ajoutées à la volée.
 * Les définitions doivent respecter une métagrammaire définie préalablement dans DynamicNameSpace.
 *
 * @author  pchretien
 */
public final class DslSketchesRepository {

	/***
	 * On retient les définitions dans l'ordre pour
	 * créer les fichiers toujours de la même façon.
	 */
	private final Map<String, DslSketch> dslDefinitions = new LinkedHashMap<>();
	private final List<DslSketch> partials = new ArrayList<>();

	private final DynamicRegistry registry;
	private final DslGrammar grammar;

	/**
	 * Constructor.
	 * @param registry DynamicDefinitionHandler
	 */
	public DslSketchesRepository(final DynamicRegistry registry) {
		Assertion.check().isNotNull(registry);
		//-----
		this.registry = registry;
		grammar = registry.getGrammar();
	}

	/**
	 * @return Grammar
	 */
	public DslGrammar getGrammar() {
		return grammar;
	}

	/**
	 * Returns true if a definition to which the specified name is mapped.
	 * @param definitionName name of the definitionClé de la définition
	 * @return Si la définition a déjà été enregistrée
	 */
	public boolean containsDefinitionName(final String definitionName) {
		return dslDefinitions.containsKey(definitionName);
	}

	/**
	 * Récupération d'une définition par sa clé
	 *  -Soit la clé n'existe pas
	 *  -Soit la clé existe mais sans aucune définition
	 *  -Soit la clé raméne une définition.
	 *
	 * @param definitionName Name of the definition
	 * @return DynamicDefinition Définition correspondante ou null.
	 */
	public DslSketch getDefinition(final String definitionName) {
		Assertion.check().isTrue(dslDefinitions.containsKey(definitionName), "Aucune clé enregistrée pour :{0} parmi {1}", definitionName, dslDefinitions.keySet());
		//-----
		final DslSketch definition = dslDefinitions.get(definitionName);
		//-----
		Assertion.check().isNotNull(definition, "Clé trouvée mais pas de définition enregistrée trouvée pour {0}", definitionName);
		return definition;
	}

	/**
	 * Résolution des références de définitions.
	 * @param notebook Space where all the models are stored
	 * @return a list of DefinitionSuppliers
	 */
	public List<SketchSupplier> solve(final Notebook notebook) {
		mergePartials();

		final List<DslSketch> sortedDslDefinitions = DslSolver.solve(notebook, this);
		return createModelStream(sortedDslDefinitions);
	}

	private void mergePartials() {
		//parts of definitions are merged
		for (final DslSketch partial : partials) {
			final DslSketch merged = DslSketch.builder(partial.getName(), partial.getEntity())
					.merge(getDefinition(partial.getName()))
					.merge(partial).build();
			dslDefinitions.put(partial.getName(), merged);
		}
	}

	private List<SketchSupplier> createModelStream(final List<DslSketch> sortedDynamicDefinitions) {
		return sortedDynamicDefinitions
				.stream()
				.filter(dslDefinition -> !dslDefinition.getEntity().isProvided()) // provided definitions are excluded
				.map(this::createModel)
				.collect(Collectors.toList());
	}

	private SketchSupplier createModel(final DslSketch dslDefinition) {
		DslSketchValidator.check(dslDefinition);
		//The definition identified as root are not registered.
		return registry.supplyModel(dslDefinition);
	}

	/**
	 * Add a definition.
	 * @param dslDefinition DynamicDefinition
	 */
	public void addDefinition(final DslSketch dslDefinition) {
		Assertion.check().isNotNull(dslDefinition);
		//---
		final DslSketch previousDefinition = dslDefinitions.put(dslDefinition.getName(), dslDefinition);
		Assertion.check().isNull(previousDefinition, "this definition '{0}' has already be registered", dslDefinition.getName());
		//---
		registry.onNewDefinition(dslDefinition)
				.stream()
				.forEach(this::addDefinition);
	}

	/**
	 * adds a partial definition.
	 * @param partial the part of a definition
	 */
	public void addPartialDefinition(final DslSketch partial) {
		Assertion.check().isNotNull(partial);
		//---
		partials.add(partial);
	}

	/**
	 *  @return Liste des clés orphelines.
	 */
	Set<String> getOrphanDefinitionKeys() {
		return dslDefinitions.entrySet()
				.stream()
				.filter(entry -> entry.getValue() == null) //select orphans
				.map(Entry::getKey)
				.collect(Collectors.toSet());
	}

	/**
	 * @return Liste des définitions complètes
	 */
	Collection<DslSketch> getDefinitions() {
		return Collections.unmodifiableCollection(dslDefinitions.values());
	}
}
