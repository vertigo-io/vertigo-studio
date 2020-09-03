/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.impl.generator;

import java.io.File;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.studio.generator.GeneratorConfig;

/**
 * This class provides a way to create a FileFenerator.
 * @author pchretien, mlaroche
 */
public final class FileGeneratorBuilder implements Builder<FileGenerator> {
	private Map<String, Object> myModel;
	private String myFileName;
	private String myPackageName;

	private Class myRelativeClass;
	private String myTemplateName;
	private String myTargetGenDir;
	private String myGenSubDir;
	private String myEncoding;

	/**
	 * @param generatorConfig the config of the file generator
	*/
	FileGeneratorBuilder(final GeneratorConfig generatorConfig) {
		Assertion.check().isNotNull(generatorConfig);
		//---
		myEncoding = generatorConfig.getEncoding();
		myTargetGenDir = generatorConfig.getTargetGenDir();
	}

	/**
	 * @param model the model
	 * @return this builder
	 */
	public FileGeneratorBuilder withModel(final Map<String, Object> model) {
		Assertion.check().isNotNull(model);
		//---
		myModel = model;
		return this;
	}

	/**
	 * @param fileName the name of the file including extension
	 * @return this builder
	 */
	public FileGeneratorBuilder withFileName(final String fileName) {
		Assertion.check().isNotNull(fileName);
		//---
		myFileName = fileName;
		return this;
	}

	/**
	 * @param packageName the name of the package
	 * @return this builder
	 */
	public FileGeneratorBuilder withPackageName(final String packageName) {
		Assertion.check().isNotNull(packageName);
		//---
		myPackageName = packageName;
		return this;
	}

	/**
	 * @param packageName the name of the package
	 * @return this builder
	 */
	public FileGeneratorBuilder withEncoding(final String encoding) {
		Assertion.check().isNotNull(encoding);
		//---
		myEncoding = encoding;
		return this;
	}

	/**
	 * @param templateName the name of the template
	 * @return this builder
	 */
	public FileGeneratorBuilder withTemplateName(final Class relativeClass, final String templateName) {
		Assertion.check()
				.isNotNull(relativeClass)
				.isNotNull(templateName);
		//---
		myRelativeClass = relativeClass;
		myTemplateName = templateName;
		return this;
	}

	/**
	 * @param targetGenDir Repertoire de destination
	 * @return this builder
	 */
	public FileGeneratorBuilder withTargetGenDir(final String targetGenDir) {
		Assertion.check().isNotNull(targetGenDir);
		//---
		myTargetGenDir = targetGenDir;
		return this;
	}

	/**
	 * @param genSubDir Nom subdir de génération
	 * @return this builder
	 */
	public FileGeneratorBuilder withGenSubDir(final String genSubDir) {
		Assertion.check().isNotNull(genSubDir);
		//---
		myGenSubDir = genSubDir;
		return this;
	}

	@Override
	public FileGenerator build() {
		Assertion.check()
				.isNotNull(myModel, "a model is required")
				.isNotNull(myFileName, "a file name is required")
				.isNotNull(myPackageName, "a package is required")
				.isNotNull(myRelativeClass, "a relative class is required to locate template")
				.isNotNull(myTemplateName, "a template is required")
				.isNotNull(myEncoding, "an encoding is required")
				.isNotNull(myTargetGenDir, "a target gen dir is required")
				.isNotNull(myGenSubDir, "a sub directory is required");
		//---
		final String filePath = buildFilePath();
		return new FileGeneratorFreeMarker(myModel, filePath, myTemplateName, myEncoding, myRelativeClass);
	}

	private String buildFilePath() {
		final String directoryPath = myTargetGenDir + myGenSubDir + File.separatorChar + package2directory(myPackageName) + File.separatorChar;
		return directoryPath + myFileName;
	}

	private static String package2directory(final String packageName) {
		return packageName.replace('.', File.separatorChar).replace('\\', File.separatorChar);
	}
}
