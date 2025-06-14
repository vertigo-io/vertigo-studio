/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2025, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo.loaders;

import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;

/**
 * Chargeur de l'environnement.
 * @author pchretien, mlaroche
 */
public interface Loader {
	/**
	 * Type parsed by loader.
	 * Examples : oom, kpr, eaxmi...
	 * @return Type parsed by loader
	 */
	String getType();

	/**
	 * Parsing des définitions pour un fichier (oom, kpr ou ksp)
	 * défini par une url (sur système de fichier ou classpath)
	 * et selon la grammaire en argument.
	 * @param resourcePath resourcePath
	 * @param rawRepository dslDefinitionRepository
	 */
	void load(String resourcePath, DslRawRepository rawRepository);

}
