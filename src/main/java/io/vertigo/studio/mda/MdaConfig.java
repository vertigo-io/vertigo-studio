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
package io.vertigo.studio.mda;

import java.util.Properties;

import io.vertigo.core.lang.Assertion;

/**
 * Configuration du generateur de fichiers.
 *
 * @author dchallas
 */
public final class MdaConfig {
	/**
	 * Répertoire des fichiers TOUJOURS générés
	 * Doit être renseigné dans le fichier properties [targetDir]
	 */
	private final String targetGenDir;
	/**
	 * Racine du projet.
	 */
	private final String projectPackageName;
	/**
	 * Encoding des fichiers générés.
	 */
	private final String encoding;

	private final Properties properties;

	/**
	 * Chargement des paramètres depuis le fichier properties.
	 */
	MdaConfig(
			final String targetGenDir,
			final String projectPackageName,
			final String encoding,
			final Properties properties) {
		Assertion.check()
				.argNotEmpty(targetGenDir, "Le repertoire des fichiers generes [targetGenDir] doit etre renseigné !")
				.argNotEmpty(projectPackageName, "le package racine du projet doit être renseigne ! ")
				.argNotEmpty(encoding, "l'encoding des fichiers gérénés [encoding] doit etre renseigné !")
				.notNull(properties);
		//-----
		this.targetGenDir = targetGenDir;
		this.projectPackageName = projectPackageName;
		this.encoding = encoding;
		Assertion.check().state(targetGenDir.endsWith("/"), "Le chemin doit finir par '/'.");
		this.properties = properties;
	}

	/**
	 * Chargement des paramètres depuis le fichier properties.
	 */
	public static MdaConfigBuilder builder(
			final String projectPackageName) {
		return new MdaConfigBuilder(projectPackageName);
	}

	/**
	 * Donne la valeur de projectPackageName.
	 *
	 * @return la valeur de projectPackageName.
	 */
	public String getProjectPackageName() {
		return projectPackageName;
	}

	/**
	 * Donne la valeur de targetGenDir.
	 *
	 * @return la valeur de targetGenDir.
	 */
	public String getTargetGenDir() {
		return targetGenDir;
	}

	/**
	 * Donne la valeur de encoding.
	 *
	 * @return la valeur de encoding.
	 */
	public String getEncoding() {
		return encoding;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getOrDefaultAsString(final String key, final String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public String getAsString(final String key) {
		return properties.getProperty(key);
	}

	public boolean getAsBoolean(final String key) {
		final String value = properties.getProperty(key);
		Assertion.check().notNull(value);
		return Boolean.valueOf(value);
	}

	public boolean getOrDefaultAsBoolean(final String key, final boolean defaultValue) {
		return properties.containsKey(key) ? Boolean.valueOf(properties.getProperty(key)) : defaultValue;
	}

}
