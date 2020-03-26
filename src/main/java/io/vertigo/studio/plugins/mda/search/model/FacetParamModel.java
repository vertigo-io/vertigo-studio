package io.vertigo.studio.plugins.mda.search.model;

import io.vertigo.core.lang.Assertion;

public class FacetParamModel {

	private final String paramName;
	private final String paramValue;

	public FacetParamModel(final String paramName, final String paramValue) {
		Assertion.checkArgNotEmpty(paramName);
		Assertion.checkArgNotEmpty(paramValue);
		//---
		this.paramName = paramName;
		this.paramValue = paramValue;
	}

	public String getName() {
		return paramName;
	}

	public String getValue() {
		return paramValue;
	}
}
