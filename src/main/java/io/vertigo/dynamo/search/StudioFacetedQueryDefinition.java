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
package io.vertigo.dynamo.search;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionPrefix;
import io.vertigo.datafactory.collections.metamodel.ListFilterBuilder;
import io.vertigo.dynamo.domain.metamodel.Domain;
import io.vertigo.dynamo.domain.metamodel.StudioDtDefinition;

/**
 * Définition des requêtes d'accès à l'index de recherche.
 *
 * les requêtes sont facettées.
 *
 * @author pchretien
 */
@DefinitionPrefix("StQry")
public final class StudioFacetedQueryDefinition implements Definition {
	/**
	 * Nom de la définition.
	 */
	private final String name;

	private final StudioDtDefinition keyConceptDtDefinition;

	/** Liste indexée des facettes.*/
	private final Map<String, StudioFacetDefinition> facetDefinitions = new LinkedHashMap<>();

	/** Domain du criteria. */
	private final Domain criteriaDomain;

	/** Query du listFilterBuilder. */
	private final String listFilterBuilderQuery;

	/**
	 * Moyen de créer le ListFilter à partir du Criteria.
	 */
	private final Class<? extends ListFilterBuilder> listFilterBuilderClass;

	/**
	 * Constructor.
	 * @param name Nom de la definition
	 * @param keyConceptDtDefinition Definition du keyConcept sur lequel s'applique cette recherche
	 * @param facetDefinitions Liste des facettes
	 * @param criteriaDomain Criteria's domain
	 * @param listFilterBuilderClass listFilterBuilderClass to use
	 * @param listFilterBuilderQuery listFilterBuilderQuery to use
	 */
	public StudioFacetedQueryDefinition(
			final String name,
			final StudioDtDefinition keyConceptDtDefinition,
			final List<StudioFacetDefinition> facetDefinitions,
			final Domain criteriaDomain,
			final Class<? extends ListFilterBuilder> listFilterBuilderClass,
			final String listFilterBuilderQuery) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkNotNull(keyConceptDtDefinition);
		Assertion.checkNotNull(facetDefinitions);
		Assertion.checkNotNull(criteriaDomain);
		Assertion.checkNotNull(listFilterBuilderClass);
		Assertion.checkNotNull(listFilterBuilderQuery);
		//-----
		this.name = name;
		this.keyConceptDtDefinition = keyConceptDtDefinition;
		for (final StudioFacetDefinition facetDefinition : facetDefinitions) {
			this.facetDefinitions.put(facetDefinition.getName(), facetDefinition);
		}
		this.criteriaDomain = criteriaDomain;
		this.listFilterBuilderClass = listFilterBuilderClass;
		this.listFilterBuilderQuery = listFilterBuilderQuery;
	}

	/**
	 * Retourne la facette identifié par son nom.
	 *
	 * @param facetName Nom de la facette recherché.
	 * @return Définition de la facette.
	 */
	public StudioFacetDefinition getFacetDefinition(final String facetName) {
		Assertion.checkArgNotEmpty(facetName);
		//-----
		final StudioFacetDefinition facetDefinition = facetDefinitions.get(facetName);
		//-----
		Assertion.checkNotNull(facetDefinition, "Aucune Définition de facette trouvée pour {0}", facetName);
		return facetDefinition;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Définition du keyConcept de cette recherche.
	 * @return Définition du keyConcept.
	 */
	public StudioDtDefinition getKeyConceptDtDefinition() {
		return keyConceptDtDefinition;
	}

	/**
	 * @return Liste des facettes portées par l'index.
	 */
	public Collection<StudioFacetDefinition> getFacetDefinitions() {
		return Collections.unmodifiableCollection(facetDefinitions.values());
	}

	/**
	 * @return Domain du criteria.
	 */
	public Domain getCriteriaDomain() {
		return criteriaDomain;
	}

	/**
	  * @return Class du ListFilterBuilder.
	 */
	public Class<? extends ListFilterBuilder> getListFilterBuilderClass() {
		return listFilterBuilderClass;
	}

	/**
	 * @return Query du ListFilterBuilder.
	 */
	public String getListFilterBuilderQuery() {
		return listFilterBuilderQuery;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name;
	}
}
