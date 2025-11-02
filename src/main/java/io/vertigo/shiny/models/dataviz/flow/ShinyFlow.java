package io.vertigo.shiny.models.dataviz.flow;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyFlow(
		UUID id,
		List<ShinyFlowNode> nodes,
		List<ShinyFlowEdge> edges) implements ShinyBlock {

	public ShinyFlow {
		Assertion.check()
				.isNotNull(nodes)
				.isNotNull(edges);
	}
}
