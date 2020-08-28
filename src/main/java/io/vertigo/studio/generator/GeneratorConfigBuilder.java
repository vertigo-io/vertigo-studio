package io.vertigo.studio.generator;

import java.util.Properties;

import io.vertigo.core.lang.Builder;

public final class GeneratorConfigBuilder implements Builder<GeneratorConfig> {

	private final String myProjectPackageName;
	private final Properties myProperties = new Properties();

	private String myTargetGenDir;
	private String myEncoding;

	GeneratorConfigBuilder(final String projectPackageName) {
		myProjectPackageName = projectPackageName;
		myEncoding = "UTF-8";
		myTargetGenDir = "src/main/";
	}

	public GeneratorConfigBuilder withEncoding(final String encoding) {
		myEncoding = encoding;
		return this;
	}

	public GeneratorConfigBuilder withTargetGenDir(final String targetGenDir) {
		myTargetGenDir = targetGenDir;
		return this;
	}

	public GeneratorConfigBuilder addProperty(final String key, final String value) {
		myProperties.put(key, value);
		return this;
	}

	@Override
	public GeneratorConfig build() {
		return new GeneratorConfig(myTargetGenDir, myProjectPackageName, myEncoding, myProperties);
	}

}
