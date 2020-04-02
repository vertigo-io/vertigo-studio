package io.vertigo.studio.plugins.mda.search.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.node.definition.DefinitionUtil;
import io.vertigo.dynamo.search.StudioFacetDefinition;
import io.vertigo.studio.plugins.mda.VertigoConstants.VertigoDefinitionPrefix;

public class FacetDefinitionModel {

	private final StudioFacetDefinition studioFacetDefinition;
	private List<FacetValueModel> facetValueModels;
	private List<FacetParamModel> facetParamModels;

	public FacetDefinitionModel(final StudioFacetDefinition studioFacetDefinition) {
		Assertion.checkNotNull(studioFacetDefinition);
		//---
		this.studioFacetDefinition = studioFacetDefinition;
		if (studioFacetDefinition.isRangeFacet()) {
			facetValueModels = studioFacetDefinition.getFacetRanges().stream().map(FacetValueModel::new).collect(Collectors.toList());
			facetParamModels = Collections.emptyList();
		} else if (studioFacetDefinition.isCustomFacet()) {
			facetValueModels = Collections.emptyList();
			facetParamModels = studioFacetDefinition.getFacetParams().entrySet().stream()
					.map(entry -> new FacetParamModel(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
		} else {
			facetValueModels = Collections.emptyList();
			facetParamModels = Collections.emptyList();
		}
	}

	public boolean isTerm() {
		return !studioFacetDefinition.isRangeFacet() && !studioFacetDefinition.isCustomFacet();
	}

	public boolean isCustom() {
		return studioFacetDefinition.isCustomFacet();
	}

	public boolean isRange() {
		return studioFacetDefinition.isRangeFacet();
	}

	public String getName() {
		return VertigoDefinitionPrefix.FacetDefinition.getPrefix() + DefinitionUtil.getLocalName(studioFacetDefinition.getName(), StudioFacetDefinition.class);
	}

	public String getFieldName() {
		return studioFacetDefinition.getDtField().getName();
	}

	public String getLabel() {
		return studioFacetDefinition.getLabel().getDisplay();
	}

	public Boolean isMultiSelectable() {
		return studioFacetDefinition.isMultiSelectable();
	}

	public String getOrder() {
		return studioFacetDefinition.getOrder().name();
	}

	public List<FacetValueModel> getFacetValues() {
		return facetValueModels;
	}

	public List<FacetParamModel> getFacetParams() {
		return facetParamModels;
	}

}
