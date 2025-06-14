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
package io.vertigo.studio.plugins.generator.vertigo.search.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;

/**
 * Model used by FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class SearchDtModel {
	private final DtSketch dtSketch;

	/**
	 * Constructeur.
	 *
	 * @param dtSketch DtSketch de l'objet à générer
	 */
	public SearchDtModel(final DtSketch dtSketch) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;

	}

	/**
	 * @return Nom canonique (i.e. avec le package) de la classe d'implémentation du DtObject
	 */
	public String getClassCanonicalName() {
		return dtSketch.getClassCanonicalName();
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe d'implémentation du DtObject
	 */
	public String getClassSimpleName() {
		return dtSketch.getClassSimpleName();
	}

	/**
	 * Retourne le nom local de la definition (const case, sans prefix)
	 * @return Simple Nom (i.e. sans le package) de la definition du DtObject
	 */
	public String getLocalName() {
		return dtSketch.getLocalName();
	}

	/**
	 * Name of the DtObjet
	 * @return name of the DtObjet
	 */
	public String getDtName() {
		return "Dt" + dtSketch.getLocalName();
	}

	/**
	 * @return Nom du package
	 */
	public String getPackageName() {
		return dtSketch.getPackageName();
	}

}
