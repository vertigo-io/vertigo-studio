package io.vertigo.studio.plugins.metamodel.vertigo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.definition.DefinitionSupplier;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.studio.impl.metamodel.MetamodelResourceParserPlugin;
import io.vertigo.studio.metamodel.MetamodelRepository;
import io.vertigo.studio.metamodel.MetamodelResource;
import io.vertigo.studio.metamodel.authorization.SecuredFeature;

public class AccountJsonSecurityResourceParserPlugin implements MetamodelResourceParserPlugin {

	private static final Gson gson = new Gson();

	private final ResourceManager resourceManager;

	@Inject
	public AccountJsonSecurityResourceParserPlugin(
			final ResourceManager resourceManager) {
		Assertion.checkNotNull(resourceManager);
		//---
		this.resourceManager = resourceManager;
	}

	@Override
	public List<DefinitionSupplier> parseResources(final List<MetamodelResource> resources, final MetamodelRepository metamodelRepository) {
		return resources.stream()
				.flatMap(metamodelResource -> parseJson(resourceManager.resolve(metamodelResource.getPath())).stream())
				.map(webserviceDefinition -> (DefinitionSupplier) dS -> webserviceDefinition)
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getHandledResourceTypes() {
		return Collections.singletonList("security");
	}

	final List<SecuredFeature> parseJson(final URL url) {
		final List<SecuredFeature> securedFeatures = new ArrayList<>();
		final JsonObject rootJsonObject;
		try (InputStream inputStream = url.openStream()) {
			rootJsonObject = gson.fromJson(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8), JsonObject.class);
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
		rootJsonObject.getAsJsonArray("globalAuthorizations").forEach(global -> securedFeatures.add(
				new SecuredFeature(
						global.getAsJsonObject().get("name").getAsString(),
						global.getAsJsonObject().get("label").getAsString(),
						extractComment(global.getAsJsonObject()),
						Optional.empty())));

		rootJsonObject.getAsJsonArray("securedEntities").forEach(entity -> {
			final String entityName = entity.getAsJsonObject().get("entity").getAsString();
			entity.getAsJsonObject().get("operations").getAsJsonArray().forEach(operation -> securedFeatures.add(new SecuredFeature(
					operation.getAsJsonObject().get("name").getAsString(),
					operation.getAsJsonObject().get("label").getAsString(),
					extractComment(operation.getAsJsonObject()),
					Optional.of(entityName))));
		});

		return securedFeatures;

	}

	private static final Optional<String> extractComment(final JsonObject jsonObject) {
		if (jsonObject.has("__comment")) {
			return Optional.of(jsonObject.get("__comment").getAsString());
		}
		return Optional.empty();
	}

}
