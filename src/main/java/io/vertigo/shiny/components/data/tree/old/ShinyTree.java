package io.vertigo.shiny.components.data.tree.old;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTree(
		ShinyTreeNode rootNode) implements ShinyComponent {

	@Override
	public String type() {
		return "tree";
	}


	public ShinyTree {
	}

	public ShinyTreeNode getRoot() {
		return rootNode;
	}
}
