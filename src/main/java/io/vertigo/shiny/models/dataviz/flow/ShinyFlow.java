package io.vertigo.shiny.models.dataviz.flow;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyFlow(
		UUID id,
		@Nonnull List<ShinyFlowNode> nodes,
		@Nonnull List<ShinyFlowEdge> edges) implements ShinyBlock {

	public ShinyFlow {
		Assertion.check()
				.isNotNull(nodes)
				.isNotNull(edges);
	}
}
