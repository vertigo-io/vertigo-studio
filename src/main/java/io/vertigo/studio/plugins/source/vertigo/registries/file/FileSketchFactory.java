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
package io.vertigo.studio.plugins.source.vertigo.registries.file;

import java.util.List;

import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.notebook.file.FileInfoSketch;
import io.vertigo.studio.plugins.source.vertigo.KspProperty;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslEntity;
import io.vertigo.studio.plugins.source.vertigo.dsl.entity.DslGrammar;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslRaw;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslRawKey;
import io.vertigo.studio.plugins.source.vertigo.dsl.raw.DslSketchFactory;

/**
 * @author pchretien, mlaroche
 */
public final class FileSketchFactory implements DslSketchFactory {

	@Override
	public DslGrammar getGrammar() {
		return new FileGrammar();
	}

	/** {@inheritDoc} */
	@Override
	public List<Sketch> createSketches(final Notebook notebook, final DslRaw raw) {
		final DslEntity entity = raw.getEntity();
		if (FileGrammar.FILE_INFO_ENTITY.equals(entity)) {
			//Seuls les taches sont gérées.
			return List.of(createFileInfoSketch(raw));
		}
		throw new IllegalStateException("his kind of raw " + entity + " is not managed by me");
	}

	private static FileInfoSketch createFileInfoSketch(final DslRaw fileInfoRaw) {
		final DslRawKey fileInfoRawKey = fileInfoRaw.getKey();
		final String storeName = (String) fileInfoRaw.getPropertyValue(KspProperty.DATA_SPACE);

		return new FileInfoSketch(fileInfoRawKey.getName(), storeName);
	}

}
