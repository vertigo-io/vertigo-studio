package io.vertigo.shiny.components.data.organization;

import java.util.List;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyOrganization(
        List<ShinyOrganizationNode> nodes) implements ShinyComponent {

    @Override
    public String type() {
        return "organization";
    }
}
