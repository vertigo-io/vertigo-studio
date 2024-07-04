/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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

import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.authorization.SecuredFeatureSketch;

public class SecuredFeatureModel {

	private final SecuredFeatureSketch securedFeatureSketch;

	public SecuredFeatureModel(final SecuredFeatureSketch securedFeatureSketch) {
		Assertion.check().isNotNull(securedFeatureSketch);
		//---
		this.securedFeatureSketch = securedFeatureSketch;
	}

	public Optional<String> getComment() {
		return securedFeatureSketch.getComment();
	}

	public String getOperationName() {
		return securedFeatureSketch.getCode();
	}

}
