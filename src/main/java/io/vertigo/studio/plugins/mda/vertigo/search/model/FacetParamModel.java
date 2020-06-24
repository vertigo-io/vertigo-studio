package io.vertigo.studio.plugins.mda.vertigo.search.model;

import io.vertigo.core.lang.Assertion;

public class FacetParamModel {

	private final String paramName;
	private final String paramValue;

	public FacetParamModel(final String paramName, final String paramValue) {
		Assertion.check()
				.isNotBlank(paramName)
				.isNotBlank(paramValue);
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
