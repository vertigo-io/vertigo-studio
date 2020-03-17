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
package io.vertigo.studio.plugins.mda.domain.ts.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.dynamo.domain.metamodel.Domain;
import io.vertigo.dynamo.domain.metamodel.StudioDtField;

/**
 * Model used to define a DtField.
 *
 * @author pchretien
 */
public final class TSStudioDtFieldModel {
	private final StudioDtField dtField;

	/***
	 * Constructeur.
	 * @param dtField Champ Ã  gÃ©nÃ©rer
	 */
	TSStudioDtFieldModel(final StudioDtField dtField) {
		Assertion.checkNotNull(dtField);
		//-----
		this.dtField = dtField;
	}

	public String getCamelCaseName() {
		return dtField.getName();
	}

	/**
	 * @return Type javascript du champ with cardinality
	 */
	public String getTypescriptType() {
		return buildTypescriptType(dtField.getDomain(), dtField.getCardinality().hasMany());
	}

	/**
	 * @return Label du champ
	 */
	public String getLabel() {
		return dtField.getLabel().getDisplay();
	}

	/**
	 * @return Name of the domain
	 */
	public String getDomainName() {
		return dtField.getDomain().getName();
	}

	/**
	 * @return Local name of the domain
	 */
	public String getDomainDefinitionName() {
		return dtField.getDomain().getName();
	}

	/**
	 * @return Simple TS type
	 */
	public String getDomainTypeName() {
		return buildTypescriptType(dtField.getDomain(), false);
	}

	/**
	 * @return Si la propriÃ©tÃ© est requise
	 */
	public boolean isRequired() {
		return dtField.getCardinality().hasOne();
	}

	/**
	 * @return True si le type est une primitive.
	 */
	public boolean isPrimitive() {
		return dtField.getDomain().getScope().isPrimitive();
	}

	/**
	 * @return True si le type est un objet (au sens js).
	 */
	public boolean isObject() {
		return dtField.getDomain().getScope().isDataObject() || dtField.getDomain().getScope().isValueObject();
	}

	/**
	 * @return True si le type est une liste.
	 */
	public boolean isListOfObject() {
		return dtField.getCardinality().hasMany() && (dtField.getDomain().getScope().isDataObject() || dtField.getDomain().getScope().isValueObject());
	}

	/**
	 * Returns the javascript type.
	 * @param  domain DtDomain
	 * @return String
	 */
	private static String buildTypescriptType(final Domain domain, final boolean withArray) {
		final String typescriptType;
		if (domain.getScope().isPrimitive()) {
			final BasicType dataType = domain.getDataType();
			if (dataType.isNumber()) {
				typescriptType = "number";
			} else if (dataType == BasicType.Boolean) {
				typescriptType = "boolean";
			} else {
				typescriptType = "string";
			}
		} else {
			typescriptType = domain.getJavaClass().getSimpleName();
		}
		return typescriptType + (withArray ? "[]" : "");
	}
}
