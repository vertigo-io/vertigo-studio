/*
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
package io.vertigo.studio.generator;

import java.io.PrintStream;

import io.vertigo.core.util.StringUtil;

/**
 * Résultat de la génération.
 *
 * @author pchretien, mlaroche
 */
public record GeneratorResult(
		int createdFiles,
		int updatedFiles,
		int errorFiles,
		int identicalFiles,
		int deletedFiles,
		long durationMillis) {

	/**
	 * Static method factory for GeneratorResultBuilder
	 * @return GeneratorResultBuilder
	 */
	public static GeneratorResultBuilder builder() {
		return new GeneratorResultBuilder();
	}

	/**
	 * Affichage du résultat de la génération dans la console.
	 */
	public void displayResultMessage(final PrintStream out) {
		out.append(getResultMessage());
	}

	/**
	 * Affichage du résultat de la génération dans la console.
	 */
	public String getResultMessage() {
		return StringUtil.format("\nCréation de {0} fichiers, Mise à jour de {1} fichiers, {2} fichiers identiques et {3} problemes en {4} ms",
				createdFiles, updatedFiles, identicalFiles, errorFiles, durationMillis);
	}
}
