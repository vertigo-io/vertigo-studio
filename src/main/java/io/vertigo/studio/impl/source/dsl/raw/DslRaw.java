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
package io.vertigo.studio.impl.source.dsl.raw;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslEntityField;

/**
 * Classe permettant de créer dynamiquement une structure grammaticale.
 * Cette Classe est utilisée pour parcourir dynamiquement les modèles.
 * Rappelons qu'une structure est elle-même composée de sous structure grammaticales.
 *
 * @author  pchretien
 */
public final class DslRaw {
	/** Type. */
	private final DslEntity entity;

	/** Name of the package. */
	private final String packageName;

	/**key of this sketch.*/
	private final DslRawKey rawKey;

	/** Map  (fieldName, propertyValue)  */
	private final Map<DslEntityField, Object> propertyValueByEntityField;

	/**
	 * Links.
	 * Map (fieldName, definitions identified by its name)
	 */
	private final Map<DslEntityField, List<DslRawKey>> rawKeysByEntityField;

	/**
	 * Children.
	 * Map (fieldName, definitions
	 */
	private final Map<DslEntityField, List<DslRaw>> subRawsByEntityField;

	DslRaw(
			final DslEntity entity,
			final String packageName,
			final DslRawKey rawKey,
			final Map<DslEntityField, Object> propertyValueByEntityField,
			final Map<DslEntityField, List<DslRawKey>> rawKeysByEntityField,
			final Map<DslEntityField, List<DslRaw>> subRawsByEntityField) {
		Assertion.check()
				.isNotNull(entity)
				//packageName can be null
				.isNotNull(rawKey)
				.isNotNull(propertyValueByEntityField)
				.isNotNull(rawKeysByEntityField)
				.isNotNull(subRawsByEntityField);
		//---
		this.entity = entity;
		this.packageName = packageName;
		this.rawKey = rawKey;
		this.propertyValueByEntityField = propertyValueByEntityField;
		this.rawKeysByEntityField = rawKeysByEntityField;
		this.subRawsByEntityField = subRawsByEntityField;
	}

	/**
	 * Static method builder for defintions.
	 * @param name the name of the dslSketch
	 * @param entity entity
	 * @return the dsl sketch builder
	 */
	public static DslRawBuilder builder(final DslRawKey rawKey, final DslEntity entity) {
		return new DslRawBuilder(rawKey, entity);
	}

	public static DslRawBuilder builder(final String rawName, final DslEntity entity) {
		return new DslRawBuilder(DslRawKey.of(rawName), entity);
	}

	/**
	 * @return Nom du package
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return Entité
	 */
	public DslEntity getEntity() {
		return entity;
	}

	/**
	 * @return Nom de la Définition
	 */
	public DslRawKey getKey() {
		return rawKey;
	}

	/**
	 * Retourne la valeur d'une (méta) propriété liée au domaine, champ, dtDéfinition...
	 * null si cette propriété n'existe pas
	 * @param fieldName Name of the field
	 * @return valeur de la propriété
	 */
	public Object getPropertyValue(final String fieldName) {
		final DslEntityField entityField = entity.getField(fieldName);
		Assertion.check().isTrue(entityField.getType().isProperty(), "expected a property on {0}", fieldName);
		// On ne vérifie rien sur le type retourné par le getter.
		// le type a été validé lors du put.
		//-----
		// Conformémément au contrat, on retourne null si pas de propriété trouvée
		return propertyValueByEntityField.get(entityField);
	}

	/**
	 * Set des propriétés gérées.
	 * @return Collection
	 */
	public Set<String> getPropertyNames() {
		return propertyValueByEntityField.keySet()
				.stream()
				.map(DslEntityField::getName)
				.collect(Collectors.toSet());
	}

	/**
	 * Permet de récupérer la liste des définitions d'un champ.
	 *
	 * @param fieldName Nom du champ.
	 * @return List
	 */
	public List<DslRawKey> getRawKeysByFieldName(final String fieldName) {
		final DslEntityField entityField = entity.getField(fieldName);
		Assertion.check().isTrue(entityField.getType().isEntityLink(), "expected a link on {0}", fieldName);
		//---
		return rawKeysByEntityField.get(entityField);
	}

	/**
	 * Uniquement si il y a une et une seule référence pour ce champ.
	 * @param fieldName Nom du champ.
	 * @return Clé de la définition
	 */
	public DslRawKey getRawKeyByFieldName(final String fieldName) {
		final List<DslRawKey> fieldRawKeys = getRawKeysByFieldName(fieldName);
		final DslRawKey fieldRawKey = fieldRawKeys.get(0);
		//-----
		// On vérifie qu'il y a une définition pour le champ demandé
		Assertion.check().isNotNull(fieldRawKey);
		return fieldRawKey;
	}

	/**
	 * Permet de récupérer la collection de tous les champs qui pointent vers des définitions utilisées par référence.
	 * @return Collection de tous les champs utilisant des définitions référencées.
	 */
	public Set<DslEntityField> getAllRawLinkFields() {
		return rawKeysByEntityField.keySet();
	}

	/**
	 * Récupération de la liste des definitions dont est composée la définition principale.
	 * @param fieldName String
	 * @return List
	 */
	public List<DslRaw> getSubRaws(final String fieldName) {
		final DslEntityField entityField = entity.getField(fieldName);
		Assertion.check().isTrue(entityField.getType().isEntity(), "expected an entity on {0}", fieldName);
		//---
		return subRawsByEntityField.get(entityField);
	}

	/**
	 * @return Collection des listes de définitions composites.
	 */
	public List<DslRaw> getAllSubRaws() {
		return subRawsByEntityField.values()
				.stream()
				.flatMap(List::stream)
				.toList();
	}
}
