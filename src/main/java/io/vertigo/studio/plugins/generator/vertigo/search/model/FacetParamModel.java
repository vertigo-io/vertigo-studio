/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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

import io.vertigo.core.lang.Assertion;

public final class FacetParamModel {

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
