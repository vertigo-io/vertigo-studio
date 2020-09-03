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
package io.vertigo.studio.impl.source.dsl.raw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.studio.impl.source.dsl.entity.DslEntityField;
import io.vertigo.studio.notebook.Notebook;

/**
 * Solver permet de résoudre les références.
 * Les références peuvent être orphelines : la clé ne correspond à aucune définition.
 * Les références circulaires ne peuvent pas être résolues.
 * Le solver est une fonction stateless qui prend en entrée le Repository du Model et calcule en sortie la liste des définitions.
 *
 * @author  pchretien
 */
final class DslRawSolver {
	private DslRawSolver() {
		//private constructor
	}

	/**
	* Résoltuion des références.
	* On appelle SyntaxHandler dans le bon Ordre
	*/
	static List<DslRaw> solve(final Notebook notebook, final DslRawRepository rawRepository) {
		Assertion.check()
				.isNotNull(notebook)
				.isNotNull(rawRepository);
		//-----
		//Liste des clés résolues
		final List<DslRaw> sortedRaws = new ArrayList<>();

		final Collection<DslRawKey> orphans = rawRepository.getOrphanDefinitionKeys();
		if (!orphans.isEmpty()) {
			throw new VSystemException(" Les clés suivantes {0} sont orphelines", orphans);
		}
		//-----
		final Collection<DslRaw> raws = new ArrayList<>(rawRepository.getRaws());

		DslRaw raw;
		int size = raws.size();
		while (size > 0) {
			for (final Iterator<DslRaw> it = raws.iterator(); it.hasNext();) {
				raw = it.next();
				//==============================================================
				//==============================================================
				//On vérifie que les sous éléments sont résolues
				if (isSolved(notebook, rawRepository, sortedRaws, raw, raw)) {
					sortedRaws.add(raw);
					it.remove();
				}
			}
			//Si la liste n'a pas diminuée c'est que l'on a fini de résoudre ce qui peut l'être.
			if (size == raws.size()) {
				throw new VSystemException(" Les références {0} ne peuvent être résolues", raws);
			}
			size = raws.size();
		}
		return sortedRaws;
	}

	private static boolean isSolved(
			final Notebook notebook,
			final DslRawRepository rawRepository,
			final List<DslRaw> sortedRaws,
			final DslRaw raw,
			final DslRaw xdefRoot) {
		//A definition is solved if all its sub definitions have been solved

		//We check all references were known
		for (final DslEntityField entityField : raw.getAllRawLinkFields()) {
			final String fieldName = entityField.getName();
			for (final DslRawKey rawKey : raw.getRawKeysByFieldName(fieldName)) {
				//reference should be already solved in a previous resources module : then continue
				if (!notebook.contains(rawKey.getName())) {
					//or references should be in currently parsed resources
					if (!rawRepository.contains(rawKey)) {
						final DslRawKey xdefRootKey = xdefRoot.getKey().equals(raw.getKey()) ? xdefRoot.getKey()
								: DslRawKey.of((xdefRoot.getKey().getName() + "." + raw.getKey().getName()));
						throw new VSystemException("Clé {0} de type {1}, référencée par la propriété {2} de {3} non trouvée",
								rawKey, raw.getEntity().getField(fieldName).getType(), fieldName, xdefRootKey);
					}
					final DslRaw linkedRaw = rawRepository.getRaw(rawKey);
					if (!sortedRaws.contains(linkedRaw)) {
						return false;
					}
				}
			}
		}

		//On vérifie que les composites sont résolues.
		return raw.getAllSubRaws()
				.stream()
				.allMatch(child -> isSolved(notebook, rawRepository, sortedRaws, child, xdefRoot));
	}
}
