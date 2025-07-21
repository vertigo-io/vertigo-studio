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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;

/**
 * An entity describes a model (e.g., DataDefinition, TaskDefinition...).
 * - It is identified by a unique name.
 * - It contains properties (mandatory or optional).
 * - It is composed of fields.
 *
 * An entity enables dynamic behaviors and the creation of grammars.
 * The set of entities describes the grammar ( metamodel), while the definitions build the model.
 *
 * @author pchretien, mlaroche
 */
public final class DslEntity implements DslEntityFieldType {
	/** Name of the entity (type of the definition). */
	private final String name;

	/** Map of fields by name. */
	private final Map<String, DslEntityField> entityFields;

	/** Indicates if the entity is provided and managed specifically. */
	private final boolean provided;

	/**
	 * Constructor for an entity.
	 * An entity corresponds to a class or interface of a definition .
	 * @param name Name of the entity
	 * @param entityFields Set of the entity's fields
	 * @param provided Indicates if the entity is provided
	 */
	DslEntity(final String name, final Set<DslEntityField> entityFields, final boolean provided) {
		Assertion.check()
				.isNotNull(name)
				.isNotNull(entityFields);
		//-----
		this.name = name;
		this.provided = provided;
		this.entityFields = new HashMap<>();
		for (final DslEntityField entityField : entityFields) {
			Assertion.check().isFalse(this.entityFields.containsKey(entityField.name()), "field {0} is already registered for {1}", entityField, this);
			//a property is unique for an entity .
			this.entityFields.put(entityField.name(), entityField);
		}
	}

	/**
	 * Static method factory for DslEntityBuilder
	 * @param name the name of the entity
	 * @return DslEntityBuilder
	 */
	public static DslEntityBuilder builder(final String name) {
		return new DslEntityBuilder(name);
	}

	/**
	 * @return Name of the entity
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Set of all property names (mandatory or optional)
	 */
	public Set<String> getPropertyNames() {
		return entityFields.values()
				.stream()
				.filter(field -> field.type().isProperty())
				.map(DslEntityField::name)
				.collect(Collectors.toSet());
	}

	/**
	 * Retrieves the property type for a given field.
	 * @param fieldName Name of the field
	 * @return Property type
	 */
	public DslPropertyType getPropertyType(final String fieldName) {
		final DslEntityFieldType type = getField(fieldName).type();
		Assertion.check().isTrue(type.isProperty(), "property {0} not found on {1}", fieldName, this);
		//-----
		return (DslPropertyType) type;
	}

	/**
	 * Retrieves the field associated with the specified name.
	 * @param fieldName Name of the field
	 * @return Field
	 */
	public DslEntityField getField(final String fieldName) {
		Assertion.check()
				.isNotNull(fieldName)
				.isTrue(entityFields.containsKey(fieldName), "Field  '{0}' is not declared on entity '{1}'", fieldName, this);
		//-----
		return entityFields.get(fieldName);
	}

	/**
	 * @return Collection of the entity's fields
	 */
	public Collection<DslEntityField> getFields() {
		return entityFields.values();
	}

	/**
	 * @return Link to this entity
	 */
	public DslEntityLink getLink() {
		return new DslEntityLink(this);
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return if this entity is identified as core and managed specificaly.
	 */
	public boolean isProvided() {
		return provided;
	}

	@Override
	public boolean isEntity() {
		return true;
	}
}
