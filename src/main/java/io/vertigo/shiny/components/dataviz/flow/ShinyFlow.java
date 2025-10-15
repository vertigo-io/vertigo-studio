package io.vertigo.shiny.components.dataviz.flow;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyFlow(
		String title,
		List<ShinyFlowNode> nodes,
		List<ShinyFlowConnection> connections) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyFlow";
	}

	public ShinyFlow {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank");
		Assertion.check()
				.isNotNull(nodes, "Nodes list cannot be null");
		Assertion.check()
				.isNotNull(connections, "Connections list cannot be null");
	}
}
