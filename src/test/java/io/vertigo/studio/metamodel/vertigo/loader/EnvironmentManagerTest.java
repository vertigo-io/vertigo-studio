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
package io.vertigo.studio.metamodel.vertigo.loader;

import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.AGE;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.CITY;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.FIRST_NAME;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.HEIGHT;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.MAIN_ADDRESS;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.MALE;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.NAME;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.POSTAL_CODE;
import static io.vertigo.studio.metamodel.vertigo.loader.PersonGrammar.STREET;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.node.component.di.DIInjector;
import io.vertigo.core.node.config.LogConfig;
import io.vertigo.core.node.config.NodeConfig;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.dynamic.DslDefinition;
import io.vertigo.studio.plugins.metamodel.vertigo.dsl.dynamic.DslDefinitionRepository;

public final class EnvironmentManagerTest {
	private AutoCloseableApp app;

	@BeforeEach
	public final void setUp() {
		app = new AutoCloseableApp(buildNodeConfig());
		DIInjector.injectMembers(this, app.getComponentSpace());
	}

	@AfterEach
	public final void tearDown() {
		if (app != null) {
			app.close();
		}
	}

	private NodeConfig buildNodeConfig() {
		return NodeConfig.builder()
				.beginBoot().withLogConfig(new LogConfig("/log4j.xml")).endBoot()
				.build();
	}

	private final DslDefinitionRepository dslDefinitionRepository = DslDynamicRegistryMock.createDynamicDefinitionRepository();

	@Test
	public void simpleTest() {
		final MetamodelRepository metamodelRepository = new MetamodelRepository();

		final DslDefinition address1Definition = DslDefinition.builder("MockMainAddress", PersonGrammar.ADDRESS_ENTITY)
				.withPackageName("io.vertigo.test.model")
				.addPropertyValue(STREET, "1, rue du louvre")
				.addPropertyValue(POSTAL_CODE, "75008")
				.addPropertyValue(CITY, "Paris")
				.build();
		dslDefinitionRepository.addDefinition(address1Definition);

		final DslDefinition address2Definition = DslDefinition.builder("MockSecondAddress", PersonGrammar.ADDRESS_ENTITY)
				.withPackageName("io.vertigo.test.model")
				.addPropertyValue(STREET, "105, rue martin")
				.addPropertyValue(POSTAL_CODE, "75008")
				.addPropertyValue(CITY, "Paris CEDEX")
				.build();
		dslDefinitionRepository.addDefinition(address2Definition);

		final DslDefinition personDefinition = DslDefinition.builder("MockMisterBean", PersonGrammar.PERSON_ENTITY)
				.withPackageName("io.vertigo.test.model")
				.addPropertyValue(NAME, "105, rue martin")
				.addPropertyValue(FIRST_NAME, "75008")
				.addPropertyValue(AGE, 42)
				.addPropertyValue(HEIGHT, 175.0d)
				.addPropertyValue(MALE, Boolean.TRUE)
				.addDefinitionLink(MAIN_ADDRESS, "MockMainAddress")
				.addDefinitionLink(PersonGrammar.SECOND_ADDRESS, "MockSecondAddress")
				.build();
		dslDefinitionRepository.addDefinition(personDefinition);

		dslDefinitionRepository.solve(metamodelRepository);
		assertNotNull(personDefinition);
	}

	@Test
	public void badTypeTest() {
		Assertions.assertThrows(ClassCastException.class,
				() -> {
					final DslDefinition address1Definition = DslDefinition.builder("MockMainAddress", PersonGrammar.ADDRESS_ENTITY)
							.withPackageName("io.vertigo.test.model")
							.addPropertyValue(STREET, "1, rue du louvre")
							.addPropertyValue(POSTAL_CODE, 75008)
							.addPropertyValue(CITY, "Paris")
							.build();
					dslDefinitionRepository.addDefinition(address1Definition);
				});

	}
}