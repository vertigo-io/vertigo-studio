/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2021, Vertigo.io, team@vertigo.io
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
package io.vertigo.studio.impl.source;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import io.vertigo.core.node.component.Plugin;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.source.Source;

public interface SourceReaderPlugin extends Plugin {

	Set<String> getHandledSourceTypes();

	Stream<? extends Sketch> parseResources(List<Source> resources, Notebook notebook);

}
