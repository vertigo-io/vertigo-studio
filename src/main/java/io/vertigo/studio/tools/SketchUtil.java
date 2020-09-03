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
package io.vertigo.studio.tools;

import io.vertigo.core.lang.Assertion;

/**
 * This class provides usefull utilies about sketchs.
 *
 * @author  pchretien
 */
public final class SketchUtil {
	private SketchUtil() {
		super();
	}

	/**
	 * Returns the short name of the sketch.
	 * @param sketchName Name of the sketch
	 * @param definitionClass Type of the sketch
	 * @return the short name of the sketch
	 */
	public static String getLocalName(final String sketchName, final String prefix) {
		Assertion.check()
				.isNotBlank(sketchName)
				.isNotBlank(prefix);
		//-----
		//On enléve le prefix et le separateur.
		//On vérifie aussi que le prefix est OK
		Assertion.check().isTrue(sketchName.startsWith(prefix), "Le nom de la définition '{0}' ne commence pas par le prefix attendu : '{1}'", sketchName, prefix);
		return sketchName.substring(prefix.length());
	}
}
