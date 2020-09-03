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

import java.util.HashSet;
import java.util.Set;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslEntityField;

/**
 *
 * Validates a sketch considering its own entity.
 *
 * @author pchretien, mlaroche
 *
 */
final class DslRawValidator {
	private DslRawValidator() {
		//utility Class
	}

	static void check(final DslRaw raw) {
		Assertion.check().isNotNull(raw);
		//-----
		final DslEntity entity = raw.getEntity();
		// 1.On vérifie la définition par rapport à la métadéfinition
		// 1.1 on vérifie les propriétés.
		final Set<String> propertyNames = raw.getPropertyNames();
		final Set<String> entityPropertyNames = entity.getPropertyNames();
		// 1.1.1 on vérifie que toutes les propriétés sont déclarées sur le
		// métamodèle
		checkProperties(raw, propertyNames, entityPropertyNames);

		// 1.1.2 on vérifie les propriétés obligatoires
		checkMandatoryProperties(raw, entity, propertyNames, entityPropertyNames);

		// 1.1.3 on vérifie les types des propriétés déclarées
		for (final String propertyName : propertyNames) {
			entity.getPropertyType(propertyName).checkValue(raw.getPropertyValue(propertyName));
		}

		// 1.2 on vérifie les définitions composites (sous définitions).
		raw.getAllSubRaws()
				.forEach(DslRawValidator::check);

		// 1.3 on vérifie les définitions références.
		// TODO vérifier les définitions références
	}

	private static void checkProperties(
			final DslRaw raw,
			final Set<String> propertyNames,
			final Set<String> entityPropertyNames) {
		// Vérification que toutes les propriétés sont déclarées sur le
		// métamodèle
		final Set<String> undeclaredPropertyNames = new HashSet<>();
		for (final String propertyName : propertyNames) {
			if (!entityPropertyNames.contains(propertyName)) {
				// Si la propriété n'est pas déclarée alors erreur
				undeclaredPropertyNames.add(propertyName);
			}
		}
		if (!undeclaredPropertyNames.isEmpty()) {
			throw new IllegalStateException("Sur l'objet '" + raw.getKey() + "' Il existe des propriétés non déclarées " + undeclaredPropertyNames);
		}
	}

	private static void checkMandatoryProperties(
			final DslRaw raw,
			final DslEntity entity,
			final Set<String> propertyNames,
			final Set<String> entityPropertyNames) {
		// Vérification des propriétés obligatoires
		final Set<String> unusedMandatoryPropertySet = new HashSet<>();
		for (final String propertyName : entityPropertyNames) {
			final DslEntityField entityField = entity.getField(propertyName);

			if ((entityField.getCardinality().hasOne())
					&& (!propertyNames.contains(propertyName) || raw.getPropertyValue(propertyName) == null)) {
				// Si la propriété obligatoire n'est pas renseignée alors erreur
				// Ou si la propriété obligatoire est renseignée mais qu'elle
				// est nulle alors erreur !
				unusedMandatoryPropertySet.add(propertyName);
			}
		}
		if (!unusedMandatoryPropertySet.isEmpty()) {
			throw new IllegalStateException(raw.getKey() + " Il existe des propriétés obligatoires non renseignées " + unusedMandatoryPropertySet);
		}
	}
}
