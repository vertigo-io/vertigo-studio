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
package io.vertigo.studio.plugins.mda.domain.sql.model;

import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.dynamo.domain.metamodel.StudioDtDefinition;
import io.vertigo.dynamo.domain.metamodel.StudioDtField;

/**
 * Model Sql des materdata.
 *
 * @author mlaroche
 */
public final class SqlMasterDataValueModel {

	private final StudioDtDefinition dtDefinition;
	private final Map<String, String> allFieldValues;

	public SqlMasterDataValueModel(final StudioDtDefinition dtDefinition, final Map<String, String> allFieldValues) {
		Assertion.checkNotNull(dtDefinition);
		Assertion.checkNotNull(allFieldValues);
		//-----
		this.dtDefinition = dtDefinition;
		this.allFieldValues = allFieldValues;
	}

	public String getFieldValue(final SqlStudioDtFieldModel field) {
		final String fieldName = field.getName();
		final StudioDtField dtField = dtDefinition.getField(fieldName);
		//---
		Assertion.when(dtField.getCardinality().hasOne())
				.check(() -> allFieldValues.containsKey(fieldName),
						"Field '{0}' is required on '{1}' and no value was provided. Provided values '{2}'",
						fieldName, dtDefinition.getName(), allFieldValues);
		//---
		return allFieldValues.getOrDefault(fieldName, "null");
	}

}
