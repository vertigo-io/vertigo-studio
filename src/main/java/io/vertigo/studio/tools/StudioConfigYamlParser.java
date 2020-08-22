package io.vertigo.studio.tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.util.FileUtil;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.mda.MdaConfig;
import io.vertigo.studio.mda.MdaConfigBuilder;
import io.vertigo.studio.notebook.NotebookConfig;
import io.vertigo.studio.source.NotebookSource;
import io.vertigo.studio.tools.YamlStudioConfig.YamlMdaConfig;
import io.vertigo.studio.tools.YamlStudioConfig.YamlResourceConfig;

final class StudioConfigYamlParser {

	static final NotebookConfig parseYaml(final URL configUrl) throws IOException, URISyntaxException {
		final YamlStudioConfig yamlStudioConfig = new Yaml(new Constructor(YamlStudioConfig.class)).loadAs(FileUtil.read(configUrl), YamlStudioConfig.class);
		final String rootPath = Path.of(configUrl.toURI()).getParent().toString() + "/";

		//metamodelresources
		final List<NotebookSource> notebookSources = parseResources(yamlStudioConfig.resources, rootPath);
		//mdaCondig
		final MdaConfig mdaConfig = parseMdaConfig(yamlStudioConfig.mdaConfig, rootPath);
		//---
		return new NotebookConfig(notebookSources, mdaConfig);
	}

	private static final List<NotebookSource> parseResources(final List<YamlResourceConfig> resources, final String rootPath) {
		//metamodelresources
		return resources.stream()
				.map(resource -> NotebookSource.of(resource.type, rootPath + resource.path))
				.collect(Collectors.toList());
	}

	private static final MdaConfig parseMdaConfig(final YamlMdaConfig yamlMdaConfig, final String rootPath) {
		//mdaCondig
		Assertion.check().isNotNull(yamlMdaConfig.projectPackageName, "A 'projectPackageName' is required in mdaConfig");
		final MdaConfigBuilder mdaConfigBuilder = MdaConfig.builder(yamlMdaConfig.projectPackageName);
		if (!StringUtil.isBlank(yamlMdaConfig.encoding)) {
			mdaConfigBuilder.withEncoding(yamlMdaConfig.encoding);
		}
		if (!StringUtil.isBlank(yamlMdaConfig.targetGenDir)) {
			mdaConfigBuilder.withTargetGenDir(rootPath + yamlMdaConfig.targetGenDir);
		}
		yamlMdaConfig.properties.entrySet().forEach(entry -> mdaConfigBuilder.addProperty(entry.getKey(), entry.getValue()));

		return mdaConfigBuilder.build();
	}

}
