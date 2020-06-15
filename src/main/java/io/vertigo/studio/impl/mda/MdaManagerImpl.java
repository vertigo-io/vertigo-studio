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
package io.vertigo.studio.impl.mda;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.mda.MdaResult;
import io.vertigo.studio.mda.MdaResultBuilder;
import io.vertigo.studio.metamodel.MetamodelRepository;

/**
 * Implements MDA.
 *
 * @author pchretien, mlaroche, dchallas
 */
public final class MdaManagerImpl implements MdaManager {
	private final List<GeneratorPlugin> generatorPlugins;

	/**
	 * Constructor.
	 * @param generatorPlugins
	 */
	@Inject
	public MdaManagerImpl(
			final List<GeneratorPlugin> generatorPlugins) {
		Assertion.check().notNull(generatorPlugins);
		//-----
		this.generatorPlugins = java.util.Collections.unmodifiableList(generatorPlugins);
	}

	/** {@inheritDoc} */
	@Override
	public MdaResult generate(final MetamodelRepository metamodelRepository, final MdaConfig mdaConfig) {
		//Création d'un objet listant les résultats
		final MdaResultBuilder mdaResultBuilder = MdaResult.builder();
		//Génèration des objets issus de la modélisation
		for (final GeneratorPlugin generatorPlugin : generatorPlugins) {
			if (mdaConfig.getOrDefaultAsBoolean(generatorPlugin.getOutputType(), false)) {
				generatorPlugin.generate(metamodelRepository, mdaConfig, mdaResultBuilder);
			}
		}
		return mdaResultBuilder.build();
	}

	@Override
	public MdaResult clean(final MdaConfig mdaConfig) {
		final File directory = new File(mdaConfig.getTargetGenDir());
		Assertion.checkArgument(directory.exists(), "targetGenDir must exist");
		Assertion.checkArgument(directory.isDirectory(), "targetGenDir must be a directory");
		//---
		// We want to final clean the directory
		final MdaResultBuilder mdaResultBuilder = MdaResult.builder();
		for (final GeneratorPlugin generatorPlugin : generatorPlugins) {
			if (mdaConfig.getOrDefaultAsBoolean(generatorPlugin.getOutputType(), false)) {
				generatorPlugin.clean(mdaConfig, mdaResultBuilder);
			}
		}
		return mdaResultBuilder.build();
	}

}
