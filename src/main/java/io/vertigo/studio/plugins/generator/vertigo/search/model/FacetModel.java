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