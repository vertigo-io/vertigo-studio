package io.vertigo.studio.tools;

import java.util.List;

import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.metamodel.MetamodelResource;

public class StudioProjectConfig {

	private final List<MetamodelResource> metamodelResources;
	private final MdaConfig mdaConfig;

	public StudioProjectConfig(final List<MetamodelResource> metamodelResources, final MdaConfig mdaConfig) {
		this.metamodelResources = metamodelResources;
		this.mdaConfig = mdaConfig;
	}

	public List<MetamodelResource> getMetamodelResources() {
		return metamodelResources;
	}

	public MdaConfig getMdaConfig() {
		return mdaConfig;
	}

}
