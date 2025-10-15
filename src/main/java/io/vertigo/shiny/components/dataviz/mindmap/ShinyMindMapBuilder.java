package io.vertigo.shiny.components.dataviz.mindmap;

import io.vertigo.core.lang.Assertion;

public final class ShinyMindMapBuilder {
	private String _title;
	private MindMapNode _rootNode;

	public ShinyMindMapBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
		this._title = title;
		return this;
	}

	public ShinyMindMapBuilder withRootNode(final MindMapNode rootNode) {
		Assertion.check().isNotNull(rootNode, "Root node cannot be null");
		this._rootNode = rootNode;
		return this;
	}

	public ShinyMindMap build() {
		Assertion.check()
				.isNotBlank(_title, "Title is required")
				.isNotNull(_rootNode, "Root node is required");
		return new ShinyMindMap(_title, _rootNode);
	}
}
