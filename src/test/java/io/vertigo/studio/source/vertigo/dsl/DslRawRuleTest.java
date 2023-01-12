/**
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
package io.vertigo.studio.source.vertigo.dsl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslRawRule;

public class DslRawRuleTest {
	private final DslRawRepository rawRepository = DslSketchFactoryMock.createDslSketchesRepository();

	@Test
	public void test2() throws PegNoMatchFoundException {
		final DslRaw raw = new DslRawRule("create", rawRepository.getGrammar())
				.parse("create Domain DoCodePostal { dataType : String } ")
				.getValue();
		Assertions.assertNotNull(raw);
	}

	@Test
	public void testTemplate() throws PegNoMatchFoundException {
		new DslRawRule("alter", rawRepository.getGrammar())
				.parse("alter DtDefinition DtTag {\r\n" +
						"	sortField : \"label\"\r\n" +
						" 	displayField : \"label\"\r\n" +
						"}");
	}
}
