package io.vertigo.shiny.components.data.tree;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyTree implements ShinyComponent {
	private final ShinyTreeNode rootNode;

	// Package-private constructor, only accessible by the Builder
	ShinyTree(ShinyTreeBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.rootNode = builder.treeRootNode;
	}

	// Static factory method to get a new Builder instance
	public static ShinyTreeBuilder builder() {
		return new ShinyTreeBuilder();
	}

	public ShinyTreeNode getRoot() {
		return rootNode;
	}

	public void render(ShinyWriter writer) {
		writer.println(rootNode.getIcon().getValue() + " " + rootNode.getLabel());
		printChildren(writer, "", rootNode.getNodes());
	}

	private void printChildren(ShinyWriter writer, final String prefix, final List<ShinyTreeNode> children) {
		for (int i = 0; i < children.size(); i++) {
			final boolean isLast = (i == children.size() - 1);
			final String connection = (isLast ? ShinyChars.BOTTOM_LEFT : ShinyChars.INNER_LEFT) + ShinyChars.HORIZONTAL + ShinyChars.HORIZONTAL;
			final ShinyTreeNode child = children.get(i);
			writer.println(prefix + connection + child.getIcon().getValue() + " " + child.getLabel());
			final List<ShinyTreeNode> grandChildren = child.getNodes();
			final String childPrefix = prefix + (isLast ? "   " : ShinyChars.VERTICAL + "  ");
			printChildren(writer, childPrefix, grandChildren);
		}
	}

}
