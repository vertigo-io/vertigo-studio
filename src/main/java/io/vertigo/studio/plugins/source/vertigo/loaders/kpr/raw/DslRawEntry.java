/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawKey;

/**
 * Une entrée de type définition est définie (XOR)
 * - soit par un champ et une définition,
 * - soit par un champ et une liste de clés de définition.
 *
 * @author pchretien, mlaroche
 */
public final class DslRawEntry {
	/**
	 * Obligatoire
	 */
	private final String fieldName;
	private final DslRaw raw;
	private final List<DslRawKey> rawKeys;

	/**
	 * Constructor.
	 *
	 * @param definitionNames Liste des clés de définition
	 * @param fieldName Nom du champ
	 */
	public DslRawEntry(final String fieldName, final List<DslRawKey> sketchKeys) {
		Assertion.check()
				.isNotNull(fieldName)
				.isNotNull(sketchKeys);
		//-----
		this.fieldName = fieldName;
		raw = null;
		this.rawKeys = sketchKeys;
	}

	/**
	 * Constructor.
	 *
	 * @param fieldName Nom du champ
	 * @param dslSketch Définition
	 */
	public DslRawEntry(final String fieldName, final DslRaw raw) {
		Assertion.check()
				.isNotNull(fieldName)
				.isNotNull(raw);
		//-----
		this.fieldName = fieldName;
		this.raw = raw;
		rawKeys = null;
	}

	/**
	 * @return Nom du champ
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * Permet de savoir dans quel mode on se trouve (Definition XOR List<DefinitionKey>).
	 *
	 * @return boolean
	 */
	public boolean containsRaw() {
		return raw != null;
	}

	/**
	 * @return Définition
	 */
	public DslRaw getRaw() {
		Assertion.check().isNotNull(raw);
		//-----
		return raw;
	}

	/**
	 * @return List des clés de définition
	 */
	public List<DslRawKey> getRawKeys() {
		Assertion.check().isNull(raw);
		//---
		return rawKeys;
	}
}
