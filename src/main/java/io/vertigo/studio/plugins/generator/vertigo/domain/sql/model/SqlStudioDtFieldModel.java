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
package io.vertigo.studio.plugins.generator.vertigo.domain.sql.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.domain.DtSketchField;

/**
 * Model used to define a DtField.
 *
 * @author pchretien, mlaroche
 */
public final class SqlStudioDtFieldModel {
	private final DtSketchField dtField;

	/***
	 * Constructeur.
	 * @param dtField Champ à générer
	 */
	SqlStudioDtFieldModel(final DtSketchField dtField) {
		Assertion.check().isNotNull(dtField);
		//-----
		this.dtField = dtField;
	}

	/**
	 * Nom du champ en camelCase
	 * @return unNom
	 */
	public String getName() {
		return dtField.getName();
	}

	/**
	 * Nom du champ en majuscules séparés par des _.
	 * @return UN_NOM
	 */
	public String getConstName() {
		return StringUtil.camelToConstCase(dtField.getName());
	}

	public boolean isId() {
		return dtField.getType().isId();
	}

	/**
	 * @return DtField
	 */
	public DtSketchField getSource() {
		return dtField;
	}

	/**
	 * @return Type java du champ
	 */
	public boolean isTypeString() {
		return dtField.getDomain().getScope().isPrimitive() && dtField.getDomain().getDataType() == BasicType.String;
	}

	/**
	 * @return Label du champ
	 */
	public String getDisplay() {
		return dtField.getLabel().getDisplay();
	}

	/**
	 * @return Si la propriété est requise
	 */
	public boolean isRequired() {
		return dtField.getCardinality().hasOne();
	}

	public boolean isPersistent() {
		return dtField.isPersistent();
	}
}
