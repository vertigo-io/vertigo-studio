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
package io.vertigo.dynamo.domain.metamodel;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.core.node.Home;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionPrefix;
import io.vertigo.core.node.definition.DefinitionReference;
import io.vertigo.core.node.definition.DefinitionUtil;
import io.vertigo.core.util.ClassUtil;
import io.vertigo.datamodel.structure.metamodel.FormatterException;
import io.vertigo.datamodel.structure.metamodel.Properties;

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
 * @author pchretien
 */
@DefinitionPrefix("Do")
public final class Domain implements Definition {
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

	private final String name;
	private final Scope scope;
	private final BasicType dataType;

	private final Class valueObjectClass;
	/**
	 * Name of the DtDefinition (for the DtObject or DtList)
	 */
	private final String dtDefinitionName;

	/** Formatter. */
	private final DefinitionReference<FormatterDefinition> formatterDefinitionRef;

	/** List of property-value tuples */
	private final Properties properties;

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
			final Class valueObjectClass,
			final FormatterDefinition formatterDefinition,
			final List<ConstraintDefinition> constraintDefinitions,
			final Properties properties) {
		Assertion.checkArgNotEmpty(name);
		Assertion.checkNotNull(scope);
		//---
		Assertion.when(scope == Scope.PRIMITIVE).check(() -> dataType != null, "a primitive domain must define a primitive type");
		Assertion.when(scope == Scope.PRIMITIVE).check(() -> dtDefinitionName == null && valueObjectClass == null, "a primitive domain can't have nor a data-object-definition nor a value-object class");
		//---
		Assertion.when(scope == Scope.DATA_OBJECT).check(() -> dtDefinitionName != null, "a data-object domain must define a data-object definition");
		Assertion.when(scope == Scope.DATA_OBJECT).check(() -> dataType == null && valueObjectClass == null, "a data-object domain can't have nor a primitive type nor a value-object class");
		//---
		Assertion.when(scope == Scope.VALUE_OBJECT).check(() -> valueObjectClass != null, "a value-object domain must define a value-object class");
		Assertion.when(scope == Scope.VALUE_OBJECT).check(() -> dataType == null && dtDefinitionName == null, "a value-object domain can't have nor a primitive type nor a data-object-definition");
		//formatterDefinition is nullable
		Assertion.checkNotNull(constraintDefinitions);
		Assertion.checkNotNull(properties);
		//-----
		this.name = name;
		this.scope = scope;
		//--- Primitive
		this.dataType = dataType;
		//---data-object
		this.dtDefinitionName = dtDefinitionName;
		//---
		this.valueObjectClass = valueObjectClass;
		//---
		formatterDefinitionRef = formatterDefinition == null ? null : new DefinitionReference<>(formatterDefinition);
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
		Assertion.checkState(scope == Scope.PRIMITIVE, "can only be used with primitives");
		//---
		return dataType;
	}

	/**
	 * Returns the formatter of the domain.
	 *
	 * @return the formatter.
	 */
	private FormatterDefinition getFormatter() {
		Assertion.checkNotNull(formatterDefinitionRef, "no formatter defined on {0}", this);
		//-----
		return formatterDefinitionRef.get();
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Chechs if the value is valid.
	 *
	 * @param value the value to check
	 */
	public void checkValue(final Object value) {
		if (getScope().isPrimitive()) {
			dataType.checkValue(value);
		}
	}

	//==========================================================================
	//for these domains : DtList or DtObject
	//==========================================================================
	/**
	 * @return the dtDefinition for the domains DtList or DtObject.
	 */
	public StudioDtDefinition getDtDefinition() {
		Assertion.checkState(scope == Scope.DATA_OBJECT, "can only be used with data-objects");
		//---
		return Home.getApp().getDefinitionSpace().resolve("St" + dtDefinitionName, StudioDtDefinition.class);
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return name;
	}

	public String getSmartTypeName() {
		return "STy" + DefinitionUtil.getLocalName(name, Domain.class);
	}

	/**
	 * @return the domain scope
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * Gets the type managed by this domain.
	 * Warn : if the domain is a list, the return type is the one inside the list.
	 *
	 * Example :
	 *	Integer => Integer
	 * 	List<Integer> => Integer
	 * 	Car => Car
	 * 	DtList<Car> => Car
	 * @return the class of the object
	 */
	public Class getJavaClass() {
		switch (scope) {
			case PRIMITIVE:
				return dataType.getJavaClass();
			case DATA_OBJECT:
				return ClassUtil.classForName(getDtDefinition().getClassCanonicalName());
			case VALUE_OBJECT:
				return valueObjectClass;
			default:
				throw new IllegalStateException();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name;
	}

	public String getFormatterClassName() {
		Assertion.checkNotNull(dataType, "can only be used with primitives");
		//---
		return getFormatter().getFormatterClassName();
	}

	public String valueToString(final Object objValue) {
		Assertion.checkNotNull(dataType, "can only be used with primitives");
		//---
		return getFormatter().valueToString(objValue, dataType);
	}

	public Object stringToValue(final String strValue) throws FormatterException {
		Assertion.checkNotNull(dataType, "can only be used with primitives");
		//---
		return getFormatter().stringToValue(strValue, dataType);
	}

}
