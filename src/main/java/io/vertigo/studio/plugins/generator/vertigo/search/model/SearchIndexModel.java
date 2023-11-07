/*
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
package io.vertigo.studio.plugins.generator.vertigo.search.model;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.search.SearchIndexSketch;
import io.vertigo.studio.plugins.generator.vertigo.VertigoConstants.VertigoDefinitionPrefix;

public final class SearchIndexModel {

	private final SearchIndexSketch searchIndexSketch;
	private final List<IndexCopyToModel> copyToModels;

	public SearchIndexModel(final SearchIndexSketch searchIndexSketch) {
		Assertion.check().isNotNull(searchIndexSketch);
		//---
		this.searchIndexSketch = searchIndexSketch;
		copyToModels = searchIndexSketch.getIndexCopyToFields()
				.stream()
				.map(fromField -> new IndexCopyToModel(fromField, searchIndexSketch.getIndexCopyToFromFields(fromField)))
				.toList();
	}

	public String getName() {
		return VertigoDefinitionPrefix.SearchIndexDefinition.getPrefix() + searchIndexSketch.getLocalName();
	}

	public String getLoaderId() {
		return searchIndexSketch.getSearchLoaderId();
	}

	public String getIndexDtDefinition() {
		return "Dt" + searchIndexSketch.getIndexDtSketch().getLocalName();
	}

	public String getKeyConceptDtDefinition() {
		return "Dt" + searchIndexSketch.getKeyConceptDtSketch().getLocalName();
	}

	public String getKeyConceptClassCanonicalName() {
		return searchIndexSketch.getKeyConceptDtSketch().getClassCanonicalName();
	}

	public List<IndexCopyToModel> getCopyToModels() {
		return copyToModels;
	}

}
