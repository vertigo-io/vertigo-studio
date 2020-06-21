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
package io.vertigo.studio.metamodel;

import io.vertigo.core.lang.Assertion;

/**
 * A resource is defined by
 * - a type
 * - a path
 * A resource can be a file, a blob or a simple java class.
 * A resource is used to configure a module.
 *
 * @author pchretien, mlaroche
 */
public final class MetamodelResource {
	private final String type;
	private final String path;

	public static MetamodelResource of(final String type, final String path) {
		return new MetamodelResource(type, path);
	}

	private MetamodelResource(final String type, final String path) {
		Assertion.check()
				.argNotEmpty(type)
				.argNotEmpty(path);
		//-----
		this.type = type;
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return "{ type: " + type + ", path: " + path + " }";
	}
}
