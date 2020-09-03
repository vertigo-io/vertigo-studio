/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
 * Field of an entity.
 *
 * @author pchretien, mlaroche
 */
public final class DslEntityField {
	private final String name;
	private final Cardinality cardinality;
	private final DslEntityFieldType entityFieldType;

	/**
	 * Constructor.
	 * @param name Name
	 * @param entityFieldType Type of the entity
	 */
	DslEntityField(final String name, final DslEntityFieldType entityFieldType, final Cardinality cardinality) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(entityFieldType)
				.isNotNull(cardinality);
		//-----
		this.name = name;
		this.cardinality = cardinality;
		this.entityFieldType = entityFieldType;
	}

	/**
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the cardinality
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}

	/**
	 * @return the type of the entity
	 */
	public DslEntityFieldType getType() {
		return entityFieldType;
	}
}
