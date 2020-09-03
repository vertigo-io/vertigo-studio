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
package io.vertigo.studio.plugins.generator.vertigo.domain.js.model;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;

/**
 * Model used by FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class JSDtModel {
	private final DtSketch dtSketch;
	private final List<JSStudioDtFieldModel> dtFieldModels;

	/**
	 * Constructeur.
	 *
	 * @param dtSketch DtDefinition de l'objet à générer
	 */
	public JSDtModel(final DtSketch dtSketch) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;

		dtFieldModels = dtSketch.getFields()
				.stream()
				.filter(dtField -> DtSketchField.FieldType.COMPUTED != dtField.getType())
				.map(JSStudioDtFieldModel::new)
				.collect(Collectors.toList());
	}

	/**
	 * @return DT définition
	 */
	public DtSketch getDtDefinition() {
		return dtSketch;
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe d'implémentation du DtObject
	 */
	public String getClassSimpleName() {
		return dtSketch.getClassSimpleName();
	}

	/**
	 * @return Liste de champs
	 */
	public List<JSStudioDtFieldModel> getFields() {
		return dtFieldModels;
	}
}
