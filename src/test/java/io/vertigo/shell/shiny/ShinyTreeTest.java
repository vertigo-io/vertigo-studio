package io.vertigo.shell.shiny;

public class ShinyTreeTest {
	public static void main(final String[] args) {
		printTree();
	}

	private static void printTree() {
		final var tree = Shiny.tree("Video Games");
		tree.getRoot().addNode("RPG")
				.addNode("Final Fantasy")
				.addNode("The Elder Scrolls");
		tree.getRoot().addNode("FPS")
				.addNode("Halo")
				.addNode("Call of Duty");
		tree.getRoot().addNode("Adventure")
				.addNode("The Legend of Zelda")
				.addNode("Uncharted");

		tree.print();
	}
}
