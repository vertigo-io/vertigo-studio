package io.vertigo.shell.shiny.tree;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyTreeNode {
	private final String label;
	private final List<ShinyTreeNode> nodes = new ArrayList<>();
	private ShinyIcon icon = ShinyIcon.NONE;

	ShinyTreeNode(final String label) {
		this(label, ShinyIcon.NONE);
	}

	ShinyTreeNode(final String label, final ShinyIcon icon) {
		Assertion.check().isNotBlank(label);
		//---
		this.label = label;
		this.icon = icon;
	}

	public ShinyTreeNode addNode(final String childLabel) {
		final ShinyTreeNode childNode = new ShinyTreeNode(childLabel);
		nodes.add(childNode);
		return childNode;
	}

	public ShinyTreeNode addNode(final String childLabel, final ShinyIcon childIcon) {
		final ShinyTreeNode childNode = new ShinyTreeNode(childLabel, childIcon);
		nodes.add(childNode);
		return this;
	}

	public List<ShinyTreeNode> getNodes() {
		return nodes;
	}

	public String getLabel() {
		return label;
	}

	public ShinyIcon getIcon() {
		return icon;
	}
}
