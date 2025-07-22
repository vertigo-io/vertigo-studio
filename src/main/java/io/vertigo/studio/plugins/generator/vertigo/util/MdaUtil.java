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
package io.vertigo.studio.plugins.generator.vertigo.util;

import java.io.File;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.generator.GeneratorResultBuilder;

public class MdaUtil {

	private MdaUtil() {
		// util
	}

	private static boolean deleteDirectory(final File directory, final GeneratorResultBuilder generatorResultBuilder) {
		deleteFiles(directory, generatorResultBuilder);
		return (directory.delete());
	}

	public static void deleteFiles(final File directory, final GeneratorResultBuilder generatorResultBuilder) {
		if (directory.exists()) {
			Assertion.check().isTrue(directory.isDirectory(), "targetGenDir must be a directory");
			for (final File file : directory.listFiles()) {
				if (file.isDirectory()) {
					deleteDirectory(file, generatorResultBuilder);
				} else {
					file.delete(); // we don't care about real deletion of the file
					generatorResultBuilder.incFileDeleted();
				}
			}
		}
	}

}
