package io.vertigo.shiny.components.dataviz.mindmap;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyMindMapNodeBuilder implements Builder<ShinyMindMapNode> {
	private final String _id;
	private final String _topic;
	private String _background;
	private String _foreground;
	private String _direction;
	private boolean _expanded = true;
	private List<ShinyMindMapNode> _children = new ArrayList<>();

	public ShinyMindMapNodeBuilder(String id, String topic) {
		Assertion.check()
				.isNotBlank(id, "Node ID cannot be blank")
				.isNotBlank(topic, "Node topic cannot be blank");
		this._id = id;
		this._topic = topic;
	}

	public ShinyMindMapNodeBuilder withBackground(String background) {
		this._background = background;
		return this;
	}

	public ShinyMindMapNodeBuilder withForeground(String foreground) {
		this._foreground = foreground;
		return this;
	}

	public ShinyMindMapNodeBuilder withDirection(String direction) {
		Assertion.check()
				.isTrue("left".equals(direction) || "right".equals(direction),
						"Direction must be 'left' or 'right'");
		//---
		this._direction = direction;
		return this;
	}

	public ShinyMindMapNodeBuilder withExpanded(boolean expanded) {
		this._expanded = expanded;
		return this;
	}

	public ShinyMindMapNodeBuilder addChild(ShinyMindMapNode child) {
		Assertion.check().isNotNull(child);
		//---
		this._children.add(child);
		return this;
	}

	public ShinyMindMapNodeBuilder addAllchildren(List<ShinyMindMapNode> children) {
		Assertion.check().isNotNull(children);
		//---
		this._children = children;
		return this;
	}

	public ShinyMindMapNodeBuilder addAllChildren(ShinyMindMapNode... children) {
		Assertion.check().isNotNull(children);
		//---
		this._children.addAll(List.of(children));
		return this;
	}

	public ShinyMindMapNode build() {
		return new ShinyMindMapNode(_id, _topic, _background, _foreground, _direction, _expanded, _children);
	}
}
