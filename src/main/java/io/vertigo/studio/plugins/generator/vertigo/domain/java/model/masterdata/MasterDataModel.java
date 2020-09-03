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
package io.vertigo.studio.plugins.generator.vertigo.domain.java.model.masterdata;

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
public final class MasterDataModel {
	private final DtSketch dtSketch;
	private final List<MasterDataValueModel> masterDataValueModels;

	public MasterDataModel(final DtSketch dtSketch, final Map<String, MasterDataValue> masterDataValuesByDtDefinition) {
		Assertion.check().isNotNull(dtSketch);
		//-----
		this.dtSketch = dtSketch;
		masterDataValueModels = masterDataValuesByDtDefinition
				.entrySet()
				.stream()
				.map(entry -> new MasterDataValueModel(dtSketch, entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	/**
	 * @return Nom de la class en CamelCase
	 */
	public String getClassSimpleName() {
		return dtSketch.getClassSimpleName();
	}

	/**
	 * @return Nom de la class en CamelCase
	 */
	public String getClassName() {
		return dtSketch.getClassCanonicalName();
	}

	public String getPackageName() {
		return dtSketch.getPackageName();
	}

	public List<MasterDataValueModel> getEnumValues() {
		return masterDataValueModels;
	}

}
