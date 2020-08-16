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
package io.vertigo.studio.notebook.file;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.AbstractSketch;
import io.vertigo.studio.notebook.SkecthPrefix;

/**
 * Définition d'un FileInfo.
 *
 * La définition n'est pas serializable.
 * Elle doit être invariante (non mutable) dans le temps.
 * Par défaut elle est chargée au (re)démarrage du serveur.
 *
 * @author  npiedeloup, pchretien
 */
@SkecthPrefix(FileInfoSketch.PREFIX)
public final class FileInfoSketch extends AbstractSketch {
	public static final String PREFIX = "Fi";
	/**
	 * StoreName des fichiers de ce type.
	 */
	private final String storeName;

	/**
	 * Constructor.
	 * @param name Nom de la définition
	 * @param storeName Nom du store de ces fichiers
	 */
	public FileInfoSketch(final String name, final String storeName) {
		super(name);
		//---
		Assertion.check().isNotBlank(storeName);
		//-----
		this.storeName = storeName;
	}

	/**
	 * @return Store d'accès aux FI.
	 */
	public String getStoreName() {
		return storeName;
	}
}
