package io.vertigo.studio.plugins.metamodel.vertigo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.config.DefinitionResourceConfig;
import io.vertigo.core.node.definition.Definition;
import io.vertigo.core.node.definition.DefinitionSpace;
import io.vertigo.core.node.definition.SimpleDefinitionProvider;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.studio.metamodel.authorization.SecuredFeature;

public class AccountJsonSecurityDefinitionProvider implements SimpleDefinitionProvider {

	private static final Gson gson = new Gson();

	private final ResourceManager resourceManager;
	private final List<DefinitionResourceConfig> definitionResourceConfigs = new ArrayList<>();

	@Inject
	public AccountJsonSecurityDefinitionProvider(
			final ResourceManager resourceManager) {
		Assertion.checkNotNull(resourceManager);
		//---
		this.resourceManager = resourceManager;
	}

	@Override
	public List<? extends Definition> provideDefinitions(final DefinitionSpace definitionSpace) {
		return definitionResourceConfigs.stream()
				.flatMap(resourceConfig -> parseJson(resourceManager.resolve(resourceConfig.getPath())).stream())
				.collect(Collectors.toList());
	}

	@Override
	public void addDefinitionResourceConfig(final DefinitionResourceConfig definitionResourceConfig) {
		Assertion.checkArgument("security".equals(definitionResourceConfig.getType()), "This DefinitionProvider Support only 'webservice' type (not {0})", definitionResourceConfig.getType());
		//-----
		definitionResourceConfigs.add(definitionResourceConfig);
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
