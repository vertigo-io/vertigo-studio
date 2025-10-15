package io.vertigo.shiny.components.dataviz.flow;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyFlowBuilder {
	private String _title;
	private final List<ShinyFlowNode> _nodes = new ArrayList<>();
	private final List<ShinyFlowConnection> _connections = new ArrayList<>();

	public ShinyFlowBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		this._title = title;
		return this;
	}

	public ShinyFlowBuilder addNode(final String id, final String name, final double x, final double y) {
		_nodes.add(new ShinyFlowNode(id, name, x, y));
		return this;
	}

	public ShinyFlowBuilder addConnection(final String from, final String to) {
		_connections.add(new ShinyFlowConnection(from, to));
		return this;
	}

	public ShinyFlow build() {
		Assertion.check()
				.isNotBlank(_title, "Title is required")
				.isFalse(_nodes.isEmpty(), "At least one node is required");
		return new ShinyFlow(_title, _nodes, _connections);
	}
}
