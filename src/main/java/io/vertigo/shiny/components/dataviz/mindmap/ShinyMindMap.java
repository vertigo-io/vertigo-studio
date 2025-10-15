package io.vertigo.shiny.components.dataviz.mindmap;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyMindMap(
		String title,
		ShinyMindMapNode rootNode) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyMindMap";
	}

	public ShinyMindMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(rootNode, "Root node cannot be null");
	}
}
