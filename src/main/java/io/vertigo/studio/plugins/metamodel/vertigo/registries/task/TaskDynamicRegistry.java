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
package io.vertigo.studio.plugins.metamodel.vertigo.registries.task;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.studio.metamodel.domain.Domain;
import io.vertigo.studio.metamodel.task.StudioTaskDefinition;
import io.vertigo.studio.metamodel.task.StudioTaskDefinitionBuilder;
import io.vertigo.studio.plugins.metamodel.vertigo.KspProperty;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.dynamic.DslDefinition;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.entity.DslGrammar;

/**
 * @author pchretien, mlaroche
 */
public final class TaskDynamicRegistry implements DynamicRegistry {

	@Override
	public DslGrammar getGrammar() {
		return new TaskGrammar();
	}

	/** {@inheritDoc} */
	@Override
	public DefinitionSupplier supplyDefinition(final DslDefinition dslDefinition) {
		final DslEntity dslEntity = dslDefinition.getEntity();

		if (TaskGrammar.TASK_DEFINITION_ENTITY.equals(dslEntity)) {
			//Only taskDefinitions are concerned
			return definitionSpace -> createTaskDefinition(definitionSpace, dslDefinition);
		}
		throw new IllegalStateException("The type of definition" + dslDefinition + " is not managed by me");
	}

	private static String getTaskEngineClassName(final DslDefinition xtaskDefinition) {
		return (String) xtaskDefinition.getPropertyValue(KspProperty.CLASS_NAME);
	}

	private static StudioTaskDefinition createTaskDefinition(final DefinitionSpace definitionSpace, final DslDefinition xtaskDefinition) {
		final String taskDefinitionName = xtaskDefinition.getName();
		final String request = (String) xtaskDefinition.getPropertyValue(KspProperty.REQUEST);
		Assertion.check().notNull(taskDefinitionName);
		final String taskEngineClassName = getTaskEngineClassName(xtaskDefinition);
		final String dataSpace = (String) xtaskDefinition.getPropertyValue(KspProperty.DATA_SPACE);
		final StudioTaskDefinitionBuilder taskDefinitionBuilder = StudioTaskDefinition.builder("St" + taskDefinitionName)
				.withEngine(taskEngineClassName)
				.withDataSpace(dataSpace)
				.withRequest(request)
				.withPackageName(xtaskDefinition.getPackageName());
		for (final DslDefinition xtaskAttribute : xtaskDefinition.getChildDefinitions(TaskGrammar.TASK_ATTRIBUTE)) {
			final String attributeName = xtaskAttribute.getName();
			Assertion.check().notNull(attributeName);
			final String smartTypeName = xtaskAttribute.getDefinitionLinkName("domain");
			final Domain domain = definitionSpace.resolve(smartTypeName, Domain.class);
			//-----
			final Cardinality cardinality = Cardinality.fromSymbol((String) xtaskAttribute.getPropertyValue(KspProperty.CARDINALITY));
			if (isInValue((String) xtaskAttribute.getPropertyValue(KspProperty.IN_OUT))) {
				taskDefinitionBuilder.addInAttribute(attributeName, domain, cardinality);
			} else {
				taskDefinitionBuilder.withOutAttribute(attributeName, domain, cardinality);
			}
		}
		return taskDefinitionBuilder.build();
	}

	private static boolean isInValue(final String sText) {
		switch (sText) {
			case "in":
				return true;
			case "out":
				return false;
			default:
				throw new IllegalArgumentException("les seuls types autorises sont 'in' ou 'out' et non > " + sText);
		}
	}
}
