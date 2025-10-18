package io.vertigo.shiny.renderers.data;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.tree.ShinyTree;
import io.vertigo.shiny.models.data.tree.ShinyTreeNode;
import io.vertigo.shiny.renderers.ShinyModelRenderer;

public final class ShinyTreeRenderer implements ShinyModelRenderer<ShinyTree> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyTree;
	}

	@Override
	public void render(final ShinyTree shinyTree) {
		Assertion.check()
				.isNotNull(shinyTree);
		//---
		//		final ShinyTreeStyle style = Shiny.theme().treeStyle();
		final ShinyWriter writer = Shiny.writer();

		writer.println(shinyTree.rootNode().getIcon().getValue() + " " + shinyTree.rootNode().getLabel());
		printChildren(writer, "", shinyTree.rootNode().getNodes());
	}

	private static void printChildren(final ShinyWriter writer, final String prefix, final List<ShinyTreeNode> children) {
		for (int i = 0; i < children.size(); i++) {
			final boolean isLast = (i == children.size() - 1);
			final String connection = (isLast ? ShinyTreeChars.BOTTOM_LEFT : ShinyTreeChars.INNER_LEFT) + ShinyTreeChars.HORIZONTAL + ShinyTreeChars.HORIZONTAL;
			final ShinyTreeNode child = children.get(i);
			writer.println(prefix + connection + child.getIcon().getValue() + " " + child.getLabel());
			final List<ShinyTreeNode> grandChildren = child.getNodes();
			final String childPrefix = prefix + (isLast ? "   " : ShinyTreeChars.VERTICAL + "  ");
			printChildren(writer, childPrefix, grandChildren);
		}
	}
}
