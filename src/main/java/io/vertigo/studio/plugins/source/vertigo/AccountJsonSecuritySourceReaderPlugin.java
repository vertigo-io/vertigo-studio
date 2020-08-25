package io.vertigo.studio.plugins.source.vertigo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.studio.impl.source.SourceReaderPlugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.authorization.SecuredFeatureSketch;
import io.vertigo.studio.source.Source;

public final class AccountJsonSecuritySourceReaderPlugin implements SourceReaderPlugin {

	private static final Gson gson = new Gson();

	private final ResourceManager resourceManager;

	@Inject
	public AccountJsonSecuritySourceReaderPlugin(final ResourceManager resourceManager) {
		Assertion.check().isNotNull(resourceManager);
		//---
		this.resourceManager = resourceManager;
	}

	@Override
	public Stream<SecuredFeatureSketch> parseResources(final List<Source> sources, final Notebook notebook) {
		Assertion.check()
				.isNotNull(sources)
				.isNotNull(notebook);
		//---	
		return sources
				.stream()
				.flatMap(notebookSource -> parseJson(resourceManager.resolve(notebookSource.getPath())).stream());
	}

	@Override
	public Set<String> getHandledSourceTypes() {
		return Set.of("security");
	}

	final List<SecuredFeatureSketch> parseJson(final URL url) {
		final List<SecuredFeatureSketch> securedFeatureSketchs = new ArrayList<>();
		final JsonObject rootJsonObject;
		try (InputStream inputStream = url.openStream()) {
			rootJsonObject = gson.fromJson(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8), JsonObject.class);
		} catch (final IOException e) {
			throw WrappedException.wrap(e);
		}
		rootJsonObject.getAsJsonArray("globalAuthorizations").forEach(global -> securedFeatureSketchs.add(
				new SecuredFeatureSketch(
						global.getAsJsonObject().get("name").getAsString(),
						global.getAsJsonObject().get("label").getAsString(),
						extractComment(global.getAsJsonObject()),
						Optional.empty())));

		rootJsonObject.getAsJsonArray("securedEntities").forEach(entity -> {
			final String entityName = entity.getAsJsonObject().get("entity").getAsString();
			entity.getAsJsonObject().get("operations").getAsJsonArray().forEach(operation -> securedFeatureSketchs.add(new SecuredFeatureSketch(
					operation.getAsJsonObject().get("name").getAsString(),
					operation.getAsJsonObject().get("label").getAsString(),
					extractComment(operation.getAsJsonObject()),
					Optional.of(entityName))));
		});

		return securedFeatureSketchs;

	}

	private static final Optional<String> extractComment(final JsonObject jsonObject) {
		if (jsonObject.has("__comment")) {
			return Optional.of(jsonObject.get("__comment").getAsString());
		}
		return Optional.empty();
	}

}
