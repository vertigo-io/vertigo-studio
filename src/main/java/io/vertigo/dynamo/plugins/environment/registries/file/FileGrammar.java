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
package io.vertigo.dynamo.plugins.environment.registries.file;

import java.util.Collections;
import java.util.List;

import io.vertigo.dynamo.plugins.environment.KspProperty;
import io.vertigo.dynamo.plugins.environment.dsl.entity.DslEntity;
import io.vertigo.dynamo.plugins.environment.dsl.entity.DslGrammar;
import io.vertigo.dynamo.plugins.environment.dsl.entity.DslPropertyType;

/**
 * @author npiedeloup
 */
final class FileGrammar implements DslGrammar {

	/**Définition de tache.*/
	public static final DslEntity FILE_INFO_DEFINITION_ENTITY;

	static {
		FILE_INFO_DEFINITION_ENTITY = DslEntity.builder("FileInfo")
				.addRequiredField(KspProperty.DATA_SPACE, DslPropertyType.String)
				.build();
	}

	@Override
	public List<DslEntity> getEntities() {
		return Collections.singletonList(FILE_INFO_DEFINITION_ENTITY);
	}
}
