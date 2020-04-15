package io.vertigo.studio.metamodel;

import java.util.List;

import io.vertigo.core.node.component.Manager;

public interface StudioMetamodelManager extends Manager {

	MetamodelRepository parseResources(List<MetamodelResource> resources);

}
