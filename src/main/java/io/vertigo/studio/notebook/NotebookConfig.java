/**
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
package io.vertigo.studio.notebook;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.source.Source;

public final class NotebookConfig {
	private final List<Source> sources;
	private final GeneratorConfig generatorConfig;

	public NotebookConfig(final List<Source> sources, final GeneratorConfig generatorConfig) {
		Assertion.check()
				.isNotNull(sources)
				.isNotNull(generatorConfig);
		//---
		this.sources = sources;
		this.generatorConfig = generatorConfig;
	}

	public List<Source> getMetamodelResources() {
		return sources;
	}

	public GeneratorConfig getMdaConfig() {
		return generatorConfig;
	}
}
