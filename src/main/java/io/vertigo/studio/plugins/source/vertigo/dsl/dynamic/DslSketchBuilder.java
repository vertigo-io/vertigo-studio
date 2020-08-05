/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
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
package io.vertigo.studio.plugins.source.vertigo.dsl.dynamic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntityField;

/**
 * Interface de création des définitions.
 * @author  pchretien
 */
public final class DslSketchBuilder implements Builder<DslSketch> {
	/** Type. */
	private final DslEntity entity;

	/** Name of the package. */
	private String packageName;

	/**name of this definition.*/
	private final String name;

	/** Map  (fieldName, propertyValue)  */
	private final Map<DslEntityField, Object> propertyValueByFieldName = new HashMap<>();

	/**
	 * Links.
	 * Map (fieldName, definitions identified by its name)
	 */

	private final Map<DslEntityField, List<String>> definitionLinkNamesByFieldName = new LinkedHashMap<>();

	/**
	 * Children.
	 * Map (fieldName, definitions
	 */
	private final Map<DslEntityField, List<DslSketch>> childDefinitionsByFieldName = new LinkedHashMap<>();

	/**
	 * Constructor.
	 * @param name the name of the dslDefinition
	 * @param entity Entité
	 */
	DslSketchBuilder(final String name, final DslEntity entity) {
		Assertion.check()
				.isNotNull(name)
				.isNotNull(entity);
		//-----
		this.name = name;
		this.entity = entity;
		for (final DslEntityField dslEntityField : entity.getFields()) {
			if (dslEntityField.getType().isEntityLink()) {
				definitionLinkNamesByFieldName.put(dslEntityField, new ArrayList<>());
			} else if (dslEntityField.getType().isEntity()) {
				childDefinitionsByFieldName.put(dslEntityField, new ArrayList<>());
			}
			// else : nothing for property
		}
	}

	public DslEntity getEntity() {
		return entity;
	}

	/**
	 * @param newPackageName Package name
	 * @return Builder
	 */
	public DslSketchBuilder withPackageName(final String newPackageName) {
		packageName = newPackageName;
		return this;
	}

	/**
	 * @param dslDefinition Definition body
	 * @return this builder
	 */
	public DslSketchBuilder merge(final DslSketch dslDefinition) {
		if (packageName == null) {
			withPackageName(dslDefinition.getPackageName());
		}
		// 1. maj des EntityProperty
		for (final String propertyName : dslDefinition.getPropertyNames()) {
			addPropertyValue(propertyName, dslDefinition.getPropertyValue(propertyName));
		}

		for (final DslEntityField dslEntityField : entity.getFields()) {
			if (dslEntityField.getType().isEntityLink()) {
				// 2. link
				addAllDefinitionLinks(dslEntityField.getName(), dslDefinition.getDefinitionLinkNames(dslEntityField.getName()));
			} else if (dslEntityField.getType().isEntity()) {
				// 3. children
				addAllChildDefinitions(dslEntityField.getName(), dslDefinition.getChildDefinitions(dslEntityField.getName()));
			}
			// else : nothing for property (already processed)
		}
		return this;
	}

	/**
	 * @param fieldName Name of the field
	 * @param value Valeur de la propriété
	 * @return this builder
	 */
	public DslSketchBuilder addPropertyValue(final String fieldName, final Object value) {
		final DslEntityField dslEntityField = entity.getField(fieldName);
		Assertion.check().isTrue(dslEntityField.getType().isProperty(), "expected a property on {0}", fieldName);
		//----
		entity.getPropertyType(fieldName).checkValue(value);
		propertyValueByFieldName.put(dslEntityField, value);
		return this;
	}

	/**
	 * Ajoute une définition au champ défini par fieldName.
	 * La définition n'est connue que par sa référence, son nom.
	 * @param fieldName Name of the field
	 * @param definitionName Name of the definition
	 * @return this builder
	 */
	public DslSketchBuilder addDefinitionLink(final String fieldName, final String definitionName) {
		return addAllDefinitionLinks(fieldName, Collections.singletonList(definitionName));
	}

	/**
	 * Ajoute une liste de définitions au champ défini par fieldName.
	 * La définition n'est connue que par sa référence, son nom.
	 * @param fieldName Name of the field
	 * @param definitionNames  list of the names of the dedinitions
	 * @return this builder
	 */
	public DslSketchBuilder addAllDefinitionLinks(final String fieldName, final List<String> definitionNames) {
		Assertion.check().isNotNull(definitionNames);
		final DslEntityField dslEntityField = entity.getField(fieldName);
		Assertion.check().isTrue(dslEntityField.getType().isEntityLink(), "expected a link on {0}", fieldName);
		//---
		definitionLinkNamesByFieldName.get(dslEntityField)
				.addAll(definitionNames);
		return this;
	}

	private void addAllChildDefinitions(final String fieldName, final List<DslSketch> dslDefinitions) {
		Assertion.check().isNotNull(dslDefinitions);
		final DslEntityField dslEntityField = entity.getField(fieldName);
		Assertion.check().isTrue(dslEntityField.getType().isEntity(), "expected an entity on {0}", fieldName);
		//---
		childDefinitionsByFieldName.get(dslEntityField)
				.addAll(dslDefinitions);
	}

	/**
	 * Ajoute une définition au champ défini par fieldName.
	 * @param fieldName Name of the field
	 * @param definition Définition
	 * @return this builder
	 */
	public DslSketchBuilder addChildDefinition(final String fieldName, final DslSketch definition) {
		Assertion.check().isNotNull(definition);
		addAllChildDefinitions(fieldName, Collections.singletonList(definition));
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DslSketch build() {
		return new DslSketch(entity, packageName, name, propertyValueByFieldName, definitionLinkNamesByFieldName, childDefinitionsByFieldName);
	}

}
