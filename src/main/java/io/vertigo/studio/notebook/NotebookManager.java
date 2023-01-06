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
package io.vertigo.studio.notebook;

import io.vertigo.core.node.component.Manager;

public interface NotebookManager extends Manager {

	/**
	 * Renders a notebook as json, so it can be shared, compared, stored and more !
	 * @param notebook the notebook
	 * @return notebook as json
	 */
	String toJson(Notebook notebook);

	/**
	 * Loads a notebook from json.
	 * @param json the notebook as json
	 * @return notebook
	 */
	Notebook fromJson(String json);

}
