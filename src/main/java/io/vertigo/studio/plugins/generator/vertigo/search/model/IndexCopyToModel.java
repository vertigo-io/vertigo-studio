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
package io.vertigo.studio.plugins.generator.vertigo.search.model;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.domain.DtSketchField;

public final class IndexCopyToModel {
	private final DtSketchField toField;
	private final List<DtSketchField> fromFields;

	public IndexCopyToModel(final DtSketchField toField, final List<DtSketchField> fromFields) {
		Assertion.check()
				.isNotNull(toField)
				.isNotNull(fromFields);
		//---
		this.toField = toField;
		this.fromFields = fromFields;
	}

	public String getTo() {
		return toField.getName();
	}

	public List<String> getFrom() {
		return fromFields
				.stream()
				.map(DtSketchField::getName)
				.collect(Collectors.toList());
	}

}
