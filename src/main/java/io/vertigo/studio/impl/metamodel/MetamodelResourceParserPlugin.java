package io.vertigo.studio.impl.metamodel;

import java.util.List;
import java.util.Set;

import io.vertigo.core.node.component.Plugin;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.studio.metamodel.MetamodelResource;

public interface MetamodelResourceParserPlugin extends Plugin {

	Set<String> getHandledResourceTypes();

	List<DefinitionSupplier> parseResources(List<MetamodelResource> resources, DefinitionSpace definitionSpace);

}
