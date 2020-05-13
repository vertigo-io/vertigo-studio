package io.vertigo.studio.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaConfigBuilder;
import io.vertigo.studio.metamodel.MetamodelResource;

public class StudioConfigJsonParser {

	private static final Gson GSON = new Gson();

	public static final StudioProjectConfig parseJson(final URL configUrl) {
		try {
			final JsonObject jsonObject = GSON.fromJson(readFile(configUrl), JsonObject.class);
			final String rootPath = Path.of(configUrl.toURI()).getParent().toString() + "/";

			//metamodelresources
			final List<MetamodelResource> metamodelResources = StreamSupport.stream(jsonObject.getAsJsonArray("metamodelResources").spliterator(), false)
					.map(jsonElement -> new MetamodelResource(jsonElement.getAsJsonObject().get("type").getAsString(), rootPath + jsonElement.getAsJsonObject().get("path").getAsString()))
					.collect(Collectors.toList());

			//mdaCondig
			final JsonObject mdaConfigAsJson = jsonObject.getAsJsonObject("mdaConfig");
			Assertion.checkState(mdaConfigAsJson.has("projectPackageName"), "A 'projectPackageName' is required in mdaConfig");
			final MdaConfigBuilder mdaConfigBuilder = MdaConfig.builder(mdaConfigAsJson.get("projectPackageName").getAsString());
			if (mdaConfigAsJson.has("encoding")) {
				mdaConfigBuilder.withEncoding(mdaConfigAsJson.get("encoding").getAsString());
			}
			if (mdaConfigAsJson.has("targetGenDir")) {
				mdaConfigBuilder.withTargetGenDir(rootPath + mdaConfigAsJson.get("targetGenDir").getAsString());
			}
			mdaConfigAsJson.getAsJsonObject("properties").entrySet().forEach(entry -> mdaConfigBuilder.addProperty(entry.getKey(), entry.getValue().getAsString()));

			return new StudioProjectConfig(metamodelResources, mdaConfigBuilder.build());
		} catch (final IOException | URISyntaxException e) {
			throw WrappedException.wrap(e);
		}
	}

	private static String readFile(final URL url) throws IOException {
		try (final BufferedReader reader = new BufferedReader(
				new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
			final StringBuilder buff = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				buff.append(line);
				line = reader.readLine();
				buff.append("\r\n");
			}
			return buff.toString();
		}
	}

}
