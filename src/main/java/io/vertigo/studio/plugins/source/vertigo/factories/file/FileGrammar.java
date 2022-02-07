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
package io.vertigo.studio.plugins.source.vertigo.factories.file;

import java.util.Collections;
import java.util.List;

import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.impl.source.dsl.entity.DslPropertyType;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;

/**
 * @author npiedeloup
 */
final class FileGrammar implements DslGrammar {

	/**Définition de tache.*/
	public static final DslEntity FILE_INFO_ENTITY;

	static {
		FILE_INFO_ENTITY = DslEntity.builder("FileInfo")
				.addRequiredField(KspProperty.DATA_SPACE, DslPropertyType.String)
				.build();
	}

	@Override
	public List<DslEntity> getEntities() {
		return Collections.singletonList(FILE_INFO_ENTITY);
	}
}
