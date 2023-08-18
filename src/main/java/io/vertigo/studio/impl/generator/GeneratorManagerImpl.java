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

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorManager;
import io.vertigo.studio.generator.GeneratorResult;
import io.vertigo.studio.generator.GeneratorResultBuilder;
import io.vertigo.studio.notebook.Notebook;

/**
 * Implements MDA.
 *
 * @author pchretien, mlaroche, dchallas
 */
public final class GeneratorManagerImpl implements GeneratorManager {
	private final List<GeneratorPlugin> generatorPlugins;

	/**
	 * Constructor.
	 * @param generatorPlugins
	 */
	@Inject
	public GeneratorManagerImpl(
			final List<GeneratorPlugin> generatorPlugins) {
		Assertion.check().isNotNull(generatorPlugins);
		//-----
		this.generatorPlugins = java.util.Collections.unmodifiableList(generatorPlugins);
	}

	/** {@inheritDoc} */
	@Override
	public GeneratorResult generate(final Notebook notebook, final GeneratorConfig generatorConfig) {
		//Création d'un objet listant les résultats
		final GeneratorResultBuilder generatorResultBuilder = GeneratorResult.builder();
		//Génèration des objets issus de la modélisation
		for (final GeneratorPlugin generatorPlugin : generatorPlugins) {
			if (generatorConfig.getOrDefaultAsBoolean(generatorPlugin.getOutputType(), false)) {
				generatorPlugin.generate(notebook, generatorConfig, generatorResultBuilder);
			}
		}
		return generatorResultBuilder.build();
	}

	@Override
	public GeneratorResult clean(final GeneratorConfig generatorConfig) {
		final File directory = new File(generatorConfig.getTargetGenDir());
		Assertion.check()
				.isTrue(directory.exists(), "targetGenDir {0} must exist", generatorConfig.getTargetGenDir())
				.isTrue(directory.isDirectory(), "targetGenDir {0} must be a directory", generatorConfig.getTargetGenDir());
		//---
		// We want to final clean the directory
		final GeneratorResultBuilder generatorResultBuilder = GeneratorResult.builder();
		for (final GeneratorPlugin generatorPlugin : generatorPlugins) {
			if (generatorConfig.getOrDefaultAsBoolean(generatorPlugin.getOutputType(), false)) {
				generatorPlugin.clean(generatorConfig, generatorResultBuilder);
			}
		}
		return generatorResultBuilder.build();
	}

}
