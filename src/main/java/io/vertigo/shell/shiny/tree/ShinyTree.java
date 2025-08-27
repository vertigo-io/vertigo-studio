package io.vertigo.shell.shiny.tree;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;

public final class ShinyTree {
	private final Shiny shiny;
	private final ShinyTreeNode rootNode;

	public ShinyTree(final Shiny shiny, final String label) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
		this.rootNode = new ShinyTreeNode(label);
	}

	public ShinyTreeNode getRoot() {
		return rootNode;
	}

	public void print() {
		shiny.getWriter().println(rootNode.getLabel());
		printChildren("", rootNode.getNodes());
	}

	private void printChildren(final String prefix, final List<ShinyTreeNode> children) {
		for (int i = 0; i < children.size(); i++) {
			final boolean isLast = (i == children.size() - 1);
			final String connection = isLast
					? ShinyChars.BOTTOM_LEFT + ShinyChars.HORIZONTAL + ShinyChars.HORIZONTAL
					: ShinyChars.INNER_LEFT + ShinyChars.HORIZONTAL + ShinyChars.HORIZONTAL;
			shiny.getWriter().println(prefix + connection + children.get(i).getLabel());
			final List<ShinyTreeNode> grandChildren = children.get(i).getNodes();
			final String childPrefix = prefix + (isLast ? "   " : ShinyChars.VERTICAL + "  ");
			printChildren(childPrefix, grandChildren);
		}
	}

}
