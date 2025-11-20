package io.vertigo.shiny.models.dataviz.mindmap;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

public record ShinyMindMapNode(
		@Nonnull String id,
		@Nonnull String topic,
		String background,
		String foreground,
		String direction, // "left" or "right"
		boolean expanded, // "true" or "false"
		@Nonnull List<ShinyMindMapNode> children) {

	public ShinyMindMapNode {
		Assertion.check()
				.isNotBlank(id, "Node ID cannot be blank")
				.isNotBlank(topic, "Node topic cannot be blank")
				.isNotNull(children);
	}
}
