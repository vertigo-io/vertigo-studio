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
package io.vertigo.studio.notebook.domain;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.locale.MessageText;
import io.vertigo.core.util.StringUtil;

/**
 * This class defines the structure of a field.
 *
 * A field represents a named and typed data
 *
 * A field
 *   - has a name
 *   - has a domain
 *   - has a fieldType
 *   - has a label
 *   - can be required
 *   - can be persistent
 *   - can be dynamic
 *
 * @author  fconstantin, pchretien , npiedeloup
 */
public final class DtSketchField {

	private static final int FIELD_NAME_MAX_LENGTH = 30;
	/** Field definition Prefix. */
	public static final String PREFIX = "fld";

	/**
	 * This enum lists all types that can be used by a field.
	 * The most common types are ID and DATA
	 */
	public enum FieldType {
		/**
		 * identity
		 */
		ID,

		/**
		 * a simple data field
		 */
		DATA,

		/**
		 * a link towards an other entity
		 */
		FOREIGN_KEY,

		/**
		 * a computed field
		 */
		COMPUTED;

		/**
		 * @return if the field is the 'id'
		 */
		public boolean isId() {
			return this == ID;
		}
	}

	private final String name;
	private final FieldType type;
	private final Cardinality cardinality;
	private final DomainSketch domainSketch;
	private final MessageText label;
	private final boolean persistent;

	/** Cas des FK ; référence à une FK. */
	private final String fkDtDefinitionName;

	/** ComputedExpression des champs Computed. */
	private final ComputedExpression computedExpression;

	private final String id;

	/**
	 * Constructor.
	 *
	 * @param id the ID of the field
	 * @param fieldName the name of the field
	 * @param type the type of the field
	 * @param domainSketch the domain of the field
	 * @param label the label of the field
	 * @param required if the field is required
	 * @param persistent if the field is persistent
	 * @param fkDtDefinitionName Nom de la DtDefinition de la FK (noNull si type=FK)
	 * @param computedExpression Expression du computed (noNull si type=Computed)
	 * @param dynamic if the field is dynamic
	 */
	DtSketchField(
			final String id,
			final String fieldName,
			final FieldType type,
			final DomainSketch domainSketch,
			final MessageText label,
			final Cardinality cardinality,
			final boolean persistent,
			final String fkDtDefinitionName,
			final ComputedExpression computedExpression) {
		Assertion.check()
				.isNotBlank(id)
				.isNotNull(type)
				.isNotNull(domainSketch)
				.isNotNull(type)
				.isNotNull(cardinality);
		//-----
		this.id = id;
		this.domainSketch = domainSketch;
		this.type = type;
		this.cardinality = cardinality;
		//-----
		Assertion.check()
				.isNotNull(fieldName)
				.isTrue(fieldName.length() <= FIELD_NAME_MAX_LENGTH, "the name of the field {0} has a limit size of {1}", fieldName, FIELD_NAME_MAX_LENGTH)
				.isTrue(StringUtil.isLowerCamelCase(fieldName), "the name of the field {0} must be in lowerCamelCase", fieldName)
				.isNotNull(label);
		//-----
		name = fieldName;
		this.label = label;
		//-----
		Assertion.check().isFalse(getType() == FieldType.COMPUTED && persistent, "a computed field can't be persistent");
		this.persistent = persistent;
		//-----
		if (getType() == FieldType.FOREIGN_KEY) {
			Assertion.check().isNotNull(fkDtDefinitionName, "Le champ {0} de type clé étrangère doit référencer une définition ", fieldName);
		} else {
			Assertion.check().isNull(fkDtDefinitionName, "Le champ {0} n''est pas une clé étrangère", fieldName);
		}
		this.fkDtDefinitionName = fkDtDefinitionName;
		//-----
		if (getType() == FieldType.COMPUTED) {
			Assertion.check().isNotNull(computedExpression, "the field {0}, declared as computed, must have an expression", fieldName);
		} else {
			Assertion.check().isNull(computedExpression, "the field {0}, not declared as computed, must have an empty expression", fieldName);
		}
		this.computedExpression = computedExpression;
	}

	/**
	 * @return the key of the resource (i18n)
	 */
	public String getResourceKey() {
		return id;
	}

	/**
	 * @return the name of the field
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the cardinality of the field (one, optional, many)
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}

	/**
	 * @return the type of the field
	 */
	public FieldType getType() {
		return type;
	}

	/**
	 * @return the domain of the field
	 */
	public DomainSketch getDomain() {
		return domainSketch;
	}

	/**
	 * @return the label of the field
	 */
	public MessageText getLabel() {
		return label;
	}

	/**
	 * Gestion de la persistance.
	 * @return Si le champ est persisté.
	 */
	public boolean isPersistent() {
		return persistent;
	}

	/**
	 *  @return DtDefinition de la ForeignKey (caractère obligatoire lié au type)
	 */
	//Todo changer le nom
	public String getFkDtDefinitionName() {
		Assertion.check().isNotNull(fkDtDefinitionName);
		//-----
		return fkDtDefinitionName;
	}

	/**
	 * Expression dans le cas d'un champ calculé.
	 *  @return ComputedExpression du champs calculé (caractère obligatoire lié au type)
	 */
	public ComputedExpression getComputedExpression() {
		Assertion.check().isNotNull(computedExpression);
		//-----
		return computedExpression;
	}

}
