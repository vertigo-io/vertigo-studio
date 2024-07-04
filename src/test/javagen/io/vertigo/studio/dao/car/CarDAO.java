/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.dao.car;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.core.lang.Generated;
import io.vertigo.core.node.Node;
import io.vertigo.datamodel.smarttype.SmartTypeManager;
import io.vertigo.datamodel.task.TaskManager;
import io.vertigo.datamodel.task.definitions.TaskDefinition;
import io.vertigo.datamodel.task.model.Task;
import io.vertigo.datamodel.task.model.TaskBuilder;
import io.vertigo.datastore.entitystore.EntityStoreManager;
import io.vertigo.datastore.impl.dao.DAO;
import io.vertigo.datastore.impl.dao.StoreServices;
import io.vertigo.studio.domain.car.Car;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
@Generated
public final class CarDAO extends DAO<Car, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param entityStoreManager Manager de persistance
	 * @param taskManager Manager de Task
	 * @param smartTypeManager SmartTypeManager
	 */
	@Inject
	public CarDAO(final EntityStoreManager entityStoreManager, final TaskManager taskManager, final SmartTypeManager smartTypeManager) {
		super(Car.class, entityStoreManager, taskManager, smartTypeManager);
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
	 * Execute la tache TkGetFirstCar.
	 * @return Option de Car dtoCar
	*/
	@io.vertigo.datamodel.task.proxy.TaskAnnotation(
			name = "TkGetFirstCar",
			request = "select * from car\n" +
					" 				limit 1",
			taskEngineClass = io.vertigo.basics.task.TaskEngineSelect.class)
	@io.vertigo.datamodel.task.proxy.TaskOutput(smartType = "STyDtCar")
	public Optional<io.vertigo.studio.domain.car.Car> getFirstCar() {
		final Task task = createTaskBuilder("TkGetFirstCar")
				.build();
		return Optional.ofNullable((io.vertigo.studio.domain.car.Car) getTaskManager()
				.execute(task)
				.getResult());
	}

	/**
	 * Execute la tache TkSelectCarByIds.
	 * @param input DtList de Car
	 * @return DtList de Car dtoCar
	*/
	@io.vertigo.datamodel.task.proxy.TaskAnnotation(
			name = "TkSelectCarByIds",
			request = "select * from car where id in (#input.rownum.id#)",
			taskEngineClass = io.vertigo.basics.task.TaskEngineSelect.class)
	@io.vertigo.datamodel.task.proxy.TaskOutput(smartType = "STyDtCar")
	public io.vertigo.datamodel.data.model.DtList<io.vertigo.studio.domain.car.Car> selectCarByIds(@io.vertigo.datamodel.task.proxy.TaskInput(name = "input", smartType = "STyDtCar") final io.vertigo.datamodel.data.model.DtList<io.vertigo.studio.domain.car.Car> input) {
		final Task task = createTaskBuilder("TkSelectCarByIds")
				.addValue("input", input)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
