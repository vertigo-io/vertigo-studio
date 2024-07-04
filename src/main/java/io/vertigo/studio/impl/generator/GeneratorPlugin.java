/*
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2024, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.impl.generator;

import io.vertigo.core.node.component.Plugin;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.notebook.Notebook;

/**
 * Plugin de génération de fichiers.
 *
 * @author dchallas
 */
public interface GeneratorPlugin extends Plugin {
	/**
	 * Génération d'un fichier à partir d'une source et de paramètres.
	 * @param generatorConfig general configuration of the generator
	 * @param generatorResultBuilder Builder
	 */
	void generate(final Notebook notebook, final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder);

	void clean(final GeneratorConfig generatorConfig, final GeneratorResultBuilder generatorResultBuilder);

	String getOutputType();

}
