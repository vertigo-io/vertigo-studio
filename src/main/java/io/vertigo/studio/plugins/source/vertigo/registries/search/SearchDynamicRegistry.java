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
package io.vertigo.studio.plugins.source.vertigo.registries.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Tuple;
import io.vertigo.core.locale.MessageText;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.search.FacetSketch;
import io.vertigo.studio.notebook.search.FacetSketch.FacetOrder;
import io.vertigo.studio.notebook.search.FacetSketchValue;
import io.vertigo.studio.notebook.search.FacetedQuerySketch;
import io.vertigo.studio.notebook.search.SearchIndexSketch;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketch;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;

/**
 * @author pchretien, mlaroche
 */
public final class SearchDynamicRegistry implements DynamicRegistry {

	@Override
	public DslGrammar getGrammar() {
		return new SearchGrammar();
	}

	/** {@inheritDoc} */
	@Override
	public SketchSupplier supplyModel(final DslSketch dslDefinition) {
		return notebook -> createModel(notebook, dslDefinition);
	}

	private static Sketch createModel(final Notebook notebook, final DslSketch dslDefinition) {
		final DslEntity dslEntity = dslDefinition.getEntity();

		if (SearchGrammar.INDEX_DEFINITION_ENTITY.equals(dslEntity)) {
			return createIndexSketch(notebook, dslDefinition);
		} else if (SearchGrammar.FACET_DEFINITION_ENTITY.equals(dslEntity)) {
			return createFacetSketch(notebook, dslDefinition);
		} else if (SearchGrammar.FACETED_QUERY_DEFINITION_ENTITY.equals(dslEntity)) {
			return createFacetedQuerySketch(notebook, dslDefinition);
		}
		throw new IllegalStateException("The type of definition" + dslDefinition + " is not managed by me");
	}

	private static SearchIndexSketch createIndexSketch(final Notebook notebook, final DslSketch xsearchObjet) {
		final DtSketch keyConceptDtDefinition = notebook.resolve(xsearchObjet.getSketchKeyByFieldName("keyConcept").getName(), DtSketch.class);
		final DtSketch indexDtDefinition = notebook.resolve(xsearchObjet.getSketchKeyByFieldName("dtIndex").getName(), DtSketch.class);
		final SketchKey definitionkey = xsearchObjet.getKey();

		//Déclaration des copyField
		final Map<DtSketchField, List<DtSketchField>> copyFields = populateCopyFields(xsearchObjet, indexDtDefinition);

		final String searchLoaderId = (String) xsearchObjet.getPropertyValue(SearchGrammar.SEARCH_LOADER_PROPERTY);
		return new SearchIndexSketch(definitionkey.getName(), keyConceptDtDefinition, indexDtDefinition, copyFields, searchLoaderId);
	}

	private static Map<DtSketchField, List<DtSketchField>> populateCopyFields(final DslSketch xsearchObjet, final DtSketch indexDtDefinition) {
		final Map<DtSketchField, List<DtSketchField>> copyToFields = new HashMap<>(); //(map toField : [fromField, fromField, ...])
		final List<DslSketch> copyToFieldNames = xsearchObjet.getChildSketches(SearchGrammar.INDEX_COPY_TO_PROPERTY);
		for (final DslSketch copyToFieldDefinition : copyToFieldNames) {
			final String copyFromFieldNames = (String) copyToFieldDefinition.getPropertyValue(SearchGrammar.INDEX_COPY_FROM_PROPERTY);
			copyToFields.put(
					indexDtDefinition.getField(copyToFieldDefinition.getKey().getName()),
					Stream.of(copyFromFieldNames.split(",")).map(fieldName -> indexDtDefinition.getField(fieldName)).collect(Collectors.toList()));
		}
		return copyToFields;
	}

