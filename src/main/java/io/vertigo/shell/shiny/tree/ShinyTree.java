package io.vertigo.shell.shiny.tree;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.ShinyComponent;

public final class ShinyTree implements ShinyComponent {
	private final Shiny shiny;
	private final ShinyTreeNode rootNode;

	public ShinyTree(final Shiny shiny, final String label) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
		this.rootNode = new ShinyTreeNode(null, label);
	}

	public ShinyTreeNode getRoot() {
		return rootNode;
	}

	public void print() {
		shiny.getWriter().println(rootNode.getIcon().getValue() + " " + rootNode.getLabel());
		printChildren("", rootNode.getNodes());
	}

	private void printChildren(final String prefix, final List<ShinyTreeNode> children) {
		for (int i = 0; i < children.size(); i++) {
			final boolean isLast = (i == children.size() - 1);
			final String connection = (isLast ? ShinyChars.BOTTOM_LEFT : ShinyChars.INNER_LEFT) + ShinyChars.HORIZONTAL + ShinyChars.HORIZONTAL;
			final ShinyTreeNode child = children.get(i);
			shiny.getWriter().println(prefix + connection + child.getIcon().getValue() + " " + child.getLabel());
			final List<ShinyTreeNode> grandChildren = child.getNodes();
			final String childPrefix = prefix + (isLast ? "   " : ShinyChars.VERTICAL + "  ");
			printChildren(childPrefix, grandChildren);
		}
	}

}
