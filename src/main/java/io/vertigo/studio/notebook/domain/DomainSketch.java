/*
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
package io.vertigo.studio.notebook.domain;

import java.util.Properties;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;
import io.vertigo.studio.notebook.SketchKey;

/**
 * A domain exists to enrich the primitive datatypes, giving them super powers.
 *
 * A domain has
 *  - a validator (executed by a list of constraints)
 *  - a formatter
 *
 * A domain is a shared object ; by nature it is immutable.
 *
 * A domain is a sketch, its prefix is "Do"
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
	 * Key of the DtSketch (for the DtObject or DtList)
	 */
	private final SketchKey dtSketchKey;

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
			final Properties properties,
			final Scope scope,
			final BasicType dataType,
			final SketchKey dtSketchKey,
			final String valueObjectClassName) {
		super(name);
		//---
		Assertion.check()
				.isNotNull(scope)
				//---
				.when(scope.isPrimitive(), () -> Assertion.check()
						.isTrue(dataType != null, "a primitive domain must define a primitive type"))
				//---
				.when(scope.isDataObject(), () -> Assertion.check()
						.isTrue(dtSketchKey != null, "a data-object domain must define a data-object sketch"))
				//---
				.when(scope.isValueObject(), () -> Assertion.check()
						.isTrue(valueObjectClassName != null, "a value-object domain must define a value-object class"))
				.isNotNull(properties);
		//-----
		this.scope = scope;
		//--- Primitive
		this.dataType = dataType;
		//---data-object
		this.dtSketchKey = dtSketchKey;
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
	public static DomainSketch of(final String name, final Properties properties, final BasicType dataType) {
		return new DomainSketch(
				name,
				properties,
				DomainSketch.Scope.PRIMITIVE,
				dataType,
				null,
				null);

	}

	/**
	 * Static method factory for DomainBuilder
	 * @param name the name of the domain
	 * @param dtSketchKey the key  of the dtSketch managed by the domain
	 * @return DomainBuilder
	 */
	public static DomainSketch of(final String name, final Properties properties, final SketchKey dtSketchKey) {
		return new DomainSketch(
				name,
				properties,
				Scope.DATA_OBJECT,
				null,
				dtSketchKey,
				null);
	}

	/**
	 * Static method factory for DomainBuilder
	 * @param name the name of the domain
	 * @param valueObjectClassName the class of the valueObject
	 * @return DomainBuilder
	 */
	public static DomainSketch of(final String name, final Properties properties, final String valueObjectClassName) {
		return new DomainSketch(
				name,
				properties,
				DomainSketch.Scope.VALUE_OBJECT,
				null,
				null,
				valueObjectClassName);
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

	public SketchKey getDtSketchKey() {
		Assertion.check().isTrue(scope.isDataObject(), "can only be used with data-objects");
		//---
		return dtSketchKey;
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
