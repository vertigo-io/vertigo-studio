/*
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
package io.vertigo.studio.plugins.generator.vertigo.task.test;

import java.util.function.Function;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.generator.GeneratorConfig;
import io.vertigo.studio.notebook.task.TaskSketch;

/**
 * Template used by freemarker.
 *
 * @author sezratty
 */
public final class TemplateAoTaskTest {
	private final String packageName;
	private final String classSimpleName;
	private final TemplateTaskDefinition templateTaskDefinition;
	private final String daoTestBaseClass;

	/**
	 * Constructor.
	 */
	TemplateAoTaskTest(
			final GeneratorConfig taskConfiguration,
			final TaskSketch taskSketch,
			final String packageName,
			final String classSimpleName,
			final String daoTestBaseClass,
			final Function<String, String> classNameFromDt) {
		Assertion.check()
				.isNotNull(taskConfiguration)
				.isNotNull(taskSketch)
				.isNotNull(packageName);
		//-----
		this.packageName = packageName;

		this.classSimpleName = classSimpleName;
		templateTaskDefinition = new TemplateTaskDefinition(taskSketch, packageName, classSimpleName, classNameFromDt);
		this.daoTestBaseClass = daoTestBaseClass;
	}

	/**
	 * @return Simple Nom (i.e. sans le package) de la classe de test.
	 */
	public String getClassSimpleName() {
		return classSimpleName;
	}

	/**
	 * @return Task
	 */
	public TemplateTaskDefinition getTaskDefinition() {
		return templateTaskDefinition;
	}

	/**
	 * @return Nom du package de la classe de DAO.
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return Nom canonique de la classe de base pour le test de DAO.
	 */
	public String getDaoTestBaseClass() {
		return daoTestBaseClass;
	}

	/**
	 * @return Nom simple de la classe de base pour le test de DAO.
	 */
	public String getDaoTestBaseClassSimpleName() {
		return getLastPackagename(daoTestBaseClass);
	}

	private static String getLastPackagename(final String canonicalName) {
		final String[] parts = canonicalName.split("\\.");
		return parts[parts.length - 1];
	}
}
