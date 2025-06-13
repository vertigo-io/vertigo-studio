/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo.factories.search;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Tuple;
import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawKey;
import io.vertigo.studio.impl.source.dsl.raw.DslSketchFactory;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.search.FacetSketch;
import io.vertigo.studio.notebook.search.FacetSketch.FacetOrder;
import io.vertigo.studio.notebook.search.FacetSketchValue;
import io.vertigo.studio.notebook.search.FacetedQuerySketch;
import io.vertigo.studio.notebook.search.SearchIndexSketch;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;

/**
 * @author pchretien, mlaroche
 */
public final class SearchSketchFactory implements DslSketchFactory {

	@Override
	public DslGrammar getGrammar() {
		return new SearchGrammar();
	}

	/** {@inheritDoc} */
	@Override
	public List<Sketch> createSketches(final Notebook notebook, final DslRaw raw) {
		final DslEntity entity = raw.getEntity();
		final Sketch sketch;
		if (SearchGrammar.INDEX_ENTITY.equals(entity)) {
			sketch = createIndexSketch(notebook, raw);
		} else if (SearchGrammar.FACET_ENTITY.equals(entity)) {
			sketch = createFacetSketch(notebook, raw);
		} else if (SearchGrammar.FACETED_QUERY_ENTITY.equals(entity)) {
			sketch = createFacetedQuerySketch(notebook, raw);
		} else {
			throw new IllegalStateException("his kind of raw " + entity + " is not managed by me");
		}
		return List.of(sketch);
	}

	private static SearchIndexSketch createIndexSketch(final Notebook notebook, final DslRaw xsearchObjet) {
		final DtSketch keyConceptDtDefinition = notebook.resolve(xsearchObjet.getRawKeyByFieldName("keyConcept").getName(), DtSketch.class);
		final DtSketch indexDtDefinition = notebook.resolve(xsearchObjet.getRawKeyByFieldName("dtIndex").getName(), DtSketch.class);
		final DslRawKey definitionkey = xsearchObjet.getKey();

		//Déclaration des copyField
		final Map<DtSketchField, List<DtSketchField>> copyFields = populateCopyFields(xsearchObjet, indexDtDefinition);

		final String searchLoaderId = (String) xsearchObjet.getPropertyValue(SearchGrammar.SEARCH_LOADER_PROPERTY);
		return new SearchIndexSketch(definitionkey.getName(), keyConceptDtDefinition, indexDtDefinition, copyFields, searchLoaderId);
	}

	private static Map<DtSketchField, List<DtSketchField>> populateCopyFields(final DslRaw xsearchObjet, final DtSketch indexDtDefinition) {
		final Map<DtSketchField, List<DtSketchField>> copyToFields = new LinkedHashMap<>(); //(map toField : [fromField, fromField, ...])
		final List<DslRaw> copyToFieldNames = xsearchObjet.getSubRaws(SearchGrammar.INDEX_COPY_TO_PROPERTY);
		for (final DslRaw copyToFieldDefinition : copyToFieldNames) {
			final String copyFromFieldNames = (String) copyToFieldDefinition.getPropertyValue(SearchGrammar.INDEX_COPY_FROM_PROPERTY);
			copyToFields.put(
					indexDtDefinition.getField(copyToFieldDefinition.getKey().getName()),
					Stream.of(copyFromFieldNames.split(",")).map(indexDtDefinition::getField).collect(Collectors.toList()));
		}
		return copyToFields;
	}

