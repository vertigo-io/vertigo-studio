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
package io.vertigo.studio.plugins.source.vertigo.factories.task;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Cardinality;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawKey;
import io.vertigo.studio.impl.source.dsl.raw.DslSketchFactory;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.notebook.domain.DomainSketch;
import io.vertigo.studio.notebook.task.TaskSketch;
import io.vertigo.studio.notebook.task.TaskSketchBuilder;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;

/**
 * @author pchretien, mlaroche
 */
public final class TaskSketchFactory implements DslSketchFactory {

	@Override
	public DslGrammar getGrammar() {
		return new TaskGrammar();
	}

	/** {@inheritDoc} */
	@Override
	public List<Sketch> createSketches(final Notebook notebook, final DslRaw raw) {
		final DslEntity entity = raw.getEntity();

		if (TaskGrammar.TASK_ENTITY.equals(entity)) {
			//Only taskDefinitions are concerned
			return List.of(createTaskSketch(notebook, raw));
		}
		throw new IllegalStateException("his kind of raw " + entity + " is not managed by me");
	}

	private static String getTaskEngineClassName(final DslRaw xtaskDefinition) {
		return (String) xtaskDefinition.getPropertyValue(KspProperty.CLASS_NAME);
	}

	private static TaskSketch createTaskSketch(final Notebook notebook, final DslRaw taskRaw) {
		final DslRawKey taskRawKey = taskRaw.getKey();
		final String request = (String) taskRaw.getPropertyValue(KspProperty.REQUEST);
		Assertion.check().isNotNull(taskRawKey);
		final String taskEngineClassName = getTaskEngineClassName(taskRaw);
		final String dataSpace = (String) taskRaw.getPropertyValue(KspProperty.DATA_SPACE);
		final TaskSketchBuilder taskSketchBuilder = TaskSketch.builder(taskRawKey.getName())
				.withEngine(taskEngineClassName)
				.withDataSpace(dataSpace)
				.withRequest(request)
				.withPackageName(taskRaw.getPackageName());
		for (final DslRaw taskAttributeRaw : taskRaw.getSubRaws(TaskGrammar.TASK_ATTRIBUTE_IN)) {
			final String attributeName = taskAttributeRaw.getKey().getName();
			taskSketchBuilder.addInAttribute(attributeName,
					buildDomainSketch(notebook, taskAttributeRaw),
					buildCardinality(taskAttributeRaw));
		}
		for (final DslRaw taskAttributeRaw : taskRaw.getSubRaws(TaskGrammar.TASK_ATTRIBUTE_OUT)) {
			final String attributeName = taskAttributeRaw.getKey().getName();
			taskSketchBuilder.withOutAttribute(attributeName,
					buildDomainSketch(notebook, taskAttributeRaw),
					buildCardinality(taskAttributeRaw));
		}
		return taskSketchBuilder.build();
	}

	private static Cardinality buildCardinality(final DslRaw xtaskAttribute) {
		return Cardinality.fromSymbol((String) xtaskAttribute.getPropertyValue(KspProperty.CARDINALITY));
	}

	private static DomainSketch buildDomainSketch(final Notebook notebook, final DslRaw taskAttributeRaw) {
		final DslRawKey smartTypeKey = taskAttributeRaw.getRawKeyByFieldName("domain");
		return notebook.resolve(smartTypeKey.getName(), DomainSketch.class);
	}

}
