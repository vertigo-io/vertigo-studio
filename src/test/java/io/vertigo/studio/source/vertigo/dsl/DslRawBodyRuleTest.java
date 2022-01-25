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
package io.vertigo.studio.source.vertigo.dsl;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.studio.impl.source.dsl.entity.DslEntity;
import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.raw.DslRawBody;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslRawBodyRule;

public class DslRawBodyRuleTest {
	private final DslRawRepository rawRepository = DslSketchFactoryMock.createDslSketchesRepository();

	private static DslEntity find(final List<DslEntity> entities, final String entityName) {
		return entities
				.stream()
				.filter(entity -> entity.getName().equals(entityName))
				.findFirst()
				.orElseThrow(() -> new VSystemException("not found {0}", entityName));
	}

	@Test
	public void test2() throws PegNoMatchFoundException {
		final List<DslEntity> entities = rawRepository.getGrammar().getEntities();
		final DslEntity entity = find(entities, "Domain");

		final DslRawBody dslSketchBody = new DslRawBodyRule(entity)
				.parse("{ dataType : String } ")
				.getValue();

		Assertions.assertNotNull(dslSketchBody);
	}

	@Test
	public void testError() {
		final List<DslEntity> entities = rawRepository.getGrammar().getEntities();
		final DslEntity entity = find(entities, "Domain");
		final String testValue = "{ dataType : String,  maxLengh:\"true\"   } ";
		try {
			new DslRawBodyRule(entity)
					.parse(testValue);
			Assertions.fail();
		} catch (final PegNoMatchFoundException e) {
			//System.out.println(e.getFullMessage());
			Assertions.assertEquals(testValue.indexOf("maxLengh") + "maxLengh".length() - 1, e.getIndex());
		}
	}
}
