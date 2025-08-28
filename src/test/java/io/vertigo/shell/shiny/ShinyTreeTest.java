package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.tree.ShinyIcon;

public class ShinyTreeTest {
	public static void main(final String[] args) {
		printTree();
		printIcons();
	}

	private static void printTree() {
		final var tree = Shiny.tree("Video Games");
		tree.getRoot()
				.addNode("RPG", ShinyIcon.FOLDER_OPEN)
				.addNode("Final Fantasy", ShinyIcon.FILE)
				.addNode("The Elder Scrolls", ShinyIcon.FILE);
		tree.getRoot()
				.addNode("FPS", ShinyIcon.FOLDER_OPEN)
				.addNode("Halo", ShinyIcon.FILE)
				.addNode("Call of Duty", ShinyIcon.FILE);
		tree.getRoot()
				.addNode("Adventure", ShinyIcon.FOLDER_OPEN)
				.addNode("The Legend of Zelda", ShinyIcon.FILE)
				.addNode("Uncharted", ShinyIcon.FILE);

		tree.print();
	}

	private static void printIcons() {
		final var tree = Shiny.tree("Icons");
		tree.getRoot()
				.addNode("Success", ShinyIcon.SUCCESS)
				.addNode("Error", ShinyIcon.ERROR)
				.addNode("Warning", ShinyIcon.WARNING)
				.addNode("Info", ShinyIcon.INFO)
				.addNode("Question", ShinyIcon.QUESTION)
				.addNode("Clock", ShinyIcon.CLOCK)
				.addNode("Arrows", ShinyIcon.FOLDER_OPEN)
				.addNode("Up", ShinyIcon.ARROW_UP)
				.addNode("Down", ShinyIcon.ARROW_DOWN)
				.addNode("Left", ShinyIcon.ARROW_LEFT)
				.addNode("Right", ShinyIcon.ARROW_RIGHT)
				.addNode("Star", ShinyIcon.STAR)
				.addNode("Heart", ShinyIcon.HEART)
				.addNode("Smiley", ShinyIcon.SMILEY);
		tree.print();
	}
}
