package io.vertigo.shell.shiny.tree;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyTreeNode {
	private final ShinyTreeNode parent;
	private final String label;
	private final List<ShinyTreeNode> nodes = new ArrayList<>();
	private ShinyIcon icon = ShinyIcon.NONE;

	ShinyTreeNode(final ShinyTreeNode parent, final String label) {
		this(parent, label, ShinyIcon.NONE);
	}

	ShinyTreeNode(final ShinyTreeNode parent, final String label, final ShinyIcon icon) {
		Assertion.check().isNotBlank(label);
		//---
		this.parent = parent;
		this.label = label;
		this.icon = icon;
	}

	public ShinyTreeNode up() {
		Assertion.check().isNotNull(parent, "you can't up the root");
		return parent;
	}

	public ShinyTreeNode addChild(final String childLabel) {
		return addChild(childLabel, icon);
	}

	public ShinyTreeNode addChild(final String childLabel, final ShinyIcon childIcon) {
		final ShinyTreeNode childNode = new ShinyTreeNode(this, childLabel, childIcon);
		nodes.add(childNode);
		return childNode;
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
