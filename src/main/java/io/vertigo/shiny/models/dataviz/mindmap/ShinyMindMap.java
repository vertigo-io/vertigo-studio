package io.vertigo.shiny.models.dataviz.mindmap;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyMindMap(
		String title,
		ShinyMindMapNode rootNode) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyMindMap";
	}

	public ShinyMindMap {
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(rootNode, "Root node cannot be null");
	}
}