	private static FacetSketch createFacetSketch(final Notebook notebook, final DslRaw dslSketch) {
		final DtSketch indexDtSketch = notebook.resolve(dslSketch.getRawKeyByFieldName("dtDefinition").getName(), DtSketch.class);
		final String dtFieldName = (String) dslSketch.getPropertyValue(SearchGrammar.FIELD_NAME);
		final DtSketchField dtField = indexDtSketch.getField(dtFieldName);
		final String label = (String) dslSketch.getPropertyValue(KspProperty.LABEL);

		//Déclaration des ranges
		final List<DslRaw> rangeDefinitions = dslSketch.getSubRaws("range");
		final List<DslRaw> paramsDefinitions = dslSketch.getSubRaws("params");
		final LocaleMessageText labelMsg = LocaleMessageText.of(label);
		final FacetSketch facetDefinition;
		if (!rangeDefinitions.isEmpty()) {
			final List<FacetSketchValue> facetValues = rangeDefinitions
					.stream()
					.map(SearchSketchFactory::createFacetValue)
					.toList();
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
					.map(SearchSketchFactory::createFacetParam)
					.collect(Collectors.toMap(Tuple::val1, Tuple::val2));
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

	private static FacetOrder getFacetOrder(final DslRaw dslSketch, final FacetOrder defaultOrder) {
		final String orderStr = (String) dslSketch.getPropertyValue(SearchGrammar.FACET_ORDER);
		Assertion.check().isTrue(orderStr == null
				|| FacetOrder.alpha.name().equals(orderStr)
				|| FacetOrder.count.name().equals(orderStr)
				|| FacetOrder.definition.name().equals(orderStr), "Facet order must be one of {0}", Arrays.toString(FacetOrder.values()));
		return orderStr != null ? FacetOrder.valueOf(orderStr) : defaultOrder;
	}

	private static boolean isMultiSelectable(final DslRaw dslSketch, final boolean defaultValue) {
		final Boolean multiSelectable = (Boolean) dslSketch.getPropertyValue(SearchGrammar.FACET_MULTISELECTABLE);
		return multiSelectable != null ? multiSelectable : defaultValue;
	}

	private static FacetSketchValue createFacetValue(final DslRaw rangeSketch) {
		final String listFilterString = (String) rangeSketch.getPropertyValue(SearchGrammar.RANGE_FILTER_PROPERTY);
		final String label = (String) rangeSketch.getPropertyValue(KspProperty.LABEL);
		final String code = rangeSketch.getKey().getName();
		return new FacetSketchValue(code, listFilterString, label);
	}

	private static Tuple<String, String> createFacetParam(final DslRaw paramSketch) {
		final String name = paramSketch.getKey().getName();
		final String value = (String) paramSketch.getPropertyValue(SearchGrammar.PARAMS_VALUE_PROPERTY);
		return Tuple.of(name, value);
	}

	private static FacetedQuerySketch createFacetedQuerySketch(final Notebook notebook, final DslRaw dslSketch) {
		final DtSketch keyConceptDtSketch = notebook.resolve(dslSketch.getRawKeyByFieldName("dtIndex").getName(), DtSketch.class);
		final List<DslRawKey> facetSketchRawKeys = dslSketch.getRawKeysByFieldName("facets");
		final List<FacetSketch> facetSketches = facetSketchRawKeys
				.stream()
				.map(key -> notebook.resolve(key.getName(), FacetSketch.class))
				.toList();
		final String listFilterBuilderQuery = (String) dslSketch.getPropertyValue(SearchGrammar.LIST_FILTER_BUILDER_QUERY);
		final String geoSearchQuery = (String) dslSketch.getPropertyValue(SearchGrammar.GEO_SEARCH_QUERY);
		final String listFilterBuilderClassName = getListFilterBuilderClassName(dslSketch);
		final DslRawKey criteriaDomainRawKey = dslSketch.getRawKeyByFieldName("domainCriteria");
		final DomainSketch criteriaDomain = notebook.resolve(criteriaDomainRawKey.getName(), DomainSketch.class);

		return new FacetedQuerySketch(
				dslSketch.getKey().getName(),
				keyConceptDtSketch,
				facetSketches,
				criteriaDomain,
				listFilterBuilderClassName,
				listFilterBuilderQuery,
				Optional.ofNullable(geoSearchQuery));
	}

	private static String getListFilterBuilderClassName(final DslRaw taskDslSketch) {
		return (String) taskDslSketch.getPropertyValue(SearchGrammar.LIST_FILTER_BUILDER_CLASS);
	}

}
