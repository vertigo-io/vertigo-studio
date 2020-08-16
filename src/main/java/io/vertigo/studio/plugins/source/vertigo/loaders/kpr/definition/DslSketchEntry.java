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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr.definition;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketch;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketchKey;

/**
 * Une entrée de type définition est définie (XOR)
 * - soit par un champ et une définition,
 * - soit par un champ et une liste de clés de définition.
 *
 * @author pchretien, mlaroche
 */
public final class DslSketchEntry {
	/**
	 * Obligatoire
	 */
	private final String fieldName;
	private final DslSketch dslSketch;
	private final List<DslSketchKey> sketchKeys;

	/**
	 * Constructor.
	 *
	 * @param definitionNames Liste des clés de définition
	 * @param fieldName Nom du champ
	 */
	public DslSketchEntry(final String fieldName, final List<DslSketchKey> sketchKeys) {
		Assertion.check()
				.isNotNull(fieldName)
				.isNotNull(sketchKeys);
		//-----
		this.fieldName = fieldName;
		dslSketch = null;
		this.sketchKeys = sketchKeys;
	}

	/**
	 * Constructor.
	 *
	 * @param fieldName Nom du champ
	 * @param dslSketch Définition
	 */
	public DslSketchEntry(final String fieldName, final DslSketch dslSketch) {
		Assertion.check()
				.isNotNull(fieldName)
				.isNotNull(dslSketch);
		//-----
		this.fieldName = fieldName;
		this.dslSketch = dslSketch;
		sketchKeys = null;
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
	public boolean containsSketch() {
		return dslSketch != null;
	}

	/**
	 * @return Définition
	 */
	public DslSketch getSketch() {
		Assertion.check().isNotNull(dslSketch);
		//-----
		return dslSketch;
	}

	/**
	 * @return List des clés de définition
	 */
	public List<DslSketchKey> getSketchKeys() {
		return sketchKeys;
	}
}
