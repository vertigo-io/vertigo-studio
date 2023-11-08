/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.search.model;

import java.util.List;
import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.search.FacetedQuerySketch;
import io.vertigo.studio.plugins.generator.vertigo.VertigoConstants.VertigoDefinitionPrefix;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;

/**
 * Génération des classes/méthodes des taches de type DAO.
 *
 * @author pchretien, mlaroche
 */
public final class FacetedQueryModel {
	private final FacetedQuerySketch facetedQuerySketch;

	private final String simpleName;
	private final String criteriaClassCanonicalName;
	private final List<FacetModel> facetDefinitionModels;

	public FacetedQueryModel(final FacetedQuerySketch facetedQuerySketch, final Function<String, String> classNameFromDt) {
		Assertion.check().isNotNull(facetedQuerySketch);
		//-----
		this.facetedQuerySketch = facetedQuerySketch;
		simpleName = facetedQuerySketch.getLocalName();
		criteriaClassCanonicalName = DomainUtil.buildJavaTypeName(facetedQuerySketch.getCriteriaDomain(), classNameFromDt);
		facetDefinitionModels = facetedQuerySketch.getFacetSketchs()
				.stream()
				.map(FacetModel::new)
				.toList();
	}

	/**
	 * @return Nom local CamelCase de la facetedQueryDefinition
	 */
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * @return Urn de la facetedQueryDefinition
	 */
	public String getUrn() {
		return facetedQuerySketch.getKey().getName();
	}

	public String getQueryName() {
		return VertigoDefinitionPrefix.FacetedQueryDefiniton.getPrefix() + simpleName;
	}

	public String getCriteriaSmartType() {
		return facetedQuerySketch.getCriteriaDomain().getSmartTypeName();
	}

	public String getListFilterBuilderQuery() {
		return facetedQuerySketch.getListFilterBuilderQuery();
	}

	public boolean hasGeoSearch() {
		return facetedQuerySketch.hasGeoSearch();
	}

	public String getGeoSearchQuery() {
		return facetedQuerySketch.getGeoSearchQuery();
	}

	public String getKeyConceptDtDefinition() {
		return VertigoDefinitionPrefix.DtDefinition.getPrefix() + facetedQuerySketch.getIndexDtSketch().getLocalName();
	}

	public String getListFilterClassName() {
		return facetedQuerySketch.getListFilterBuilderClassName();
	}

	/**
	 * @return Nom de la classe du criteria
	 */
	public String getCriteriaClassCanonicalName() {
		return criteriaClassCanonicalName;
	}

	public List<FacetModel> getFacetDefinitions() {
		return facetDefinitionModels;
	}

}
