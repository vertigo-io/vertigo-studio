package io.vertigo.shiny.components.data.tree.old;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTreeBuilder implements Builder<ShinyTree> {
	private String treeLabel;
	private ShinyTreeNode treeRootNode;

	public ShinyTreeBuilder withLabel(final String label) {
		this.treeLabel = label;
		return this;
	}

	public ShinyTreeBuilder withRootNode(final ShinyTreeNode rootNode) {
		Assertion.check().isNotNull(rootNode);
		this.treeRootNode = rootNode;
		return this;
	}

	@Override
	public ShinyTree build() {
		Assertion.check().isNotBlank(treeLabel, "Label is required for ShinyTree");
		// If rootNode is not set, create a default one
		if (treeRootNode == null) {
			treeRootNode = new ShinyTreeNode(null, treeLabel);
		}
		//---
		return new ShinyTree(treeRootNode);
	}
}
