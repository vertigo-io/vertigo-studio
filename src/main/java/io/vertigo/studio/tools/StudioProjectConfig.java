package io.vertigo.studio.tools;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.metamodel.MetamodelResource;

final class StudioProjectConfig {
	private final List<MetamodelResource> metamodelResources;
	private final MdaConfig mdaConfig;

	StudioProjectConfig(final List<MetamodelResource> metamodelResources, final MdaConfig mdaConfig) {
		Assertion.check()
				.isNotNull(metamodelResources)
				.isNotNull(mdaConfig);
		//---
		this.metamodelResources = metamodelResources;
		this.mdaConfig = mdaConfig;
	}

	List<MetamodelResource> getMetamodelResources() {
		return metamodelResources;
	}

	MdaConfig getMdaConfig() {
		return mdaConfig;
	}
}
