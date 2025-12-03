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
package io.vertigo.vortex.model.types;

import io.vertigo.core.lang.Assertion;

/**
 * Property (meta-data, aspect, attribute) transverse managed by the application.
 * Some properties are natively managed by Dynamo,
 * they are listed as constants.
 * <br><br>
 * <u>Example :</u> the mandatory nature of a field is declared at the model level,
 * thanks to the NOT_NULL property. This information is used to
 * <ul>
 * <li>automate server-side unit business tests,</li>
 * <li>automate client-side surface tests (e.g. using javascript),</li>
 * <li>modify the display to inform the user about the mandatory nature
 *      of the field. (Example: bold label or asterisk next to the field)</li>
 * </ul>
 * This information or property can be directly carried by the field or
 * more effectively carried by a business domain.
 * <br>
 * The purpose of the business domain is to go beyond simple technical types
 * to enrich them with strong semantics (the mandatory nature for example).
 * <br>
 * This rich semantics being used automatically and transparently
 * in the Dynamo framework or the application's common framework.
 *
 * @author  pchretien
 * @param <T> type of the property value
 */
public record VXProperty<T>(
		VXPropertyKey<T> key,
		T value) {

	/**
	 * Constructor for the property.
	 * @param key The key defining the property's name and type.
	 * @param value The value of the property.
	 */
	public VXProperty {
		Assertion.check()
				.isNotNull(key)
				.isNotNull(value); // Value can be null if it's an optional property, but for now we enforce non-null.

		// Ensure the value's type matches the key's type
		Assertion.check().isTrue(key.type().isInstance(value),
				"Value type '{0}' does not match expected type '{1}' for property '{2}'",
				value.getClass().getSimpleName(), key.type().getSimpleName(), key.name());
	}
}
