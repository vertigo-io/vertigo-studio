package io.vertigo.shell.shiny;

import java.util.ArrayList;
import java.util.List;

public final class ShinyNode {
	private String label;
	private List<ShinyNode> nodes = new ArrayList<>();

	public ShinyNode(String label) {
		this.label = label;
	}

	public ShinyNode addNode(String childLabel) {
		ShinyNode childNode = new ShinyNode(childLabel);
		nodes.add(childNode);
		return childNode;
	}

	public List<ShinyNode> getNodes() {
		return nodes;
	}

	public String getLabel() {
		return label;
	}
}
