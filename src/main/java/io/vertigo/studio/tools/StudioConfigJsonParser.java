package io.vertigo.studio.tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.FileUtil;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaConfigBuilder;
import io.vertigo.studio.metamodel.MetamodelResource;

final class StudioConfigJsonParser {
	private static final String PATH = "path";
	private static final String TYPE = "type";
	private static final String METAMODEL_RESOURCES = "metamodelResources";
	private static final String MDA_CONFIG = "mdaConfig";
	private static final String PROJECT_PACKAGE_NAME = "projectPackageName";
	private static final String PROPERTIES = "properties";
	private static final String ENCODING = "encoding";
	private static final String TARGET_GEN_DIR = "targetGenDir";
	private static final Gson GSON = new Gson();

	static final StudioProjectConfig parseJson(final URL configUrl) throws IOException, URISyntaxException {
		final JsonObject jsonObject = GSON.fromJson(FileUtil.read(configUrl), JsonObject.class);
		final String rootPath = Path.of(configUrl.toURI()).getParent().toString() + "/";

		//metamodelresources
		final List<MetamodelResource> metamodelResources = parseMetamodelResource(jsonObject, rootPath);
		//mdaCondig
		final MdaConfig mdaConfig = parseMdaConfig(jsonObject, rootPath);
		//---
		return new StudioProjectConfig(metamodelResources, mdaConfig);
	}

	private static final List<MetamodelResource> parseMetamodelResource(JsonObject jsonObject, String rootPath) {
		//metamodelresources
		return StreamSupport.stream(jsonObject.getAsJsonArray(METAMODEL_RESOURCES).spliterator(), false)
				.map(jsonElement -> MetamodelResource.of(jsonElement.getAsJsonObject().get(TYPE).getAsString(), rootPath + jsonElement.getAsJsonObject().get(PATH).getAsString()))
				.collect(Collectors.toList());
	}

	private static final MdaConfig parseMdaConfig(JsonObject jsonObject, String rootPath) {
		//mdaCondig
		final JsonObject mdaConfigAsJson = jsonObject.getAsJsonObject(MDA_CONFIG);
		Assertion.check().isTrue(mdaConfigAsJson.has(PROJECT_PACKAGE_NAME), "A 'projectPackageName' is required in mdaConfig");
		final MdaConfigBuilder mdaConfigBuilder = MdaConfig.builder(mdaConfigAsJson.get(PROJECT_PACKAGE_NAME).getAsString());
		if (mdaConfigAsJson.has(ENCODING)) {
			mdaConfigBuilder.withEncoding(mdaConfigAsJson.get(ENCODING).getAsString());
		}
		if (mdaConfigAsJson.has(TARGET_GEN_DIR)) {
			mdaConfigBuilder.withTargetGenDir(rootPath + mdaConfigAsJson.get(TARGET_GEN_DIR).getAsString());
		}
		mdaConfigAsJson.getAsJsonObject(PROPERTIES).entrySet().forEach(entry -> mdaConfigBuilder.addProperty(entry.getKey(), entry.getValue().getAsString()));

		return mdaConfigBuilder.build();
	}

}
