/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.dao;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Generated;
import io.vertigo.core.node.Node;
import io.vertigo.datamodel.task.TaskManager;
import io.vertigo.datamodel.task.definitions.TaskDefinition;
import io.vertigo.datamodel.task.model.Task;
import io.vertigo.datamodel.task.model.TaskBuilder;
import io.vertigo.datastore.impl.dao.StoreServices;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
 @Generated
public final class DaoPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public DaoPAO(final TaskManager taskManager) {
		Assertion.check().isNotNull(taskManager);
		//-----
		this.taskManager = taskManager;
	}

	/**
	 * Creates a taskBuilder.
	 * @param name  the name of the task
	 * @return the builder 
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Node.getNode().getDefinitionSpace().resolve(name, TaskDefinition.class);
		return Task.builder(taskDefinition);
	}

	/**
	 * Execute la tache TkOneListParamSelect.
	 * @param input List de Integer
	 * @return Integer result
	*/
	@io.vertigo.datamodel.task.proxy.TaskAnnotation(
			name = "TkOneListParamSelect",
			request = "select 1 where 1 in  (#input.rownum#)",
			taskEngineClass = io.vertigo.basics.task.TaskEngineSelect.class)
	@io.vertigo.datamodel.task.proxy.TaskOutput(smartType = "STyInteger")
	public Integer oneListParamSelect(@io.vertigo.datamodel.task.proxy.TaskInput(name = "input", smartType = "STyInteger") final java.util.List<Integer> input) {
		final Task task = createTaskBuilder("TkOneListParamSelect")
				.addValue("input", input)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TkOneParamSelect.
	 * @param input Integer
	 * @return Integer result
	*/
	@io.vertigo.datamodel.task.proxy.TaskAnnotation(
			name = "TkOneParamSelect",
			request = "select 1 where 1 = #input#",
			taskEngineClass = io.vertigo.basics.task.TaskEngineSelect.class)
	@io.vertigo.datamodel.task.proxy.TaskOutput(smartType = "STyInteger")
	public Integer oneParamSelect(@io.vertigo.datamodel.task.proxy.TaskInput(name = "input", smartType = "STyInteger") final Integer input) {
		final Task task = createTaskBuilder("TkOneParamSelect")
				.addValue("input", input)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TkPing.
	 * @return Option de Integer result
	*/
	@io.vertigo.datamodel.task.proxy.TaskAnnotation(
			name = "TkPing",
			request = "select 1",
			taskEngineClass = io.vertigo.basics.task.TaskEngineSelect.class)
	@io.vertigo.datamodel.task.proxy.TaskOutput(smartType = "STyInteger")
	public Optional<Integer> ping() {
		final Task task = createTaskBuilder("TkPing")
				.build();
		return Optional.ofNullable((Integer) getTaskManager()
				.execute(task)
				.getResult());
	}

	private TaskManager getTaskManager() {
		return taskManager;
	}
}
