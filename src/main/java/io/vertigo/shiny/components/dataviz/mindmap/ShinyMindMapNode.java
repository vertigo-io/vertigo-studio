package io.vertigo.shiny.components.dataviz.mindmap;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public record ShinyMindMapNode(
		String id,
		String topic,
		String _background,
		String _foreground,
		String _direction, // "left" or "right"
		String _expanded, // "true" or "false"
		List<ShinyMindMapNode> _children) {

	public ShinyMindMapNode {
		Assertion.check()
				.isNotBlank(id, "Node ID cannot be blank")
				.isNotBlank(topic, "Node topic cannot be blank")
				.isNotNull(_children);
	}

	public static ShinyMindMapNode of(String id, String topic) {
		return new ShinyMindMapNode(id, topic, null, null, null, null, List.of());
	}

	public ShinyMindMapNode withBackground(String background) {
		return new ShinyMindMapNode(id, topic, background, _foreground, _direction, _expanded, _children);
	}

	public ShinyMindMapNode withForeground(String foreground) {
		return new ShinyMindMapNode(id, topic, _background, foreground, _direction, _expanded, _children);
	}

	public ShinyMindMapNode withDirection(String direction) {
		Assertion.check().isTrue("left".equals(direction) || "right".equals(direction), "Direction must be 'left' or 'right'");
		return new ShinyMindMapNode(id, topic, _background, _foreground, direction, _expanded, _children);
	}

	public ShinyMindMapNode withExpanded(boolean expanded) {
		return new ShinyMindMapNode(id, topic, _background, _foreground, _direction, String.valueOf(expanded), _children);
	}

	public ShinyMindMapNode withChildren(List<ShinyMindMapNode> children) {
		Assertion.check().isNotNull(children);
		return new ShinyMindMapNode(id, topic, _background, _foreground, _direction, _expanded, children);
	}

	public ShinyMindMapNode addChildren(ShinyMindMapNode... newChildren) {
		final List<ShinyMindMapNode> newChildrenList = new ArrayList<>(_children);
		newChildrenList.addAll(List.of(newChildren));
		return new ShinyMindMapNode(id, topic, _background, _foreground, _direction, _expanded, newChildrenList);
	}
}
