package io.vertigo.studio.impl.metamodel;

import java.util.List;

import io.vertigo.core.node.component.Plugin;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;

public interface MetamodelResourceParserPlugin extends Plugin {

	List<String> getHandledResourceTypes();

	List<DefinitionSupplier> parseResources(List<MetamodelResource> resources, MetamodelRepository metamodelRepository);

}
