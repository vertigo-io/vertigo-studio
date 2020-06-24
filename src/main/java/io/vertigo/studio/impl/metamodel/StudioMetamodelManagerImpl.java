package io.vertigo.studio.impl.metamodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.StudioMetamodelManager;

public class StudioMetamodelManagerImpl implements StudioMetamodelManager {

	private final Map<String, MetamodelResourceParserPlugin> metamodelResourceParserPluginsByType = new HashMap<>();

	@Inject
	public StudioMetamodelManagerImpl(
			final List<MetamodelResourceParserPlugin> metamodelResourceParserPlugins) {
		Assertion.check().isNotNull(metamodelResourceParserPlugins);
		//---
		for (final MetamodelResourceParserPlugin metamodelResourceParserPlugin : metamodelResourceParserPlugins) {
			for (final String resourceType : metamodelResourceParserPlugin.getHandledResourceTypes()) {
				Assertion.check().isTrue(!metamodelResourceParserPluginsByType.containsKey(resourceType), "Only one plugin can manage the ResourceType {0}",
						resourceType);
				metamodelResourceParserPluginsByType.put(resourceType, metamodelResourceParserPlugin);
			}
		}
	}

	@Override
	public MetamodelRepository parseResources(final List<MetamodelResource> resources) {
		final Map<MetamodelResourceParserPlugin, List<MetamodelResource>> resourcesByPlugin = resources.stream()
				.collect(Collectors.groupingBy(resource -> metamodelResourceParserPluginsByType.get(resource.getType())));

		final MetamodelRepository metamodelRepository = new MetamodelRepository();
		resourcesByPlugin.entrySet().stream()
				.flatMap(entry -> entry.getKey().parseResources(entry.getValue(), metamodelRepository).stream())
				.map(definitionSupplier -> definitionSupplier.get(metamodelRepository))
				.forEach(metamodelRepository::registerDefinition);
		return metamodelRepository;
	}

}
