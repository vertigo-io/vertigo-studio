package io.vertigo.shiny.components.dataviz.flow;

import io.vertigo.core.lang.Assertion;

public record ShinyFlowNode(
		String id,
		String name,
		double x,
		double y) {

	public ShinyFlowNode {
		Assertion.check()
				.isNotBlank(id, "Node ID cannot be blank")
				.isNotBlank(name, "Node name cannot be blank");
	}
}
