package io.vertigo.shiny.components.data.organization;

import java.util.ArrayList;
import java.util.List;

public final class ShinyOrganizationBuilder {
    private final List<ShinyOrganizationNode> _nodes = new ArrayList<>();

    public ShinyOrganizationBuilder addNode(final ShinyOrganizationNode node) {
        _nodes.add(node);
        return this;
    }

    public ShinyOrganizationBuilder addNode(final String id, final String parentId, final String name, final String position, final String imageUrl) {
        _nodes.add(new ShinyOrganizationNode(id, parentId, name, position, imageUrl));
        return this;
    }

    public ShinyOrganization build() {
        return new ShinyOrganization(_nodes);
    }
}
