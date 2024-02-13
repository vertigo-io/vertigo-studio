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
package io.vertigo.studio.plugins.generator.vertigo.task.test;

import java.util.List;

import io.vertigo.datamodel.data.model.DataObject;
import io.vertigo.datamodel.data.model.DtList;

/**
 *
 * Dummy values generator
 * @author mlaroche
 *
 */
public interface TaskTestDummyGenerator {

	/**
	 * Creates a dummy value for the specified type
	 * @param type class of the wanted object
	 * @param <T> class of the wanted object
	 * @return dummy value
	 */
	<T> T dum(final Class<T> type);

	/**
	 * Creates a list of dummy values for the specified type
	 * @param clazz class of the wanted object
	 * @param <T> class of the wanted object
	 * @return dummy values as List
	 */
	<T> List<T> dumList(final Class<T> clazz);

	/**
	 * Creates a dtList of dummy values for the specified type
	 * @param dtoClass class of the wanted object
	 * @param <D> class of the wanted object
	 * @return dummy values as DtList
	 */
	<D extends DataObject> DtList<D> dumDtList(final Class<D> dtoClass);

	/**
	 * Creates a dummy dtObject for the specified type as new (no pk)
	 * @param dtoClass class of the wanted object
	 * @param <D> class of the wanted object
	 * @return dummy value
	 */
	<D extends DataObject> D dumNew(final Class<D> dtoClass);
}
