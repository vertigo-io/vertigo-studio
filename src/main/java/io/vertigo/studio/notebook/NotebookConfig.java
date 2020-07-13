package io.vertigo.studio.notebook;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.source.NotebookSource;

public final class NotebookConfig {
	private final List<NotebookSource> notebookSources;
	private final MdaConfig mdaConfig;

	public NotebookConfig(final List<NotebookSource> notebookSources, final MdaConfig mdaConfig) {
		Assertion.check()
				.isNotNull(notebookSources)
				.isNotNull(mdaConfig);
		//---
		this.notebookSources = notebookSources;
		this.mdaConfig = mdaConfig;
	}

	public List<NotebookSource> getMetamodelResources() {
		return notebookSources;
	}

	public MdaConfig getMdaConfig() {
		return mdaConfig;
	}
}
