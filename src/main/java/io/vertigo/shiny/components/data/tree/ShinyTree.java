package io.vertigo.shiny.components.data.tree;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTree(
		ShinyTreeNode rootNode) implements ShinyComponent {

	public ShinyTree {
	}

	public ShinyTreeNode getRoot() {
		return rootNode;
	}
}
