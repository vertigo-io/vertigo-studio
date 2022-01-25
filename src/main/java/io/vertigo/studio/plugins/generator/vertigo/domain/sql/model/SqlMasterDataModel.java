/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.domain.sql.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.masterdata.MasterDataValue;

/**
 * Model Sql des materdata.
 *
 * @author mlaroche
 */
public final class SqlMasterDataModel {
	private final SqlStudioDtDefinitionModel sqlDtDefinitionModel;
	private final List<SqlMasterDataValueModel> sqlMasterDataValueModels;

	public SqlMasterDataModel(final DtSketch dtSketch, final Map<String, MasterDataValue> masterDataValuesByDtSketch) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		sqlDtDefinitionModel = new SqlStudioDtDefinitionModel(dtSketch);
		sqlMasterDataValueModels = masterDataValuesByDtSketch
				.entrySet()
				.stream()
				.map(entry -> new SqlMasterDataValueModel(dtSketch, entry.getValue()))
				.collect(Collectors.toList());
	}

	/**
	 * @return Nom de la table
	 */
	public String getTableName() {
		return sqlDtDefinitionModel.getLocalName();
	}

	public SqlStudioDtDefinitionModel getDefinition() {
		return sqlDtDefinitionModel;
	}

	public List<SqlMasterDataValueModel> getValues() {
		return sqlMasterDataValueModels;
	}

}
