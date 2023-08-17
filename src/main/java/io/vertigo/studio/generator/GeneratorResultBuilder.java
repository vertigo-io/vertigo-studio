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

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.core.lang.Builder;

/**
 * Résultat de la génération.
 *
 * @author pchretien, mlaroche
 */
public final class GeneratorResultBuilder implements Builder<GeneratorResult> {
	private static final Logger LOGGER = LogManager.getLogger(GeneratorResultBuilder.class);

	/** Nombre de fichiers écrits . */
	private int updatedFiles;
	private int createdFiles;
	private int deletedFiles;
	/** Nombre de fichiers en erreurs. */
	private int errorFiles;
	/** Nombre de fichiers identiques. */
	private int identicalFiles;

	private final long start = System.currentTimeMillis();

	/**
	 * Constructor.
	 */
	GeneratorResultBuilder() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public GeneratorResult build() {
		final long duration = System.currentTimeMillis() - start;
		return new GeneratorResult(createdFiles, updatedFiles, errorFiles, identicalFiles, deletedFiles, duration);
	}

	/**
	 * Increments the number of deleted files
	 */
	public void incFileDeleted() {
		deletedFiles++;
	}

	/**
	 * Notification de la génération d'un fichier (écrit sur disque).
	 * @param file Fichier généré
	 */
	public void addCreatedFile(final File file) {
		createdFiles++;
		LOGGER.trace("file created : {}", file.getAbsolutePath());
	}

	/**
	 * Increments the number of files in error and logs the error
	 * @param file Java file of the error
	 */
	public void addErrorFile(final File file) {
		errorFiles++;
		//Ajout d'un fichier en erreur.
		LOGGER.trace("file error : {}", file.getAbsolutePath());
	}

	/**
	 * Increments the number of files updated and logs the error
	 * @param file Java file of the error
	 */
	public void addUpdatedFile(final File file) {
		updatedFiles++;
		LOGGER.trace("file updated : {}", file.getAbsolutePath());
	}

	/**
	 * Le fichier est identique
	 * @param file Fichier généré
	 */
	public void addIdenticalFile(final File file) {
		identicalFiles++;
		LOGGER.trace("Fichier identique : {}", file.getAbsolutePath());
	}
}
