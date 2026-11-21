/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2026, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.generator.vertigo.task.test;

import io.vertigo.core.lang.VUserException;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.datamodel.smarttype.definitions.ConstraintException;

/**
 * Vérifieur d'objets d'accès aux données.
 * @author sezratty
 *
 */
public class TaskTestDaoChecker {

	/**
	 * Appelle une action de DAO.
	 * On ne vérifie que la sémantique : SQL valide et modèle valide.
	 * Les erreurs liées aux données ne font pas tomber le test en erreur.
	 * @param action Action appelant le DAO à tester.
	 */
	public void semantics(final Runnable action) {
		try {
			action.run();
		} catch (final VUserException vue) {
			// Erreur utilisateur liées aux données
			// On considère que le test est OK.
		} catch (final WrappedException e) {
			if (isConstraintCase(e)) {
				// Contrainte sur les données (ex : la requête ne retourne aucune ligne alors que le résultat est obligatoire) : on considère que le test est OK.
				return;
			}
			throw e;
		}
		// Autre erreur : elle fait tomber le test en erreur.
	}

	/** @return if a ConstraintException is the cause : the validation of the data (task params or result, see TaskResult) */
	private static boolean isConstraintCase(final WrappedException e) {
		for (Throwable cause = e.getCause(); cause != null; cause = cause.getCause()) {
			if (cause instanceof ConstraintException) {
				return true;
			}
		}
		return false;
	}
}
