/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo.loaders.xml;

import io.vertigo.core.lang.Assertion;

/**
 * Identifiant d'un objet powerAMC ou XMI.
 *
 * @author pchretien, mlaroche, pforhan
 */
public final class XmlId {
	private final String keyValue;

	/**
	 * Constructor.
	 * @param keyValue Valeur de l'identiant
	 */
	public XmlId(final String keyValue) {
		Assertion.check().isNotNull(keyValue);
		//-----
		this.keyValue = keyValue;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return keyValue.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof XmlId) {
			return ((XmlId) o).keyValue.equals(this.keyValue);
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "id(" + keyValue + ')';
	}
}
