package io.vertigo.shiny.models.dataviz.organization;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public record ShinyOrganizationNode(
		@Nonnull String id,
		String parentId,
		@Nonnull String name,
		@Nonnull String position,
		String imageUrl) {

	public ShinyOrganizationNode {
		Assertion.check()
				.isNotBlank(id, "id cannot be blank")
				.isNotBlank(name, "name cannot be blank")
				.isNotBlank(position, "position cannot be blank");
	}
}
