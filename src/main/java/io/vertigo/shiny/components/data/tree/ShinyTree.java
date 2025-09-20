package io.vertigo.shiny.components.data.tree;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTree(
		ShinyTreeNode rootNode) implements ShinyComponent {

	public ShinyTree {
	}

	// Static factory method to get a new Builder instance
	public static ShinyTreeBuilder builder() {
		return new ShinyTreeBuilder();
	}

	public ShinyTreeNode getRoot() {
		return rootNode;
	}

	public void render(final ShinyWriter writer) {
		new ShinyTreeRenderer().render(this, writer);
	}

}
