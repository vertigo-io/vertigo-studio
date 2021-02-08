/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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
 * Une entité permet de décrire un modèle, une classe.
 * - Elle est définie par son nom.
 * - Elle possède une liste de propriétés (Chacune étant obligatoire / facultative)
 * - Elle est composée d'une liste d'attibuts.
 *
 * Une entité permet, ainsi, d'adopter des comportement dynamique, de fabriquer des grammaires.
 * Si l'ensemble des définitions permet de construire le modèle, l'ensemble des entités permet de décrire le métamodèle.
 *
 * @author pchretien, mlaroche
 */
public final class DslEntity implements DslEntityFieldType {
	/**
	 * Nom de la metadefinition (Type de la définition).
	 */
	private final String name;

	/**
	 * Map : Field by names
	 */
	private final Map<String, DslEntityField> entityFields;

	private final boolean provided;

	/**
	 * Constructeur de la MetaDefinition
	 * Une instance de MetaDefinition correspond à une classe -ou une interface- de Definition
	 * (Exemple : Classe Service).
	 * @param name Classe représentant l'instance métaDéfinition
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
			Assertion.check().isFalse(this.entityFields.containsKey(entityField.getName()), "field {0} is already registered for {1}", entityField, this);
			//Une propriété est unique pour une définition donnée.
			//Il n'y a jamais de multiplicité
			this.entityFields.put(entityField.getName(), entityField);
		}
	}

	/**
	 * Static method factory for TaskBuilder
	 * @param name the name of the entity
	 * @return TaskBuilder
	 */
	public static DslEntityBuilder builder(final String name) {
		return new DslEntityBuilder(name);
	}

	/**
	 * @return Nom de l'entité (Type de la définition).
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Ensemble de toutes les propriétés gérées (obligatoires ou non).
	 */
	public Set<String> getPropertyNames() {
		return entityFields.values()
				.stream()
				.filter(field -> field.getType().isProperty())
				.map(DslEntityField::getName)
				.collect(Collectors.toSet());
	}

	/**
	 * @param fieldName Name of the field
	 * @return Property type
	 */
	public DslPropertyType getPropertyType(final String fieldName) {
		final DslEntityFieldType type = getField(fieldName).getType();
		Assertion.check().isTrue(type.isProperty(), "property {0} not found on {1}", fieldName, this);
		//-----
		return (DslPropertyType) type;
	}

	/**
	 * Returns the value to which the specified name is mapped.
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
	 * @return List of the entity's fields
	 */
	public Collection<DslEntityField> getFields() {
		return entityFields.values();
	}

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
