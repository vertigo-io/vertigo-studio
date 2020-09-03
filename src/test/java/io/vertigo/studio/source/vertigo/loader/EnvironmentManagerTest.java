/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.source.vertigo.loader;

import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.AGE;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.CITY;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.FIRST_NAME;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.HEIGHT;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.MAIN_ADDRESS;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.MALE;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.NAME;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.POSTAL_CODE;
import static io.vertigo.studio.source.vertigo.loader.PersonGrammar.STREET;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.vertigo.studio.impl.source.dsl.raw.DslRaw;
import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;
import io.vertigo.studio.notebook.Notebook;

public final class EnvironmentManagerTest {
	private final DslRawRepository rawRepository = DslSketchFactoryMock.createDRawRepository();

	@Test
	public void simpleTest() {
		final Notebook notebook = new Notebook();

		final DslRaw address1Definition = DslRaw.builder("MockMainAddress", PersonGrammar.ADDRESS_ENTITY)
				.withPackageName("io.vertigo.test.model")
				.addPropertyValue(STREET, "1, rue du louvre")
				.addPropertyValue(POSTAL_CODE, "75008")
				.addPropertyValue(CITY, "Paris")
				.build();
		rawRepository.addRaw(address1Definition);

		final DslRaw address2Definition = DslRaw.builder("MockSecondAddress", PersonGrammar.ADDRESS_ENTITY)
				.withPackageName("io.vertigo.test.model")
				.addPropertyValue(STREET, "105, rue martin")
				.addPropertyValue(POSTAL_CODE, "75008")
				.addPropertyValue(CITY, "Paris CEDEX")
				.build();
		rawRepository.addRaw(address2Definition);

		final DslRaw personDefinition = DslRaw.builder("MockMisterBean", PersonGrammar.PERSON_ENTITY)
				.withPackageName("io.vertigo.test.model")
				.addPropertyValue(NAME, "105, rue martin")
				.addPropertyValue(FIRST_NAME, "75008")
				.addPropertyValue(AGE, 42)
				.addPropertyValue(HEIGHT, 175.0d)
				.addPropertyValue(MALE, Boolean.TRUE)
				.addRawLink(MAIN_ADDRESS, "MockMainAddress")
				.addRawLink(PersonGrammar.SECOND_ADDRESS, "MockSecondAddress")
				.build();
		rawRepository.addRaw(personDefinition);

		rawRepository.solve(notebook);
		assertNotNull(personDefinition);
	}

	@Test
	public void badTypeTest() {
		Assertions.assertThrows(ClassCastException.class,
				() -> {
					final DslRaw address1Definition = DslRaw.builder("MockMainAddress", PersonGrammar.ADDRESS_ENTITY)
							.withPackageName("io.vertigo.test.model")
							.addPropertyValue(STREET, "1, rue du louvre")
							.addPropertyValue(POSTAL_CODE, 75008)
							.addPropertyValue(CITY, "Paris")
							.build();
					rawRepository.addRaw(address1Definition);
				});

	}
}
