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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw;

import java.util.List;

import io.vertigo.core.lang.Assertion;

/**
 * Model of Definition from KSP.
 * @author npiedeloup
 */
public final class DslRawBody {
	private final List<DslRawEntry> rawEntries;
	private final List<DslPropertyEntry> propertyEntries;

	/**
	 * @param rawEntries
	 * @param propertyEntries
	 */
	public DslRawBody(final List<DslRawEntry> rawEntries, final List<DslPropertyEntry> propertyEntries) {
		Assertion.check()
				.isNotNull(rawEntries)
				.isNotNull(propertyEntries);
		//-----
		this.rawEntries = rawEntries;
		this.propertyEntries = propertyEntries;
	}

	/**
	 * @return List of properties
	 */
	public List<DslPropertyEntry> getPropertyEntries() {
		return propertyEntries;
	}

	/**
	 * @return List of Definition
	 */
	public List<DslRawEntry> getRawEntries() {
		return rawEntries;
	}
}