	private static FacetSketch createFacetSketch(final Notebook notebook, final DslSketch dslSketch) {
		final DtSketch indexDtSketch = notebook.resolve(dslSketch.getSketchKeyByFieldName("dtDefinition").getName(), DtSketch.class);
		final String dtFieldName = (String) dslSketch.getPropertyValue(SearchGrammar.FIELD_NAME);
		final DtSketchField dtField = indexDtSketch.getField(dtFieldName);
		final String label = (String) dslSketch.getPropertyValue(KspProperty.LABEL);

		//Déclaration des ranges
		final List<DslSketch> rangeDefinitions = dslSketch.getChildSketches("range");
		final List<DslSketch> paramsDefinitions = dslSketch.getChildSketches("params");
		final MessageText labelMsg = MessageText.of(label);
		final FacetSketch facetDefinition;
		if (!rangeDefinitions.isEmpty()) {
			final List<FacetSketchValue> facetValues = rangeDefinitions
					.stream()
					.map(SearchDynamicRegistry::createFacetValue)
					.collect(Collectors.toList());
			facetDefinition = FacetSketch.createFacetSketchByRange(
					dslSketch.getKey().getName(),
					indexDtSketch,
					dtField,
					labelMsg,
					facetValues,
					isMultiSelectable(dslSketch, false),
					getFacetOrder(dslSketch, FacetOrder.definition));
		} else if (!paramsDefinitions.isEmpty()) {
			final Map<String, String> facetParams = paramsDefinitions.stream()
					.map(SearchDynamicRegistry::createFacetParam)
					.collect(Collectors.toMap(Tuple::getVal1, Tuple::getVal2));
			facetDefinition = FacetSketch.createCustomFacetSketch(
					dslSketch.getKey().getName(),
					indexDtSketch,
					dtField,
					labelMsg,
					facetParams,
					isMultiSelectable(dslSketch, false),
					getFacetOrder(dslSketch, FacetOrder.definition));
		} else {
			facetDefinition = FacetSketch.createFacetSketchByTerm(
					dslSketch.getKey().getName(),
					indexDtSketch,
					dtField,
					labelMsg,
					isMultiSelectable(dslSketch, false),
					getFacetOrder(dslSketch, FacetOrder.count));
		}
		return facetDefinition;
	}

	private static FacetOrder getFacetOrder(final DslSketch dslSketch, final FacetOrder defaultOrder) {
		final String orderStr = (String) dslSketch.getPropertyValue(SearchGrammar.FACET_ORDER);
		Assertion.check().isTrue(orderStr == null
				|| FacetOrder.alpha.name().equals(orderStr)
				|| FacetOrder.count.name().equals(orderStr)
				|| FacetOrder.definition.name().equals(orderStr), "Facet order must be one of {0}", Arrays.toString(FacetOrder.values()));
		return orderStr != null ? FacetOrder.valueOf(orderStr) : defaultOrder;
	}

	private static boolean isMultiSelectable(final DslSketch dslSketch, final boolean defaultValue) {
		final Boolean multiSelectable = (Boolean) dslSketch.getPropertyValue(SearchGrammar.FACET_MULTISELECTABLE);
		return multiSelectable != null ? multiSelectable : defaultValue;
	}

	private static FacetSketchValue createFacetValue(final DslSketch rangeSketch) {
		final String listFilterString = (String) rangeSketch.getPropertyValue(SearchGrammar.RANGE_FILTER_PROPERTY);
		final String label = (String) rangeSketch.getPropertyValue(KspProperty.LABEL);
		final String code = rangeSketch.getKey().getName();
		return new FacetSketchValue(code, listFilterString, label);
	}

	private static Tuple<String, String> createFacetParam(final DslSketch paramSketch) {
		final String name = paramSketch.getKey().getName();
		final String value = (String) paramSketch.getPropertyValue(SearchGrammar.PARAMS_VALUE_PROPERTY);
		return Tuple.of(name, value);
	}

	private static FacetedQuerySketch createFacetedQuerySketch(final Notebook notebook, final DslSketch dslSketch) {
		final DtSketch keyConceptDtDefinition = notebook.resolve(dslSketch.getSketchKeyByFieldName("keyConcept").getName(), DtSketch.class);
		final List<SketchKey> facetSketchKeys = dslSketch.getSketchKeysByFieldName("facets");
		final List<FacetSketch> facetSketches = facetSketchKeys
				.stream()
				.map(key -> notebook.resolve(key.getName(), FacetSketch.class))
				.collect(Collectors.toList());
		final String listFilterBuilderQuery = (String) dslSketch.getPropertyValue(SearchGrammar.LIST_FILTER_BUILDER_QUERY);
		final String geoSearchQuery = (String) dslSketch.getPropertyValue(SearchGrammar.GEO_SEARCH_QUERY);
		final String listFilterBuilderClassName = getListFilterBuilderClassName(dslSketch);
		final SketchKey criteriaDomainKey = dslSketch.getSketchKeyByFieldName("domainCriteria");
		final DomainSketch criteriaDomain = notebook.resolve(criteriaDomainKey.getName(), DomainSketch.class);

		return new FacetedQuerySketch(
				dslSketch.getKey().getName(),
				keyConceptDtDefinition,
				facetSketches,
				criteriaDomain,
				listFilterBuilderClassName,
				listFilterBuilderQuery,
				Optional.ofNullable(geoSearchQuery));
	}

	private static String getListFilterBuilderClassName(final DslSketch taskDslSketch) {
		return (String) taskDslSketch.getPropertyValue(SearchGrammar.LIST_FILTER_BUILDER_CLASS);
	}

}
