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
package io.vertigo.studio.source.vertigo.java.data;

import java.util.Iterator;

import io.vertigo.core.util.ListBuilder;
import io.vertigo.studio.source.vertigo.java.data.domain.Attachment;
import io.vertigo.studio.source.vertigo.java.data.domain.City;
import io.vertigo.studio.source.vertigo.java.data.domain.CityFragment;
import io.vertigo.studio.source.vertigo.java.data.domain.Command;
import io.vertigo.studio.source.vertigo.java.data.domain.CommandCriteria;
import io.vertigo.studio.source.vertigo.java.data.domain.CommandType;
import io.vertigo.studio.source.vertigo.java.data.domain.CommandValidation;

/**
 * Attention cette classe est générée automatiquement !
 */
public final class DtDefinitions implements Iterable<Class<?>> {

	/** {@inheritDoc} */
	@Override
	public Iterator<Class<?>> iterator() {
		return new ListBuilder<Class<?>>()
				.add(Attachment.class)
				.add(City.class)
				.add(CityFragment.class)
				.add(Command.class)
				.add(CommandCriteria.class)
				.add(CommandType.class)
				.add(CommandValidation.class)
				.build().iterator();
	}
}
