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
package io.vertigo.studio.plugins.mda.vertigo.domain.sql.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.BasicType;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.DtSketchField;

/**
 * Model used by FreeMarker.
 *
 * @author pchretien, mlaroche
 */
public final class SqlStudioDtDefinitionModel {
	private final DtSketch dtSketch;
	private final boolean hasSequence;
	private final List<SqlStudioDtFieldModel> dtFieldModels;

	/**
	 * Constructeur.
	 *
	 * @param dtSketch DtSketch de l'objet à générer
	 */
	public SqlStudioDtDefinitionModel(final DtSketch dtSketch) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;

		final Optional<DtSketchField> pkFieldOpt = dtSketch.getIdField();
		if (pkFieldOpt.isPresent()) {
			final BasicType pkDataType = pkFieldOpt.get().getDomain().getDataType();
			hasSequence = pkDataType.isNumber();
		} else {
			hasSequence = false;
		}

		dtFieldModels = dtSketch.getFields()
				.stream()
				.filter(dtField -> DtSketchField.FieldType.COMPUTED != dtField.getType())
				.map(SqlStudioDtFieldModel::new)
				.collect(Collectors.toList());
	}

	public String getLocalName() {
		return StringUtil.camelToConstCase(dtSketch.getLocalName());
	}

	/**
	 * @return Si l'on doit générer une sequence
	 */
	public boolean hasSequence() {
		return hasSequence;
	}

	/**
	 * @return Liste de champs
	 */
	public List<SqlStudioDtFieldModel> getFields() {
		return dtFieldModels;
	}

}
