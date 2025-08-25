package io.vertigo.shell.shiny;

import java.util.List;

public final class ShinyTree {
	private final ShinyTreeNode rootNode;

	public ShinyTree(String label) {
		this.rootNode = new ShinyTreeNode(label);
	}

	public ShinyTreeNode getRoot() {
		return rootNode;
	}

	public void print() {
		System.out.println(rootNode.getLabel());
		printChildren("", rootNode.getNodes());
	}

	private void printChildren(String prefix, List<ShinyTreeNode> children) {
		for (int i = 0; i < children.size(); i++) {
			boolean isLast = (i == children.size() - 1);
			String connection = isLast ? "└── " : "├── ";
			System.out.println(prefix + connection + children.get(i).getLabel());
			List<ShinyTreeNode> grandChildren = children.get(i).getNodes();
			String childPrefix = prefix + (isLast ? "   " : "│  ");
			printChildren(childPrefix, grandChildren);
		}
	}

}
