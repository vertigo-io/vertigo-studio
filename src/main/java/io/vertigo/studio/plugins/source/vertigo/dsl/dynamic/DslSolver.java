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
package io.vertigo.studio.plugins.source.vertigo.dsl.dynamic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.SketchKey;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntityField;

/**
 * Solver permet de résoudre les références.
 * Les références peuvent être orphelines : la clé ne correspond à aucune définition.
 * Les références circulaires ne peuvent pas être résolues.
 * Le solver est une fonction stateless qui prend en entrée le Repository du Model et calcule en sortie la liste des définitions.
 *
 * @author  pchretien
 */
final class DslSolver {
	private DslSolver() {
		//private constructor
	}

	/**
	* Résoltuion des références.
	* On appelle SyntaxHandler dans le bon Ordre
	*/
	static List<DslDefinition> solve(final Notebook notebook, final DslDefinitionRepository definitionRepository) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(definitionRepository);
		//-----
		//Liste des clés résolues
		final List<DslDefinition> sortedList = new ArrayList<>();

		final Collection<String> orphans = definitionRepository.getOrphanDefinitionKeys();
		if (!orphans.isEmpty()) {
			throw new VSystemException(" Les clés suivantes {0} sont orphelines", orphans);
		}
		//-----
		final Collection<DslDefinition> coll = new ArrayList<>(definitionRepository.getDefinitions());

		DslDefinition dslDefinition;
		int size = coll.size();
		while (size > 0) {
			for (final Iterator<DslDefinition> it = coll.iterator(); it.hasNext();) {
				dslDefinition = it.next();
				//==============================================================
				//==============================================================
				//On vérifie que les sous éléments sont résolues
				if (isSolved(notebook, definitionRepository, sortedList, dslDefinition, dslDefinition)) {
					sortedList.add(dslDefinition);
					it.remove();
				}
			}
			//Si la liste n'a pas diminuée c'est que l'on a fini de résoudre ce qui peut l'être.
			if (size == coll.size()) {
				throw new VSystemException(" Les références {0} ne peuvent être résolues", coll);
			}
			size = coll.size();
		}
		return sortedList;
	}

	private static boolean isSolved(
			final Notebook notebook,
			final DslDefinitionRepository definitionRepository,
			final List<DslDefinition> orderedList,
			final DslDefinition dslDefinition,
			final DslDefinition xdefRoot) {
		//A definition is solved if all its sub definitions have been solved

		//We check all references were known
		for (final DslEntityField dslEntityField : dslDefinition.getAllDefinitionLinkFields()) {
			final String fieldName = dslEntityField.getName();
			for (final String definitionName : dslDefinition.getDefinitionLinkNames(fieldName)) {
				//reference should be already solved in a previous resources module : then continue
				if (!notebook.contains(SketchKey.of(definitionName))) {
					//or references should be in currently parsed resources
					if (!definitionRepository.containsDefinitionName(definitionName)) {
						final String xdefRootName = xdefRoot.getName().equals(dslDefinition.getName()) ? xdefRoot.getName() : (xdefRoot.getName() + "." + dslDefinition.getName());
						throw new VSystemException("Clé {0} de type {1}, référencée par la propriété {2} de {3} non trouvée",
								definitionName, dslDefinition.getEntity().getField(fieldName).getType(), fieldName, xdefRootName);
					}
					final DslDefinition linkedDefinition = definitionRepository.getDefinition(definitionName);
					if (!orderedList.contains(linkedDefinition)) {
						return false;
					}
				}
			}
		}

		//On vérifie que les composites sont résolues.
		return dslDefinition.getAllChildDefinitions()
				.stream()
				.allMatch(child -> isSolved(notebook, definitionRepository, orderedList, child, xdefRoot));
	}
}
