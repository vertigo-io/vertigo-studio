package io.vertigo.shiny.models.dataviz.mindmap;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyMindMap(
		UUID id,
		String title,
		ShinyMindMapNode rootNode) implements ShinyModel {

	public ShinyMindMap {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(title, "Title cannot be blank")
				.isNotNull(rootNode, "Root node cannot be null");
	}
}
