/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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

/**
 * There are 3 types of field :
 * - property
 * - entity
 * - entity Link
 *
 * @author pchretien, mlaroche
 *
 */
public interface DslEntityFieldType {

	/**
	 * @return If the field is a property
	 */
	default boolean isProperty() {
		return false;
	}

	/**
	 * @return If the field is a link towards an entity
	 */
	default boolean isEntityLink() {
		return false;
	}

	/**
	 * @return If the field is a child entity
	 */
	default boolean isEntity() {
		return false;
	}
}
