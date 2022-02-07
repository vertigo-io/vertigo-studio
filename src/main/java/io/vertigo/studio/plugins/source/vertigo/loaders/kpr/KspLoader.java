/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.plugins.source.vertigo.loaders.kpr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import io.vertigo.commons.peg.PegNoMatchFoundException;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.util.StringUtil;
import io.vertigo.studio.impl.source.dsl.raw.DslRawRepository;
import io.vertigo.studio.plugins.source.vertigo.loaders.kpr.rules.DslKspRule;

/**
 * Parser d'un fichier KSP.
 *
 * @author pchretien, mlaroche
 */
final class KspLoader {

	private final Charset charset;
	private final URL kspURL;

	/**
	 * Constructor.
	 *
	 * @param kspURL URL du fichier KSP.
	 * @param charset charset d'encoding du fihcier KSP à lire
	 */
	KspLoader(final URL kspURL, final Charset charset) {
		Assertion.check()
				.isNotNull(kspURL)
				.isNotNull(charset);
		//-----
		this.kspURL = kspURL;
		this.charset = charset;
	}

	/**
	 * Chargement et analyse du fichier.
	 *
	 * @param rawRepository DynamicDefinitionRepository
	 */
	void load(final DslRawRepository rawRepository) {
		Assertion.check().isNotNull(rawRepository);
		try {
			final String s = parseFile();
			new DslKspRule(rawRepository)
					.parse(s);
		} catch (final PegNoMatchFoundException e) {
			final String message = StringUtil.format("Echec de lecture du fichier KSP {0}\n{1}", kspURL.getFile(), e.getFullMessage());
			throw WrappedException.wrap(e, message);
		} catch (final Exception e) {
			final String message = StringUtil.format("Echec de lecture du fichier KSP {0}\n{1}", kspURL.getFile(), e.getMessage());
			throw WrappedException.wrap(e, message);
		}
	}

	/**
	 * Transforme un fichier en une chaine de caractÃ¨re parsable.
	 *
	 * @return String Chaine parsable correspondant au fichier.
	 * @throws IOException Erreur d'entrÃ©e/sortie
	 */
	private String parseFile() throws IOException {
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(kspURL.openStream(), charset))) {
			final StringBuilder buff = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				buff.append(line);
				line = reader.readLine();
				buff.append("\r\n");
			}
			return buff.toString();

		}
	}
}
