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
