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

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.notebook.task.TaskSketchBuilder;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketch;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;

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
	public SketchSupplier supplyModel(final DslSketch dslDefinition) {
		final DslEntity dslEntity = dslDefinition.getEntity();

		if (TaskGrammar.TASK_DEFINITION_ENTITY.equals(dslEntity)) {
			//Only taskDefinitions are concerned
			return notebook -> createTaskSketch(notebook, dslDefinition);
		}
		throw new IllegalStateException("The type of definition" + dslDefinition + " is not managed by me");
	}

	private static String getTaskEngineClassName(final DslSketch xtaskDefinition) {
		return (String) xtaskDefinition.getPropertyValue(KspProperty.CLASS_NAME);
	}

	private static TaskSketch createTaskSketch(final Notebook notebook, final DslSketch xtaskDefinition) {
		final String taskDefinitionName = xtaskDefinition.getName();
		final String request = (String) xtaskDefinition.getPropertyValue(KspProperty.REQUEST);
		Assertion.check().isNotNull(taskDefinitionName);
		final String taskEngineClassName = getTaskEngineClassName(xtaskDefinition);
		final String dataSpace = (String) xtaskDefinition.getPropertyValue(KspProperty.DATA_SPACE);
		final TaskSketchBuilder taskSketchBuilder = TaskSketch.builder(taskDefinitionName)
				.withEngine(taskEngineClassName)
				.withDataSpace(dataSpace)
				.withRequest(request)
				.withPackageName(xtaskDefinition.getPackageName());
		for (final DslSketch xtaskAttribute : xtaskDefinition.getChildSketches(TaskGrammar.TASK_ATTRIBUTE_IN)) {
			final String attributeName = xtaskAttribute.getName();
			Assertion.check().isNotNull(attributeName);
			//-----
			taskSketchBuilder.addInAttribute(attributeName,
					buildDomainSketch(notebook, xtaskAttribute),
					buildCardinality(xtaskAttribute));
		}
		for (final DslSketch xtaskAttribute : xtaskDefinition.getChildSketches(TaskGrammar.TASK_ATTRIBUTE_OUT)) {
			final String attributeName = xtaskAttribute.getName();
			Assertion.check().isNotNull(attributeName);
			//-----
			taskSketchBuilder.withOutAttribute(attributeName,
					buildDomainSketch(notebook, xtaskAttribute),
					buildCardinality(xtaskAttribute));
		}
		return taskSketchBuilder.build();
	}

	private static Cardinality buildCardinality(final DslSketch xtaskAttribute) {
		return Cardinality.fromSymbol((String) xtaskAttribute.getPropertyValue(KspProperty.CARDINALITY));
	}

	private static DomainSketch buildDomainSketch(final Notebook notebook, final DslSketch xtaskAttribute) {
		final String smartTypeName = xtaskAttribute.getDefinitionLinkName("domain");
		final DomainSketch domainSketch = notebook.resolve(SketchKey.of(smartTypeName), DomainSketch.class);
		return domainSketch;
	}

}
