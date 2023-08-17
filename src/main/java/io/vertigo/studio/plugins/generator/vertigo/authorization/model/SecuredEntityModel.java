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
package io.vertigo.studio.plugins.generator.vertigo.authorization.model;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.authorization.SecuredFeatureSketch;
import io.vertigo.studio.notebook.domain.DtSketch;

public final class SecuredEntityModel {

	private final DtSketch dtSketch;
	private final List<SecuredFeatureModel> securedFeatureModels;

	public SecuredEntityModel(final List<SecuredFeatureSketch> securedFeatureSketchs, final DtSketch dtSketch) {
		Assertion.check().isNotNull(dtSketch);
		//---
		this.dtSketch = dtSketch;
		securedFeatureModels = securedFeatureSketchs
				.stream()
				.map(SecuredFeatureModel::new)
				.collect(Collectors.toList());
	}

	public String getClassSimpleName() {
		return dtSketch.getClassSimpleName();
	}

	public String getClassCanonicalName() {
		return dtSketch.getClassCanonicalName();
	}

	public List<SecuredFeatureModel> getOperations() {
		return securedFeatureModels;
	}

}
