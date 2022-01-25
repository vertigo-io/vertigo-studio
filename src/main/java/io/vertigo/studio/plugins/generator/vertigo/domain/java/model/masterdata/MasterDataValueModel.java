/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.java.model.masterdata;

import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;

/**
 * Model Sql des materdata.
 *
 * @author npiedeloup
 */
public final class MasterDataValueModel {

	private final String idValue;
	private final String enumNameValue;
	private final Map<String, String> allFieldValues;

	public MasterDataValueModel(final DtSketch dtSketch, final String enumNameValue, final Map<String, String> allFieldValues) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.enumNameValue = enumNameValue;
		this.allFieldValues = allFieldValues;
		final String idFieldName = dtSketch.getIdField().get().getName();
		//--
		Assertion.check().isTrue(allFieldValues.containsKey(idFieldName), "The id value is not present for the masterdata '{0}' of type {1}", allFieldValues, dtSketch.getLocalName());
		//---
		idValue = allFieldValues.get(idFieldName);
	}

	public String getId() {
		return idValue;
	}

	public String getName() {
		return enumNameValue;
	}

	public String getFieldValue(final String fieldName) {
		return allFieldValues.get(fieldName);
	}

}
