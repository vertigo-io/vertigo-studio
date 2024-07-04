/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.ksp.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;
import io.vertigo.studio.notebook.domain.DtSketchField.FieldType;

/**
 * Model used to define a DtField.
 *
 * @author pchretien, mlaroche
 */
public final class KspDtFieldModel {
	private final DtSketch dtSketch;
	private final DtSketchField dtField;

	/***
	 * Constructeur.
	 * @param dtField Champ à générer
	 */
	KspDtFieldModel(
			final DtSketch dtSketch,
			final DtSketchField dtField) {
		Assertion.check()
				.isNotNull(dtSketch)
				.isNotNull(dtField);
		//-----
		this.dtSketch = dtSketch;
		this.dtField = dtField;
		//---
	}

	/**
	 * Nom du champ en camelCase.
	 * En freemarker pour upperCamelCase : myString?cap_first
	 * @return unNom
	 */
	public String getName() {
		return dtField.getName();
	}

	/**
	 * @return DtField
	 */
	public DtSketchField getSource() {
		return dtField;
	}

	public String getResourceKey() {
		return dtField.getResourceKey();
	}

	public String getLabel() {
		return dtField.getLabel().getDisplay();
	}

	public String getDomainName() {
		return dtField.getDomain().getKey().getName();
	}

	public boolean hasSpecificCardinality() {
		return dtField.getCardinality() != Cardinality.OPTIONAL_OR_NULLABLE;
	}

	public String getCardinalitySymbol() {
		return dtField.getCardinality().toSymbol();
	}

	/**
	 * @return Type du champ (
	 */
	public String getType() {
		return dtField.getType().name();
	}

	/**
	 * @return Label du champ
	 */
	public String getDisplay() {
		return dtField.getLabel().getDisplay();
	}

	/**
	 * @return Label du champ
	 */
	public boolean isSortField() {
		return dtSketch.getSortField().isPresent() && dtSketch.getSortField().get().getName().equals(dtField.getName());
	}

	/**
	 * @return Label du champ
	 */
	public boolean isDisplayField() {
		return dtSketch.getDisplayField().isPresent() && dtSketch.getDisplayField().get().getName().equals(dtField.getName());
	}

	/**
	 * @return Si la propriété est requise
	 */
	public boolean isRequired() {
		return dtField.getCardinality().hasOne();
	}

	/**
	 * @return Si la propriété est multiple
	 */
	public boolean isMultiple() {
		return dtField.getCardinality().hasMany();
	}

	/**
	 * @return Si la propriété vise un autre type de dt
	 */
	public boolean isDataObject() {
		return dtField.getDomain().getScope().isDataObject();
	}

	/**
	 * @return Code java correspondant à l'expression de ce champ calculé
	 */
	public String getJavaCode() {
		return dtField.getComputedExpression().getJavaCode();
	}

	private boolean isChildOfEntity() {
		return dtSketch.getFragment().isEmpty();
	}

	public boolean isForeignKey() {
		return isChildOfEntity() && dtField.getType() == FieldType.FOREIGN_KEY;
	}

}
