/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
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
package io.vertigo.studio.plugins.mda.vertigo.authorization.model;

import java.util.Optional;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.metamodel.authorization.SecuredFeature;

public class GlobalAuthorizationModel {

	private final SecuredFeature securedFeature;

	public GlobalAuthorizationModel(final SecuredFeature securedFeature) {
		Assertion.check().notNull(securedFeature);
		//---
		this.securedFeature = securedFeature;
	}

	public Optional<String> getComment() {
		return securedFeature.getComment();
	}

	public String getOperationName() {
		return securedFeature.getCode();
	}

}
