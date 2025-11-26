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
package io.vertigo.vortex.model;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import io.vertigo.core.lang.Assertion;

/**
 * Gestion de la flexibilité structurelle du modèle.
 * Permet d'ajouter des propriétés sur les concepts structurels.
 *
 * @author pchretien
 */
public final class VxProperties {
	private final Map<VxProperty<?>, Object> properties;

	VxProperties(final Map<VxProperty<?>, Object> properties) {
		Assertion.check().isNotNull(properties);
		//-----
		this.properties = properties.isEmpty()
				? Collections.emptyMap()
				: Map.copyOf(properties);
	}

	/**
	 * Set des propriétés gérées.
	 * @return Collection
	 */
	public Set<VxProperty<?>> getProperties() {
		return properties.keySet();
	}

	/**
	 * Retourne la valeur d'une (méta) propriété liée au domaine, champ, dtDéfinition...
	 * null si cette propriété n'existe pas
	 * @param property Propriété
	 * @return valeur de la propriété
	 */
	public <T> T getValue(final VxProperty<T> property) {
		Assertion.check().isNotNull(property);
		//On ne vérifie rien sur le type retourné par le getter.
		//le type a été validé lors du put.
		//-----
		//Conformémément au contrat, on retourne null si pas de propriété trouvée
		return property.type().cast(properties.get(property));
	}
}
