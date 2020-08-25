package io.vertigo.studio.notebook;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.source.Source;

public final class NotebookConfig {
	private final List<Source> sources;
	private final MdaConfig mdaConfig;

	public NotebookConfig(final List<Source> sources, final MdaConfig mdaConfig) {
		Assertion.check()
				.isNotNull(sources)
				.isNotNull(mdaConfig);
		//---
		this.sources = sources;
		this.mdaConfig = mdaConfig;
	}

	public List<Source> getMetamodelResources() {
		return sources;
	}

	public MdaConfig getMdaConfig() {
		return mdaConfig;
	}
}
