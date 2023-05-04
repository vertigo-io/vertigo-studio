/**
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.search.FacetSketch;

public final class FacetModel {

	private final FacetSketch facetSketch;
	private final List<FacetValueModel> facetValueModels;
	private final List<FacetParamModel> facetParamModels;

	public FacetModel(final FacetSketch facetSketch) {
		Assertion.check().isNotNull(facetSketch);
		//---
		this.facetSketch = facetSketch;
		if (facetSketch.isRangeFacet()) {
			facetValueModels = facetSketch.getFacetRanges()
					.stream()
					.map(FacetValueModel::new)
					.collect(Collectors.toList());
			facetParamModels = Collections.emptyList();
		} else if (facetSketch.isCustomFacet()) {
			facetValueModels = Collections.emptyList();
			facetParamModels = facetSketch.getFacetParams().entrySet()
					.stream()
					.map(entry -> new FacetParamModel(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
		} else {
			facetValueModels = Collections.emptyList();
			facetParamModels = Collections.emptyList();
		}
	}

	public boolean isTerm() {
		return !facetSketch.isRangeFacet() && !facetSketch.isCustomFacet();
	}

	public boolean isCustom() {
		return facetSketch.isCustomFacet();
	}

	public boolean isRange() {
		return facetSketch.isRangeFacet();
	}

	public String getName() {
		return facetSketch.getKey().getName();
	}

	public String getFieldName() {
		return facetSketch.getDtField().getName();
	}

	public String getLabel() {
		return facetSketch.getLabel().getDisplay();
	}

	public Boolean isMultiSelectable() {
		return facetSketch.isMultiSelectable();
	}

	public String getOrder() {
		return facetSketch.getOrder().name();
	}

	public List<FacetValueModel> getFacetValues() {
		return facetValueModels;
	}

	public List<FacetParamModel> getFacetParams() {
		return facetParamModels;
	}

}
