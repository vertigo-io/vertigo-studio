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
package io.vertigo.studio.dao.car.carDAOTest;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.vertigo.studio.source.vertigo.data.tasktest.DaoTestClass;

public class SelectCarByIdsTest extends DaoTestClass {
	
	@Inject
	io.vertigo.studio.dao.car.CarDAO carDAO;

	@Test
	public void check_selectCarByIds_Ok(){		
		this.check().semantics(() -> carDAO.selectCarByIds(dum().dumDtList(io.vertigo.studio.domain.car.Car.class)));
	}
}