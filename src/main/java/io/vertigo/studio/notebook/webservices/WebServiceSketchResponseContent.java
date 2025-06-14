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
package io.vertigo.studio.notebook.webservices;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;

public final class WebServiceSketchResponseContent {

	private final Type type;
	private final Cardinality cardinality;

	public WebServiceSketchResponseContent(
			final Type type,
			final Cardinality cardinality) {
		Assertion.check()
				.isNotNull(type)
				.isNotNull(cardinality);
		//-----
		this.type = type;
		this.cardinality = cardinality;
	}

	/**
	 * @return Parameter class
	 */
	public Class<?> getType() {
		if (type instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) type).getRawType();
		}
		return (Class<?>) type;
	}

	/**
	 * @return generics Type
	 */
	public Type getGenericType() {
		return type;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}

}
