package io.vertigo.studio.plugins.mda.search.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.dynamo.search.StudioFacetValue;

public class FacetValueModel {

	private final StudioFacetValue facetValue;

	public FacetValueModel(final StudioFacetValue facetValue) {
		Assertion.checkNotNull(facetValue);
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
