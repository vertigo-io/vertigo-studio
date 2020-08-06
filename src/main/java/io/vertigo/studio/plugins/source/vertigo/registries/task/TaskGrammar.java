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
package io.vertigo.studio.plugins.source.vertigo.registries.task;

import static io.vertigo.studio.plugins.source.vertigo.KspProperty.CARDINALITY;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.CLASS_NAME;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.DATA_SPACE;
import static io.vertigo.studio.plugins.source.vertigo.KspProperty.REQUEST;
import static io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslPropertyType.String;

import java.util.List;

import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;
import io.vertigo.studio.plugins.source.vertigo.registries.domain.DomainGrammar;

/**
 * @author pchretien, mlaroche
 */
final class TaskGrammar implements DslGrammar {
	/** Attribute name. */
	public static final String TASK_ATTRIBUTE_IN = "in";
	public static final String TASK_ATTRIBUTE_OUT = "out";

	/**Définition de tache.*/
	public static final DslEntity TASK_DEFINITION_ENTITY;

	static {
		final DslEntity taskAttributeInEntity = DslEntity.builder(TASK_ATTRIBUTE_IN)
				.addRequiredField(CARDINALITY, String)
				.addRequiredField("domain", DomainGrammar.DOMAIN_ENTITY.getLink())
				.build();
		final DslEntity taskAttributeOutEntity = DslEntity.builder(TASK_ATTRIBUTE_OUT)
				.addRequiredField(CARDINALITY, String)
				.addRequiredField("domain", DomainGrammar.DOMAIN_ENTITY.getLink())
				.build();

		TASK_DEFINITION_ENTITY = DslEntity.builder("Task")
				.addRequiredField(REQUEST, String)
				.addOptionalField(DATA_SPACE, String)
				.addRequiredField(CLASS_NAME, String)
				.addManyFields(TASK_ATTRIBUTE_IN, taskAttributeInEntity)
				.addOptionalField(TASK_ATTRIBUTE_OUT, taskAttributeOutEntity)
				.build();
	}

	@Override
	public List<DslEntity> getEntities() {
		return List.of(TASK_DEFINITION_ENTITY);
	}
}
