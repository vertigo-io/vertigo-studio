package io.vertigo.shiny.models.dataviz.mindmap;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyMindMapBuilder implements Builder<ShinyMindMap> {
	private UUID _id;
	private String _title;
	private ShinyMindMapNode _rootNode;

	public ShinyMindMapBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

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
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyMindMap(_id, _title, _rootNode);
	}
}
