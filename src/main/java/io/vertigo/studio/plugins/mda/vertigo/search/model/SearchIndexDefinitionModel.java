package io.vertigo.studio.plugins.mda.vertigo.search.model;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.studio.notebook.search.SearchIndexSketch;
import io.vertigo.studio.plugins.mda.vertigo.VertigoConstants.VertigoDefinitionPrefix;

public class SearchIndexDefinitionModel {

	private final SearchIndexSketch searchIndexSketch;
	private final List<IndexCopyToModel> copyToModels;

	public SearchIndexDefinitionModel(final SearchIndexSketch searchIndexSketch) {
		Assertion.check().isNotNull(searchIndexSketch);
		//---
		this.searchIndexSketch = searchIndexSketch;
		copyToModels = searchIndexSketch.getIndexCopyToFields().stream()
				.map(fromField -> new IndexCopyToModel(fromField, searchIndexSketch.getIndexCopyToFromFields(fromField)))
				.collect(Collectors.toList());
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

	public List<IndexCopyToModel> getCopyToModels() {
		return copyToModels;
	}

}
