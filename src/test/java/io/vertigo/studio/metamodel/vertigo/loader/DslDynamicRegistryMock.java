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

import java.util.Collections;
import java.util.List;

import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;
import io.vertigo.studio.notebook.SketchSupplier;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketch;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DslSketchesRepository;
import io.vertigo.studio.plugins.source.vertigo.dsl.dynamic.DynamicRegistry;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;

/**
 * Mock pour les tests de regles sur les Definitions.
 * @author npiedeloup
 */
public final class DslDynamicRegistryMock implements DynamicRegistry {

	private DslDynamicRegistryMock() {
		//constructeur private
	}

	/**
	 * @return DynamicDefinitionRepository bouchon pour test
	 */
	public static DslSketchesRepository createDslSketchesRepository() {
		return new DslSketchesRepository(new DslDynamicRegistryMock());
	}

	@Override
	public DslGrammar getGrammar() {
		return new PersonGrammar();
	}

	@Override
	public List<SketchSupplier> supplyModels(final DslSketch definition) {
		return Collections.singletonList((notebook) -> new FakeModel(definition.getKey().getName()));
	}

	@SkecthPrefix(FakeModel.PREFIX)
	public final static class FakeModel extends AbstractSketch {
		public static final String PREFIX = "Mock";

		FakeModel(final String name) {
			super(name);
		}
	}
}
