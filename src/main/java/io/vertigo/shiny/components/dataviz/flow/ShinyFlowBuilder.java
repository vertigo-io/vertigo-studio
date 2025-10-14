package io.vertigo.shiny.components.dataviz.flow;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyFlowBuilder {
	private String title;
	private final List<FlowNode> nodes = new ArrayList<>();
	private final List<FlowConnection> connections = new ArrayList<>();

	public ShinyFlowBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		this.title = title;
		return this;
	}

	public ShinyFlowBuilder addNode(final String id, final String name, final double x, final double y) {
		nodes.add(new FlowNode(id, name, x, y));
		return this;
	}

	public ShinyFlowBuilder addConnection(final String from, final String to) {
		connections.add(new FlowConnection(from, to));
		return this;
	}

	public ShinyFlow build() {
		Assertion.check()
				.isNotBlank(title, "Title is required")
				.isFalse(nodes.isEmpty(), "At least one node is required");
		return new ShinyFlow(title, nodes, connections);
	}
}
