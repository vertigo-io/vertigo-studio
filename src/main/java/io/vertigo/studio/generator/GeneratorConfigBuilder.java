/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2023, Vertigo.io, team@vertigo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
