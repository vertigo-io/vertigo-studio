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

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;

/**
 * Represents a field of a DSL entity.
 * A field is defined by its name, type, and cardinality.
 *
 * @author pchretien, mlaroche
 * @param name The name of the field
 * @param type The type of the field
 * @param cardinality The cardinality of the field (e.g., single or multiple values)
 */
public record DslEntityField(
		String name,
		DslEntityFieldType type,
		Cardinality cardinality) {

	public DslEntityField {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(type)
				.isNotNull(cardinality);
	}
}
