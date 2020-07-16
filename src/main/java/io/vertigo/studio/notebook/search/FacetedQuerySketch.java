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
package io.vertigo.studio.notebook.search;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;

/**
 * Définition des requêtes d'accès à l'index de recherche.
 *
 * les requêtes sont facettées.
 *
 * @author pchretien, mlaroche
 */
@SkecthPrefix(FacetedQuerySketch.PREFIX)
public final class FacetedQuerySketch extends AbstractSketch {
	public static final String PREFIX = "Qry";

	private final DtSketch keyConceptDtDefinition;

	/** Liste indexée des facettes.*/
	private final Map<String, FacetSketch> facetDefinitions = new LinkedHashMap<>();

	/** Domain du criteria. */
	private final DomainSketch criteriaDomain;

	/** Query du listFilterBuilder. */
	private final String listFilterBuilderQuery;
	/** Query du geoSearchQuery. */
	private final Optional<String> geoSearchQueryOpt;

	/**
	 * Moyen de créer le ListFilter à partir du Criteria.
	 */
	private final String listFilterBuilderClassName;

	/**
	 * Constructor.
	 * @param name Nom de la definition
	 * @param keyConceptDtDefinition Definition du keyConcept sur lequel s'applique cette recherche
	 * @param facetDefinitions Liste des facettes
	 * @param criteriaDomain Criteria's domain
	 * @param listFilterBuilderClassName listFilterBuilderClass to use
	 * @param listFilterBuilderQuery listFilterBuilderQuery to use
	 */
	public FacetedQuerySketch(
			final String name,
			final DtSketch keyConceptDtDefinition,
			final List<FacetSketch> facetDefinitions,
			final DomainSketch criteriaDomain,
			final String listFilterBuilderClassName,
			final String listFilterBuilderQuery,
			final Optional<String> geoSearchQuery) {
		super(name);
		//---
		Assertion
				.check()
				.isNotBlank(name)
				.isNotNull(keyConceptDtDefinition)
				.isNotNull(facetDefinitions)
				.isNotNull(criteriaDomain)
				.isNotBlank(listFilterBuilderClassName)
				.isNotNull(listFilterBuilderQuery)
				.isNotNull(geoSearchQuery);
		//---
		this.keyConceptDtDefinition = keyConceptDtDefinition;
		for (final FacetSketch facetDefinition : facetDefinitions) {
			this.facetDefinitions.put(facetDefinition.getName(), facetDefinition);
		}
		this.criteriaDomain = criteriaDomain;
		this.listFilterBuilderClassName = listFilterBuilderClassName;
		this.listFilterBuilderQuery = listFilterBuilderQuery;
		geoSearchQueryOpt = geoSearchQuery;
	}

	/**
	 * Retourne la facette identifié par son nom.
	 *
	 * @param facetName Nom de la facette recherché.
	 * @return Définition de la facette.
	 */
	public FacetSketch getFacetDefinition(final String facetName) {
		Assertion.check().isNotBlank(facetName);
		//-----
		final FacetSketch facetDefinition = facetDefinitions.get(facetName);
		//-----
		Assertion.check().isNotNull(facetDefinition, "Aucune Définition de facette trouvée pour {0}", facetName);
		return facetDefinition;
	}

	/**
	 * Définition du keyConcept de cette recherche.
	 * @return Définition du keyConcept.
	 */
	public DtSketch getKeyConceptDtDefinition() {
		return keyConceptDtDefinition;
	}

	/**
	 * @return Liste des facettes portées par l'index.
	 */
	public Collection<FacetSketch> getFacetDefinitions() {
		return Collections.unmodifiableCollection(facetDefinitions.values());
	}

	/**
	 * @return Domain du criteria.
	 */
	public DomainSketch getCriteriaDomain() {
		return criteriaDomain;
	}

	/**
	  * @return Class du ListFilterBuilder.
	 */
	public String getListFilterBuilderClassName() {
		return listFilterBuilderClassName;
	}

	/**
	 * @return Query du ListFilterBuilder.
	 */
	public String getListFilterBuilderQuery() {
		return listFilterBuilderQuery;
	}

	/**
	 * @return if there is a geoSearchQuery.
	 */
	public String getGeoSearchQuery() {
		return geoSearchQueryOpt.get();
	}

	/**
	 * @return Query du geoSearch.
	 */
	public boolean hasGeoSearch() {
		return geoSearchQueryOpt.isPresent();
	}
}
