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

/**
 * Defines a link to an entity.
 * @author pchretien, mlaroche
 */
public final class DslEntityLink implements DslEntityFieldType {
	private final DslEntity entity;

	/**
	 * Constructor
	 * @param entity the entity that is linked
	 */
	DslEntityLink(final DslEntity entity) {
		Assertion.check().isNotNull(entity);
		//-----
		this.entity = entity;
	}

	/**
	 * @return the linked entity
	 */
	public DslEntity getEntity() {
		return entity;
	}

	@Override
	public String toString() {
		return "Link<" + entity.getName() + ">";
	}

	@Override
	public boolean isEntityLink() {
		return true;
	}
}
