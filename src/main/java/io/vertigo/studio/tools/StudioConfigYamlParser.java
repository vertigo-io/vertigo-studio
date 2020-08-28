package io.vertigo.studio.tools;

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
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.generator.GeneratorConfigBuilder;
import io.vertigo.studio.notebook.NotebookConfig;
import io.vertigo.studio.source.Source;
import io.vertigo.studio.tools.YamlStudioConfig.YamlMdaConfig;
import io.vertigo.studio.tools.YamlStudioConfig.YamlResourceConfig;

final class StudioConfigYamlParser {

	static final NotebookConfig parseYaml(final URL configUrl) throws URISyntaxException {
		final YamlStudioConfig yamlStudioConfig = new Yaml(new Constructor(YamlStudioConfig.class)).loadAs(FileUtil.read(configUrl), YamlStudioConfig.class);
		final String rootPath = Path.of(configUrl.toURI()).getParent().toString() + "/";

		//metamodelresources
		final List<Source> sources = parseResources(yamlStudioConfig.resources, rootPath);
		//mdaCondig
		final GeneratorConfig generatorConfig = parseMdaConfig(yamlStudioConfig.mdaConfig, rootPath);
		//---
		return new NotebookConfig(sources, generatorConfig);
	}

	private static final List<Source> parseResources(final List<YamlResourceConfig> resources, final String rootPath) {
		//metamodelresources
		return resources.stream()
				.map(resource -> Source.of(resource.type, rootPath + resource.path))
				.collect(Collectors.toList());
	}

	private static final GeneratorConfig parseMdaConfig(final YamlMdaConfig yamlMdaConfig, final String rootPath) {
		//mdaCondig
		Assertion.check().isNotNull(yamlMdaConfig.projectPackageName, "A 'projectPackageName' is required in mdaConfig");
		final GeneratorConfigBuilder generatorConfigBuilder = GeneratorConfig.builder(yamlMdaConfig.projectPackageName);
		if (!StringUtil.isBlank(yamlMdaConfig.encoding)) {
			generatorConfigBuilder.withEncoding(yamlMdaConfig.encoding);
		}
		if (!StringUtil.isBlank(yamlMdaConfig.targetGenDir)) {
			generatorConfigBuilder.withTargetGenDir(rootPath + yamlMdaConfig.targetGenDir);
		}
		yamlMdaConfig.properties.entrySet().forEach(entry -> generatorConfigBuilder.addProperty(entry.getKey(), entry.getValue()));

		return generatorConfigBuilder.build();
	}

}
