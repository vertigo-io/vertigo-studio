package io.vertigo.studio.mda;

import java.util.Properties;

import io.vertigo.core.lang.Builder;

public class MdaConfigBuilder implements Builder<MdaConfig> {

	private final String myProjectPackageName;
	private final Properties myProperties = new Properties();

	private String myTargetGenDir;
	private String myEncoding;

	MdaConfigBuilder(final String projectPackageName) {
		myProjectPackageName = projectPackageName;
		myEncoding = "UTF-8";
		myTargetGenDir = "src/main/";
	}

	public MdaConfigBuilder withEncoding(final String encoding) {
		myEncoding = encoding;
		return this;
	}

	public MdaConfigBuilder withTargetGenDir(final String targetGenDir) {
		myTargetGenDir = targetGenDir;
		return this;
	}

	public MdaConfigBuilder addProperty(final String key, final String value) {
		myProperties.put(key, value);
		return this;
	}

	@Override
	public MdaConfig build() {
		return new MdaConfig(myTargetGenDir, myProjectPackageName, myEncoding, myProperties);
	}

}
