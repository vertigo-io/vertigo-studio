package io.vertigo.shiny.models.dataviz.organization;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyOrganizationBuilder implements Builder<ShinyOrganization> {
	private UUID _id;
    private final List<ShinyOrganizationNode> _nodes = new ArrayList<>();

	public ShinyOrganizationBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

    public ShinyOrganizationBuilder addNode(final ShinyOrganizationNode node) {
        _nodes.add(node);
        return this;
    }

    public ShinyOrganizationBuilder addNode(final String id, final String parentId, final String name, final String position, final String imageUrl) {
        _nodes.add(new ShinyOrganizationNode(id, parentId, name, position, imageUrl));
        return this;
    }

    public ShinyOrganization build() {
		_id = _id == null ? UUID.randomUUID() : _id;
        return new ShinyOrganization(_id, _nodes);
    }
}
