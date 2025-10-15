package io.vertigo.shiny.components.dataviz.mindmap;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record ShinyMindMapNode(
		String id,
		String topic,
		String background,
		String foreground,
		String direction, // "left" or "right"
		boolean expanded, // "true" or "false"
		List<ShinyMindMapNode> children) {

	public ShinyMindMapNode {
		Assertion.check()
				.isNotBlank(id, "Node ID cannot be blank")
				.isNotBlank(topic, "Node topic cannot be blank")
				.isNotNull(children);
	}
}
