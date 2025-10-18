package io.vertigo.shiny.models.data.organization;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyOrganization(
		List<ShinyOrganizationNode> nodes) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyOrganization";
	}
}
