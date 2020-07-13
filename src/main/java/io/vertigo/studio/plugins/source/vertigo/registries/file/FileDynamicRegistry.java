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
package io.vertigo.studio.plugins.source.vertigo.registries.file;

import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.notebook.file.FileInfoSketch;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslDefinition;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;

/**
 * @author pchretien, mlaroche
 */
public final class FileDynamicRegistry implements DynamicRegistry {

	@Override
	public DslGrammar getGrammar() {
		return new FileGrammar();
	}

	/** {@inheritDoc} */
	@Override
	public SketchSupplier supplyModel(final DslDefinition dslDefinition) {
		final DslEntity dslEntity = dslDefinition.getEntity();
		if (FileGrammar.FILE_INFO_DEFINITION_ENTITY.equals(dslEntity)) {
			//Seuls les taches sont gérées.
			return workbook -> createFileDefinition(dslDefinition);
		}
		throw new IllegalStateException("The type of definition" + dslDefinition + " is not managed by me");
	}

	private static FileInfoSketch createFileDefinition(final DslDefinition xFileDefinition) {
		final String fileDefinitionName = "St" + xFileDefinition.getName();
		final String storeName = (String) xFileDefinition.getPropertyValue(KspProperty.DATA_SPACE);

		return new FileInfoSketch(fileDefinitionName, storeName);
	}

}
