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

import java.util.Properties;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;

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
@SkecthPrefix(DomainSketch.PREFIX)
public final class DomainSketch extends AbstractSketch {
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
	private final String dtSketchName;

	/** List of property-value tuples */
	private final Properties properties;

	/**
	 * Constructor.
	 * @param name the name of the domain
	 * @param scope the scope of the domain
	 * @param dataType the type of the domain
	 * @param formatterSketch the formatter
	 * @param constraintSketchs the list of constraints
	 * @param properties List of property-value tuples
	 */
	DomainSketch(
			final String name,
			final Scope scope,
			final BasicType dataType,
			final String dtDefinitionName,
			final String valueObjectClassName,
			final Properties properties) {
		super(name);
		//---
		Assertion.check()
				.isNotNull(scope)
				//---
				.when(scope.isPrimitive(), () -> Assertion.check()
						.isTrue(dataType != null, "a primitive domain must define a primitive type")
						.isTrue(dtDefinitionName == null && valueObjectClassName == null, "a primitive domain can't have nor a data-object-definition nor a value-object class"))
				//---
				.when(scope.isDataObject(), () -> Assertion.check()
						.isTrue(dtDefinitionName != null, "a data-object domain must define a data-object definition")
						.isTrue(dataType == null && valueObjectClassName == null, "a data-object domain can't have nor a primitive type nor a value-object class"))
				//---
				.when(scope.isValueObject(), () -> Assertion.check()
						.isTrue(valueObjectClassName != null, "a value-object domain must define a value-object class")
						.isTrue(dataType == null && dtDefinitionName == null, "a value-object domain can't have nor a primitive type nor a data-object-definition"))
				.isNotNull(properties);
		//-----
		this.scope = scope;
		//--- Primitive
		this.dataType = dataType;
		//---data-object
		this.dtSketchName = dtDefinitionName;
		//---
		this.valueObjectClassName = valueObjectClassName;
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
		Assertion.check().isTrue(scope.isPrimitive(), "can only be used with primitives");
		//---
		return dataType;
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

	public String getDtSketchName() {
		Assertion.check().isTrue(scope.isDataObject(), "can only be used with data-objects");
		//---
		return dtSketchName;
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
		Assertion.check().isTrue(scope.isValueObject(), "can only be used with value-objects");
		//---
		return valueObjectClassName;
	}
}
