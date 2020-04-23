/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
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
package io.vertigo.studio.impl.mda;

import java.io.File;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.studio.mda.MdaConfig;

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
	 * @param fileGeneratorConfig the config of the file generator
	*/
	FileGeneratorBuilder(final MdaConfig fileGeneratorConfig) {
		Assertion.checkNotNull(fileGeneratorConfig);
		//---
		myEncoding = fileGeneratorConfig.getEncoding();
		myTargetGenDir = fileGeneratorConfig.getTargetGenDir();
	}

	/**
	 * @param model the model
	 * @return this builder
	 */
	public FileGeneratorBuilder withModel(final Map<String, Object> model) {
		Assertion.checkNotNull(model);
		//---
		myModel = model;
		return this;
	}

	/**
	 * @param fileName the name of the file including extension
	 * @return this builder
	 */
	public FileGeneratorBuilder withFileName(final String fileName) {
		Assertion.checkNotNull(fileName);
		//---
		myFileName = fileName;
		return this;
	}

	/**
	 * @param packageName the name of the package
	 * @return this builder
	 */
	public FileGeneratorBuilder withPackageName(final String packageName) {
		Assertion.checkNotNull(packageName);
		//---
		myPackageName = packageName;
		return this;
	}

	/**
	 * @param packageName the name of the package
	 * @return this builder
	 */
	public FileGeneratorBuilder withEncoding(final String encoding) {
		Assertion.checkNotNull(encoding);
		//---
		myEncoding = encoding;
		return this;
	}

	/**
	 * @param templateName the name of the template
	 * @return this builder
	 */
	public FileGeneratorBuilder withTemplateName(final Class relativeClass, final String templateName) {
		Assertion.checkNotNull(relativeClass);
		Assertion.checkNotNull(templateName);
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
		Assertion.checkNotNull(targetGenDir);
		//---
		myTargetGenDir = targetGenDir;
		return this;
	}

	/**
	 * @param genSubDir Nom subdir de génération
	 * @return this builder
	 */
	public FileGeneratorBuilder withGenSubDir(final String genSubDir) {
		Assertion.checkNotNull(genSubDir);
		//---
		myGenSubDir = genSubDir;
		return this;
	}

	@Override
	public FileGenerator build() {
		Assertion.checkNotNull(myModel, "a model is required");
		Assertion.checkNotNull(myFileName, "a file name is required");
		Assertion.checkNotNull(myPackageName, "a package is required");
		Assertion.checkNotNull(myRelativeClass, "a relative class is required to locate template");
		Assertion.checkNotNull(myTemplateName, "a template is required");
		Assertion.checkNotNull(myEncoding, "an encoding is required");
		Assertion.checkNotNull(myTargetGenDir, "a target gen dir is required");
		Assertion.checkNotNull(myGenSubDir, "a sub directory is required");
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
