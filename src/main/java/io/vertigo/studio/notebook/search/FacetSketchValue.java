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
package io.vertigo.studio.notebook.search;

import java.io.Serializable;

import io.vertigo.core.lang.Assertion;

/**
 * Valeur de facette relatif à une définition.
 * Les valeurs sont
 *  - soit déclarée.
 *  - soit déduite.
 * Exemple :
 *  - Fourchettes de prix (valeurs déclarées)
 *  - Fourchettes de dates (valeurs déclarées)
 *  - Termes les plus usités (valeurs déduites)
 *  - Clustering sémantique (valeurs déduites)
 * Fait partie du métamodèle lorsque la facette est déclarée par ses ranges.
 * Fait partie du modèle lorsque les valeurs sont déduites.
 *
 * @author pchretien, mlaroche
 */
public record FacetSketchValue(
		String code, //the code of the facet
		String listFilter,
		/**
		 * This label must be human readable
		 * 'small files' can be preferred to an expression.
		 */
		String label) implements Serializable {

	public FacetSketchValue {
		Assertion.check()
				.isNotBlank(code)
				.isNotNull(listFilter)
				.isNotNull(label);
	}
}
