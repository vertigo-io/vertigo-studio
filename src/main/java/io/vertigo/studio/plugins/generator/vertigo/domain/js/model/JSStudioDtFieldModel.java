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
package io.vertigo.studio.plugins.generator.vertigo.domain.js.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketchField;

/**
 * Model used to define a DtField.
 *
 * @author pchretien, mlaroche
 */
public final class JSStudioDtFieldModel {
	private final DtSketchField dtField;

	/***
	 * Constructeur.
	 * @param dtField Champ à générer
	 */
	JSStudioDtFieldModel(final DtSketchField dtField) {
		Assertion.check().isNotNull(dtField);
		//-----
		this.dtField = dtField;
	}

	public String getCamelCaseName() {
		return dtField.getName();
	}

	/**
	 * @return Label du champ
	 */
	public String getLabel() {
		return dtField.getLabel().getDisplay();
	}

	public String getDomainName() {
		return dtField.getDomain().getKey().getName();
	}

	/**
	 * @return Si la propriété est requise
	 */
	public boolean isRequired() {
		return dtField.getCardinality().hasOne();
	}
}
