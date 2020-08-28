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
package io.vertigo.studio.plugins.generator.vertigo.domain.ts.model;

import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;

/**
 * Model TS des materdata.
 *
 * @author npiedeloup
 */
public final class TSMasterDataValueModel {

	private final DtSketch dtSketch;
	private final Map<String, String> allFieldValues;

	public TSMasterDataValueModel(final DtSketch dtSketch, final Map<String, String> allFieldValues) {
		Assertion.check()
				.isNotNull(dtSketch)
				.isNotNull(allFieldValues);
		//-----
		this.dtSketch = dtSketch;
		this.allFieldValues = allFieldValues;
	}

	public String getFieldValue(final String fieldName) {
		final DtSketchField dtField = dtSketch.getField(fieldName);
		//---
		Assertion.check()
				.when(dtField.getCardinality().hasOne(), () -> Assertion.check()
						.isTrue(allFieldValues.containsKey(fieldName),
								"Field '{0}' is required on '{1}' and no value was provided. Provided values '{2}'",
								fieldName, dtSketch, allFieldValues));
		//---
		return allFieldValues.getOrDefault(fieldName, "null");
	}

}
