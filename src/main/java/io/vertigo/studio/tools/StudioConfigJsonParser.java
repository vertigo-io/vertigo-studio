package io.vertigo.studio.tools;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaConfigBuilder;
import io.vertigo.studio.metamodel.MetamodelResource;

public class StudioConfigJsonParser {

	private static final Gson GSON = new Gson();

	public static final StudioProjectConfig parseJson(final String json) {
		final JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);

		//metamodelresources
		final List<MetamodelResource> metamodelResources = StreamSupport.stream(jsonObject.getAsJsonArray("metamodelResources").spliterator(), false)
				.map(jsonElement -> new MetamodelResource(jsonElement.getAsJsonObject().get("type").getAsString(), jsonElement.getAsJsonObject().get("path").getAsString()))
				.collect(Collectors.toList());

		//mdaCondig
		final JsonObject mdaConfigAsJson = jsonObject.getAsJsonObject("mdaConfig");
		Assertion.checkState(mdaConfigAsJson.has("projectPackageName"), "A 'projectPackageName' is required in mdaConfig");
		final MdaConfigBuilder mdaConfigBuilder = MdaConfig.builder(mdaConfigAsJson.get("projectPackageName").getAsString());
		if (mdaConfigAsJson.has("encoding")) {
			mdaConfigBuilder.withEncoding(mdaConfigAsJson.get("encoding").getAsString());
		}
		if (mdaConfigAsJson.has("targetGenDir")) {
			mdaConfigBuilder.withTargetGenDir(mdaConfigAsJson.get("targetGenDir").getAsString());
		}
		mdaConfigAsJson.getAsJsonObject("properties").entrySet().forEach(entry -> mdaConfigBuilder.addProperty(entry.getKey(), entry.getValue().getAsString()));

		return new StudioProjectConfig(metamodelResources, mdaConfigBuilder.build());
	}

}
