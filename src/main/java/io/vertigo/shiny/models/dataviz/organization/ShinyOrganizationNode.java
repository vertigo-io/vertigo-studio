package io.vertigo.shiny.models.dataviz.organization;

import io.vertigo.core.lang.Assertion;

public record ShinyOrganizationNode(
        String id,
        String parentId,
        String name,
        String position,
        String imageUrl) {

    public ShinyOrganizationNode {
        Assertion.check()
                .isNotBlank(id, "id cannot be blank")
                .isNotBlank(name, "name cannot be blank")
                .isNotBlank(position, "position cannot be blank");
    }
}
