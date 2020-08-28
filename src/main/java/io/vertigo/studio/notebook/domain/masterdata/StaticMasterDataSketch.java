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
package io.vertigo.studio.notebook.domain.masterdata;

import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;
import io.vertigo.studio.plugins.generator.vertigo.util.DomainUtil;

/**
 * A raw masterdata values is just a map of key/value pairs :
 * 	-key : the name of the masterdata name
 *  -value: a map of all the values. each value is identified by a name
 *
 * @author mlaroche
 *
 */
@SkecthPrefix(StaticMasterDataSketch.PREFIX)
public final class StaticMasterDataSketch extends AbstractSketch {
	public static final String PREFIX = "Md";

	private final String entityClassName;
	private final Map<String, MasterDataValue> values;

	public StaticMasterDataSketch(final String entityClassName, final Map<String, MasterDataValue> values) {
		super(PREFIX + DomainUtil.getSimpleNameFromCanonicalName(entityClassName));
		//---
		Assertion.check()
				.isNotBlank(entityClassName)
				.isNotNull(values);
		//---
		this.entityClassName = entityClassName;
		this.values = values;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public Map<String, MasterDataValue> getValues() {
		return values;
	}
}
