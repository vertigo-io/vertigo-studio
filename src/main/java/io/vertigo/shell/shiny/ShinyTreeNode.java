package io.vertigo.shell.shiny;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyTreeNode {
	private final String label;
	private final List<ShinyTreeNode> nodes = new ArrayList<>();

	public ShinyTreeNode(String label) {
		Assertion.check().isNotBlank(label);
		//---
		this.label = label;
	}

	public ShinyTreeNode addNode(String childLabel) {
		final ShinyTreeNode childNode = new ShinyTreeNode(childLabel);
		nodes.add(childNode);
		return childNode;
	}

	public List<ShinyTreeNode> getNodes() {
		return nodes;
	}

	public String getLabel() {
		return label;
	}
}
