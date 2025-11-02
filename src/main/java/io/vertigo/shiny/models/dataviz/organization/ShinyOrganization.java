package io.vertigo.shiny.models.dataviz.organization;

import java.util.List;

import io.vertigo.shiny.models.ShinyBlock;

public record ShinyOrganization(
		List<ShinyOrganizationNode> nodes) implements ShinyBlock {

	public ShinyOrganization {

	}
}
