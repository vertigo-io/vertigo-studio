package io.vertigo.shiny.renderers.data;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.tree.ShinyTree;
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
		final ShinyWriter writer = Shiny.writer();
		writer.println(shinyTree.label());
		printChildren(writer, "", shinyTree.children());
	}

	private static void printChildren(final ShinyWriter writer, final String prefix, final List<ShinyTree> children) {
		for (int i = 0; i < children.size(); i++) {
			final boolean isLast = (i == children.size() - 1);
			final String connection = (isLast ? "└──" : "├──") + "──";
			final ShinyTree child = children.get(i);
			writer.println(prefix + connection + child.label());
			final List<ShinyTree> grandChildren = child.children();
			final String childPrefix = prefix + (isLast ? "   " : "│  ");
			printChildren(writer, childPrefix, grandChildren);
		}
	}
}
