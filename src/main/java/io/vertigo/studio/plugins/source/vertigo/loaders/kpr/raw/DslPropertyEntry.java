/**
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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw;

import io.vertigo.core.lang.Assertion;

/**
 * This entry defines a property and its value.
 *
 * @author pchretien, mlaroche
 */
public final class DslPropertyEntry {
	private final String propertyValue;
	private final String propertyName;

	/**
	 * Constructor.
	 * @param propertyName Name of the property
	 * @param propertyValue Value of the property
	 */
	public DslPropertyEntry(final String propertyName, final String propertyValue) {
		Assertion.check().isNotNull(propertyName);
		//-----
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	/**
	 * @return Value of the property
	 */
	public String getPropertyValueAsString() {
		return propertyValue;
	}

	/**
	 * @return Name of the property
	 */
	public String getPropertyName() {
		return propertyName;
	}
}
