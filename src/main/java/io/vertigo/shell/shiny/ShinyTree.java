package io.vertigo.shell.shiny;

import java.util.List;

public final class ShinyTree {
	private final ShinyNode rootNode;

	public ShinyTree(String label) {
		this.rootNode = new ShinyNode(label);
	}

	public ShinyNode getRoot() {
		return rootNode;
	}

	public void print() {
		System.out.println(rootNode.getLabel());
		printChildren("", rootNode.getNodes());
	}

	private void printChildren(String prefix, List<ShinyNode> children) {
		for (int i = 0; i < children.size(); i++) {
			boolean isLast = (i == children.size() - 1);
			String connection = isLast ? "└── " : "├── ";
			System.out.println(prefix + connection + children.get(i).getLabel());
			List<ShinyNode> grandChildren = children.get(i).getNodes();
			String childPrefix = prefix + (isLast ? "   " : "│  ");
			printChildren(childPrefix, grandChildren);
		}
	}

}
