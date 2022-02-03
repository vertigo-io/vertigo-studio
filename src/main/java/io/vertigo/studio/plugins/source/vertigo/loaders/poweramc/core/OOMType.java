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
package io.vertigo.studio.plugins.source.vertigo.loaders.poweramc.core;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Type des objets powerAMC.
 * Les correspondances dynamo sont précisées ci-dessous.
 *
 * @author pchretien, mlaroche
 */
enum OOMType {
	/**
	 * Objet OOM décrivant un Package >>Package.
	 */
	Package("o:Package"),
	/**
	 * Objet OOM décrivant une Class >> DtDefinition.
	 */
	Class("o:Class"),
	/**
	 * Objet OOM décrivant un Domain >> Domain.
	 */
	Domain("o:Domain"),
	/**
	 * Objet OOM décrivant un Attibute d'un OOM >> DtField.
	 */
	Attribute("o:Attribute"),
	/**
	 * OOM décrivant un Identifier >> Assignation du caractère PK d'un DtField.
	 */
	Identifier("o:Identifier"),
	/**
	 * OOM décrivant une Association >> Association.
	 */
	Association("o:Association"),
	/**
	 * Référence sur un objet OOM.
	 */
	Shortcut("o:Shortcut");

	private final String code;

	OOMType(final String code) {
		this.code = code;
	}

	private String getCode() {
		return code;
	}

	static Optional<OOMType> getType(final String code) {
		return Stream.of(OOMType.values())
				.filter(type -> type.getCode().equals(code))
				.findFirst();
	}

	static boolean isNodeByRef(final String code) {
		return Domain.getCode().equals(code)
				|| Attribute.getCode().equals(code)
				|| Class.getCode().equals(code)
				|| Shortcut.getCode().equals(code)
				|| Identifier.getCode().equals(code);
	}
}
