package io.vertigo.studio.plugins.mda.vertigo.search.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.search.FacetSketchValue;

public class FacetValueModel {

	private final FacetSketchValue facetValue;

	public FacetValueModel(final FacetSketchValue facetValue) {
		Assertion.check().isNotNull(facetValue);
		//---
		this.facetValue = facetValue;
	}

	public String getCode() {
		return facetValue.getCode();
	}

	public String getLabel() {
		return facetValue.getLabel();
	}

	public String getListFilter() {
		return facetValue.getListFilter();
	}

}
