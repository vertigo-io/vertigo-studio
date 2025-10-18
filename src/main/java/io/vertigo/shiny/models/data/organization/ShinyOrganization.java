package io.vertigo.shiny.models.data.organization;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyOrganization(
		UUID id,
		List<ShinyOrganizationNode> nodes) implements ShinyModel {

	public ShinyOrganization {
		Assertion.check().isNotNull(id);
	}

	@Override
	public String shinyType() {
		return "ShinyOrganization";
	}
}
