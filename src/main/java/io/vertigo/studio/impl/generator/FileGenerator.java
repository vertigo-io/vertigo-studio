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
package io.vertigo.studio.impl.generator;

import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorResultBuilder;

/**
 * Interface des generateurs de fichier.
 *
 * @author dchallas
 */
public interface FileGenerator {
	/**
	 * Génèration d'un fichier.
	 * Si le fichier existe déjà, il est regénéré
	 *
	 * @param generatorResultBuilder Builder
	 */
	void generateFile(final GeneratorResultBuilder generatorResultBuilder);

	/**
	 * Static method factory for FileGeneratorBuilder
	 * @param generatorConfig the config of the file generator
	 * @return FileGeneratorBuilder
	 */
	static FileGeneratorBuilder builder(final GeneratorConfig generatorConfig) {
		return new FileGeneratorBuilder(generatorConfig);
	}
}
