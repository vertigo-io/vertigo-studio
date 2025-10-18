package io.vertigo.shiny.models.dataviz.mindmap;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyMindMapBuilder implements Builder<ShinyMindMap> {
	private String _title;
	private ShinyMindMapNode _rootNode;

	public ShinyMindMapBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
		this._title = title;
		return this;
	}

	public ShinyMindMapBuilder withRootNode(final ShinyMindMapNode rootNode) {
		Assertion.check().isNotNull(rootNode, "Root node cannot be null");
		this._rootNode = rootNode;
		return this;
	}

	public ShinyMindMap build() {
		return new ShinyMindMap(_title, _rootNode);
	}
}
