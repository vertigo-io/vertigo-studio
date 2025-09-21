package io.vertigo.shiny.components.data.tree;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyTreeRenderer implements ShinyComponentRenderer<ShinyTree> {
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyTree;
	}

	@Override
	public void render(final ShinyTree shinyTree, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyTree);
		Assertion.check().isNotNull(writer);
		//---
		writer.println(shinyTree.rootNode().getIcon().getValue() + " " + shinyTree.rootNode().getLabel());
		printChildren(writer, "", shinyTree.rootNode().getNodes());
	}

	private static void printChildren(final ShinyWriter writer, final String prefix, final List<ShinyTreeNode> children) {
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
