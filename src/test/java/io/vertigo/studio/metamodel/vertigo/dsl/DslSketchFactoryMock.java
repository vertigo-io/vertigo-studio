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
package io.vertigo.studio.metamodel.vertigo.dsl;

import java.util.List;

import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslRaw;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslRawRepository;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslSketchFactory;
import io.vertigo.studio.plugins.source.vertigo.registries.domain.DomainGrammar;

/**
 * Mock pour les tests de regles sur les Definitions.
 * @author npiedeloup
 */
public final class DslSketchFactoryMock implements DslSketchFactory {

	private DslSketchFactoryMock() {
		//constructeur private
	}

	/**
	 * @return DynamicDefinitionRepository bouchon pour test
	 */
	public static DslRawRepository createDslSketchesRepository() {
		return new DslRawRepository(new DslSketchFactoryMock());
	}

	@Override
	public DslGrammar getGrammar() {
		return new DomainGrammar();
	}

	@Override
	public List<Sketch> createSketches(final Notebook notebook, final DslRaw raw) {
		return List.of(new Sketch() {
			@Override
			public SketchKey getKey() {
				return SketchKey.of("FAKE");
			}

			@Override
			public String getLocalName() {
				return "FAKE";
			}
		});
	}

}
