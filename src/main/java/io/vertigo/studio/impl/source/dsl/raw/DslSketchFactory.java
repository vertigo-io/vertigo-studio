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
package io.vertigo.studio.impl.source.dsl.raw;

import java.util.List;

import io.vertigo.studio.impl.source.dsl.entity.DslGrammar;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;

/**
 * This handler creates
 * - creates a definition from a DslDefinition
 * - adds dslDefinition from a new DslDefinition
 *
 * example : Each time a DtDefinition, two others definitions (domains)  are created (a domain for one object, a domain for a list).
 * @author pchretien, mlaroche
 */
public interface DslSketchFactory {
	/**
	 * @return Grammar
	 */
	DslGrammar getGrammar();

	/**
	 * Create a definition from a dynamic definition in a context defined by definitionSpace (preexisting definitions).
	 * @param raw Definition
	 * @return An optional definition
	 */
	List<Sketch> createSketches(Notebook notebook, DslRaw raw);

	/**
	 * Ajout d'une définition.
	 * Utilisé pour créer des définitions Ã  partir d'autres Definitions.
	 * Exemple : création des domaines à partir d'un DT.
	 *
	 * @param raw dslDefinition
	 * @return a list of dslDefinitions
	 */
	default List<DslRaw> onNewRaw(final DslRaw raw) {
		return List.of();
	}
}
