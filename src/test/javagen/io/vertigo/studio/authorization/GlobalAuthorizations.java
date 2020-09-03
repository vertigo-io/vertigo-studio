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
package io.vertigo.studio.authorization;

import io.vertigo.account.authorization.definitions.Authorization;
import io.vertigo.account.authorization.definitions.AuthorizationName;
import io.vertigo.core.node.Node;

/**
 * Warning. This class is generated automatically !
 *
 * Enum of the authorizations known by the application.
 */
public enum GlobalAuthorizations implements AuthorizationName {

	/**
	 * Admapp.
	 */
	AtzAdmapp,
	/**
	 * Admpro.
	 */
	AtzAdmpro,
	/**
	 * Admusr.
	 */
	AtzAdmusr;

	/**
	 * Get the associated authorization.
	 *
	 * @param code authorization code
	 * @return authorization
	 */
	public static Authorization of(final String code) {
		return Node.getNode().getDefinitionSpace().resolve(code, Authorization.class);
	}

	/**
	 * Get the associated authorization.
	 *
	 * @return role
	 */
	public Authorization getAuthorization() {
		return of(name());
	}

}
