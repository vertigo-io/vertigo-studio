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
package io.vertigo.studio.impl.source.dsl.raw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslEntityField;

/**
 * Interface de création des définitions.
 * @author  pchretien
 */
public final class DslRawBuilder implements Builder<DslRaw> {
	/** Type. */
	private final DslEntity entity;

	/** Name of the package. */
	private String myPackageName;

	/**key of this sketch.*/
	private final DslRawKey rawKey;

	/** Map  (fieldName, propertyValue)  */
	private final Map<DslEntityField, Object> propertyValueByEntityField = new HashMap<>();

	/**
	 * Links.
	 * Map (fieldName, definitions identified by its name)
	 */

	private final Map<DslEntityField, List<DslRawKey>> rawKeysByEntityField = new LinkedHashMap<>();

	/**
	 * Children.
	 * Map (fieldName, definitions
	 */
	private final Map<DslEntityField, List<DslRaw>> subRawsByEntityField = new LinkedHashMap<>();

	/**
	 * Constructor.
	 * @param name the name of the dslDefinition
	 * @param entity Entité
	 */
	DslRawBuilder(final DslRawKey rawKey, final DslEntity entity) {
		Assertion.check()
				.isNotNull(rawKey)
				.isNotNull(entity);
		//-----
		this.rawKey = rawKey;
		this.entity = entity;
		for (final DslEntityField dslEntityField : entity.getFields()) {
			if (dslEntityField.getType().isEntityLink()) {
				rawKeysByEntityField.put(dslEntityField, new ArrayList<>());
			} else if (dslEntityField.getType().isEntity()) {
				subRawsByEntityField.put(dslEntityField, new ArrayList<>());
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
	public DslRawBuilder withPackageName(final String packageName) {
		myPackageName = packageName;
		return this;
	}

	/**
	 * @param raw Definition body
	 * @return this builder
	 */
	public DslRawBuilder merge(final DslRaw raw) {
		if (myPackageName == null) {
			withPackageName(raw.getPackageName());
		}
		// 1. maj des EntityProperty
		for (final String propertyName : raw.getPropertyNames()) {
			addPropertyValue(propertyName, raw.getPropertyValue(propertyName));
		}

		for (final DslEntityField dslEntityField : entity.getFields()) {
			if (dslEntityField.getType().isEntityLink()) {
				// 2. link
				addAllRawLinks(dslEntityField.getName(), raw.getRawKeysByFieldName(dslEntityField.getName()));
			} else if (dslEntityField.getType().isEntity()) {
				// 3. children
				addAllSubRaws(dslEntityField.getName(), raw.getSubRaws(dslEntityField.getName()));
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
	public DslRawBuilder addPropertyValue(final String fieldName, final Object value) {
		final DslEntityField dslEntityField = entity.getField(fieldName);
		Assertion.check().isTrue(dslEntityField.getType().isProperty(), "expected a property on {0}", fieldName);
		//----
		entity.getPropertyType(fieldName).checkValue(value);
		propertyValueByEntityField.put(dslEntityField, value);
		return this;
	}

	/**
	 * Ajoute une définition au champ défini par fieldName.
	 * La définition n'est connue que par sa référence, son nom.
	 * @param fieldName Name of the field
	 * @param definitionName Name of the definition
	 * @return this builder
	 */
	public DslRawBuilder addRawLink(final String fieldName, final String rawName) {
		return addRawLink(fieldName, DslRawKey.of(rawName));
	}

	public DslRawBuilder addRawLink(final String fieldName, final DslRawKey fieldRawKey) {
		return addAllRawLinks(fieldName, List.of(fieldRawKey));
	}

	/**
	 * Ajoute une liste de définitions au champ défini par fieldName.
	 * La définition n'est connue que par sa référence, son nom.
	 * @param fieldName Name of the field
	 * @param definitionNames  list of the names of the dedinitions
	 * @return this builder
	 */
	public DslRawBuilder addAllRawLinks(final String fieldName, final List<DslRawKey> rawKeys) {
		Assertion.check().isNotNull(rawKeys);
		final DslEntityField dslEntityField = entity.getField(fieldName);
		Assertion.check().isTrue(dslEntityField.getType().isEntityLink(), "expected a link on {0}", fieldName);
		//---
		rawKeysByEntityField.get(dslEntityField)
				.addAll(rawKeys);
		return this;
	}

	private void addAllSubRaws(final String fieldName, final List<DslRaw> raws) {
		Assertion.check().isNotNull(raws);
		final DslEntityField dslEntityField = entity.getField(fieldName);
		Assertion.check().isTrue(dslEntityField.getType().isEntity(), "expected an entity on {0}", fieldName);
		//---
		subRawsByEntityField.get(dslEntityField)
				.addAll(raws);
	}

	/**
	 * Ajoute une définition au champ défini par fieldName.
	 * @param fieldName Name of the field
	 * @param subRaw Définition
	 * @return this builder
	 */
	public DslRawBuilder addSubRaw(final String fieldName, final DslRaw subRaw) {
		Assertion.check().isNotNull(subRaw);
		addAllSubRaws(fieldName, Collections.singletonList(subRaw));
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DslRaw build() {
		return new DslRaw(entity, myPackageName, rawKey, propertyValueByEntityField, rawKeysByEntityField, subRawsByEntityField);
	}

}
