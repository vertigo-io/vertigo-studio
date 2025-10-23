package io.vertigo.shiny.models.dataviz.organization;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyOrganization(
		UUID id,
		List<ShinyOrganizationNode> nodes) implements ShinyModel {

	public ShinyOrganization {

	}
}
