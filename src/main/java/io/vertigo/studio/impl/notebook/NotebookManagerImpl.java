/**
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
package io.vertigo.studio.impl.notebook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.NotebookManager;

public final class NotebookManagerImpl implements NotebookManager {
	private static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting()
			.create();

	@Override
	public String toJson(final Notebook notebook) {
		return GSON.toJson(notebook);
	}

	@Override
	public Notebook fromJson(final String json) {
		return GSON.fromJson(json, Notebook.class);
	}

}
