package io.vertigo.shiny.models.dataviz.organization;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyOrganization(
		List<ShinyOrganizationNode> nodes) implements ShinyModel {

	public ShinyOrganization {

	}
}
