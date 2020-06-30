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
package io.vertigo.studio.metamodel.domain;

import java.util.List;
import java.util.Properties;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.core.node.definition.AbstractDefinition;
import io.vertigo.core.node.definition.DefinitionPrefix;

/**
 * A domain exists to enrich the primitive datatypes, giving them super powers.
 *
 * A domain has
 *  - a validator (executed by a list of constraints)
 *  - a formatter
 *
 * A domain is a shared object ; by nature it is immutable.
 *
 * A domain is a definition, its prefix is "Do"
 *
 * Examples :
 *  A mail is not defined by a simple "String", but by a domain called 'Mail'.
 *  Weights, currencies, codes, labels...
 *
 *  An application is built with some dozens of domains.
 *
 * @author pchretien, mlaroche
 */
@DefinitionPrefix(Domain.PREFIX)
public final class Domain extends AbstractDefinition {
	public static final String PREFIX = "Do";

	public enum Scope {
		PRIMITIVE, VALUE_OBJECT, DATA_OBJECT;

		/**
		 * @return if the domain is a primitive type
		 */
		public boolean isPrimitive() {
			return this == Scope.PRIMITIVE;
		}

		/**
		 * @return if the domain is a value-object
		 */
		public boolean isValueObject() {
			return this == Scope.VALUE_OBJECT;
		}

		/**
		 * @return if the domain is a data-object
		 */
		public boolean isDataObject() {
			return this == Scope.DATA_OBJECT;
		}
	}

	private final Scope scope;
	private final BasicType dataType;

	private final String valueObjectClassName;
	/**
	 * Name of the DtDefinition (for the DtObject or DtList)
	 */
	private final String dtDefinitionName;

	/** Formatter. */
	private final FormatterDefinition formatterDefinition;

	/** List of property-value tuples */
	private final Properties properties;
	private final List<ConstraintDefinition> constraintDefinitions;

	/**
	 * Constructor.
	 * @param name the name of the domain
	 * @param scope the scope of the domain
	 * @param dataType the type of the domain
	 * @param formatterDefinition the formatter
	 * @param constraintDefinitions the list of constraints
	 * @param properties List of property-value tuples
	 */
	Domain(
			final String name,
			final Scope scope,
			final BasicType dataType,
			final String dtDefinitionName,
			final String valueObjectClassName,
			final FormatterDefinition formatterDefinition,
			final List<ConstraintDefinition> constraintDefinitions,
			final Properties properties) {
		super(name);
		//---
		Assertion.check().isNotNull(scope);
		//---
		Assertion.when(scope == Scope.PRIMITIVE)
				.isTrue(() -> dataType != null, "a primitive domain must define a primitive type")
				.isTrue(() -> dtDefinitionName == null && valueObjectClassName == null, "a primitive domain can't have nor a data-object-definition nor a value-object class");
		//---
		Assertion.when(scope == Scope.DATA_OBJECT)
				.isTrue(() -> dtDefinitionName != null, "a data-object domain must define a data-object definition")
				.isTrue(() -> dataType == null && valueObjectClassName == null, "a data-object domain can't have nor a primitive type nor a value-object class");
		//---
		Assertion.when(scope == Scope.VALUE_OBJECT)
				.isTrue(() -> valueObjectClassName != null, "a value-object domain must define a value-object class")
				.isTrue(() -> dataType == null && dtDefinitionName == null, "a value-object domain can't have nor a primitive type nor a data-object-definition");
		//formatterDefinition is nullable
		Assertion.check()
				.isNotNull(constraintDefinitions)
				.isNotNull(properties);
		//-----
		this.scope = scope;
		//--- Primitive
		this.dataType = dataType;
		//---data-object
		this.dtDefinitionName = dtDefinitionName;
		//---
		this.valueObjectClassName = valueObjectClassName;
		//---
		this.formatterDefinition = formatterDefinition;
		this.constraintDefinitions = constraintDefinitions;
		//---Properties
		this.properties = properties;
	}

	/**
	 * Static method factory for DomainBuilder
	 * @param name the name of the domain
	 * @param dataType the dataType managed by the domain
	 * @return DomainBuilder
	 */
	public static DomainBuilder builder(final String name, final BasicType dataType) {
		return new DomainBuilder(name, dataType);
	}

	/**
	 * Static method factory for DomainBuilder
	 * @param name the name of the domain
	 * @param dtDefinitionName the definition managed by the domain
	 * @return DomainBuilder
	 */
	public static DomainBuilder builder(final String name, final String dtDefinitionName) {
		return new DomainBuilder(name, dtDefinitionName);
	}

	/**
	 * Static method factory for DomainBuilder
	 * @param name the name of the domain
	 * @param valueObjectClass the definition managed by the domain
	 * @return DomainBuilder
	 */
	public static DomainBuilder builder(final String name, final Class valueObjectClass) {
		return new DomainBuilder(name, valueObjectClass);
	}

	/**
	 * Returns the dataType of the domain.
	 *
	 * @return the dataType.
	 */
	public BasicType getDataType() {
		Assertion.check().isTrue(scope == Scope.PRIMITIVE, "can only be used with primitives");
		//---
		return dataType;
	}

	/**
	 * Returns the formatter of the domain.
	 *
	 * @return the formatter.
	 */
	public FormatterDefinition getFormatterDefinition() {
		Assertion.check().isNotNull(formatterDefinition, "no formatter defined on {0}", this);
		//-----
		return formatterDefinition;
	}

	public List<ConstraintDefinition> getConstraintDefinitions() {
		return constraintDefinitions;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	//==========================================================================
	//for these domains : DtList or DtObject
	//==========================================================================

	public String getDtDefinitionName() {
		Assertion.check().isTrue(scope == Scope.DATA_OBJECT, "can only be used with data-objects");
		//---
		return dtDefinitionName;
	}

	public String getSmartTypeName() {
		return "STy" + getLocalName();
	}

	/**
	 * @return the domain scope
	 */
	public Scope getScope() {
		return scope;
	}

	public String getValueObjectClassName() {
		Assertion.check().isTrue(scope == Scope.VALUE_OBJECT, "can only be used with value-objects");
		//---
		return valueObjectClassName;
	}
}
