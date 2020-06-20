package io.vertigo.studio.metamodel;

import java.util.List;

import io.vertigo.core.node.component.Manager;
import io.vertigo.core.node.definition.DefinitionSpace;

public interface StudioMetamodelManager extends Manager {

	DefinitionSpace parseResources(List<MetamodelResource> resources);

}
