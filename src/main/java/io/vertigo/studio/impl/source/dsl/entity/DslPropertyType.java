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
package io.vertigo.studio.impl.source.dsl.entity;

import java.util.Locale;

import io.vertigo.core.lang.Assertion;

/**
 * Enumeration of supported DSL property types.
 * To ensure simplicity, only a few Java types are supported:
 * - Integer
 * - Double
 * - Boolean
 * - String
 *
 * @author pchretien
 */
public enum DslPropertyType implements DslEntityFieldType {
	/** Integer type. */
	Integer(Integer.class),

	/** Double type. */
	Double(Double.class),

	/** Boolean type. */
	Boolean(Boolean.class),

	/** String type. */
	String(String.class);

	/**
	 * The Java class encapsulated by this type.
	 */
	private final Class<?> javaClass;

	/**
	 * Constructor.
	 *
	 * @param javaClass The encapsulated Java class
	 */
	DslPropertyType(final Class<?> javaClass) {
		Assertion.check().isNotNull(javaClass, "Java class cannot be null");
		this.javaClass = javaClass;
	}

	/**
	 * Checks if a value is compatible with this type.
	 *
	 * @param value The value to check (can be null)
	 * @throws ClassCastException If the value is not an instance of the defined Java class
	 */
	public void checkValue(final Object value) {
		if (value != null && !javaClass.isInstance(value)) {
			throw new ClassCastException("Value " + value + " does not match type: " + this);
		}
	}

	/**
	 * Converts a string to a typed value matching this DSL type.
	 *
	 * @param stringValue The input string to convert (can be null)
	 * @return The typed object corresponding to the string, or null if the string is empty or null
	 * @throws IllegalArgumentException If the conversion is not possible or the type is not supported
	 */
	public Object cast(final String stringValue) {
		final String sValue = stringValue == null ? null : stringValue.trim();
		if (sValue == null || sValue.isEmpty()) {
			return null;
		}
		return switch (this) {
			case Integer -> java.lang.Integer.valueOf(sValue);
			case Double -> java.lang.Double.valueOf(sValue);
			case String -> sValue;
			case Boolean -> parseBoolean(sValue);
			default -> throw new IllegalArgumentException("Unsupported property type: '" + javaClass.getName() + "'");
		};
	}

	/**
	 * Converts a string to a boolean.
	 * Only "true" and "false" (case-insensitive) are accepted.
	 *
	 * @param sValue The string to convert
	 * @return The corresponding boolean value
	 * @throws IllegalArgumentException If the string is neither "true" nor "false"
	 */
	private Object parseBoolean(final String sValue) {
		return switch (sValue.toLowerCase(Locale.ROOT)) {
			case "true" -> true;
			case "false" -> false;
			default -> throw new IllegalArgumentException("Cannot convert boolean property from '" + sValue + "', only 'true' or 'false' are accepted");
		};
	}

	/**
	 * Indicates if this type represents a property.
	 *
	 * @return true, as this type is a property
	 */
	@Override
	public boolean isProperty() {
		return true;
	}
}
