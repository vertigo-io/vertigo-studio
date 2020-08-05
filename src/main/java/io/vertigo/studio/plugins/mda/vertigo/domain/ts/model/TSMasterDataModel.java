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
package io.vertigo.studio.plugins.mda.vertigo.domain.ts.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.notebook.domain.masterdata.MasterDataValue;

/**
 * Model TS des materdata.
 *
 * @author npiedeloup
 */
public final class TSMasterDataModel {
	private final TSStudioDtModel tsDtModel;
	private final List<TSMasterDataValueModel> tsMasterDataValueModels;

	public TSMasterDataModel(final DtSketch dtSketch, final Map<String, MasterDataValue> masterDataValuesByDtDefinition) {
		Assertion.check()
				.isNotNull(dtSketch)
				.isNotNull(masterDataValuesByDtDefinition);
		//-----
		tsDtModel = new TSStudioDtModel(dtSketch);
		tsMasterDataValueModels = masterDataValuesByDtDefinition
				.entrySet()
				.stream()
				.map(entry -> new TSMasterDataValueModel(dtSketch, entry.getValue()))
				.collect(Collectors.toList());
	}

	public TSStudioDtModel getDefinition() {
		return tsDtModel;
	}

	public String getIdFieldName() {
		return tsDtModel.getDtDefinition().getIdField().get().getName();
	}

	public List<TSMasterDataValueModel> getValues() {
		return tsMasterDataValueModels;
	}

}
