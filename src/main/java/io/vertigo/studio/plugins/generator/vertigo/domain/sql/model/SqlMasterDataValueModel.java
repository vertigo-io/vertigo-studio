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
package io.vertigo.studio.plugins.generator.vertigo.domain.sql.model;

import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;

/**
 * Model Sql des materdata.
 *
 * @author mlaroche
 */
public final class SqlMasterDataValueModel {

	private final DtSketch dtSketch;
	private final Map<String, String> allFieldValues;

	public SqlMasterDataValueModel(final DtSketch dtSketch, final Map<String, String> allFieldValues) {
		Assertion.check()
				.isNotNull(dtSketch)
				.isNotNull(allFieldValues);
		//-----
		this.dtSketch = dtSketch;
		this.allFieldValues = allFieldValues;
	}

	public String getFieldValue(final SqlStudioDtFieldModel field) {
		final String fieldName = field.getName();
		final DtSketchField dtField = dtSketch.getField(fieldName);
		//---
		Assertion.check()
				.when(dtField.getCardinality().hasOne(), () -> Assertion.check()
						.isTrue(allFieldValues.containsKey(fieldName),
								"Field '{0}' is required on '{1}' and no value was provided. Provided values '{2}'",
								fieldName, dtSketch, allFieldValues));
		//---
		final String value = allFieldValues.get(fieldName);
		return value != null ? ("'" + value + "'") : null;
	}

}
