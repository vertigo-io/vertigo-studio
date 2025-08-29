package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.tree.ShinyIcon;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyTreeTest {
	public static void main(final String[] args) {
		testVideoGamesTree();
		testIconShowcase();
		testComplexTreeStructure();
	}

	private static void testVideoGamesTree() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Video Games Tree ---" + ShinyColors.RESET);
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
		System.out.println();
	}

	private static void testIconShowcase() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Icon Showcase ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Icons");
		tree.getRoot()
				.addNode("Status Icons", ShinyIcon.FOLDER_OPEN)
				.addNode("Success", ShinyIcon.SUCCESS)
				.addNode("Error", ShinyIcon.ERROR)
				.addNode("Warning", ShinyIcon.WARNING)
				.addNode("Info", ShinyIcon.INFO)
				.addNode("Question", ShinyIcon.QUESTION);
		tree.getRoot()
				.addNode("Time & Arrows", ShinyIcon.FOLDER_OPEN)
				.addNode("Clock", ShinyIcon.CLOCK)
				.addNode("Up", ShinyIcon.ARROW_UP)
				.addNode("Down", ShinyIcon.ARROW_DOWN)
				.addNode("Left", ShinyIcon.ARROW_LEFT)
				.addNode("Right", ShinyIcon.ARROW_RIGHT);
		tree.getRoot()
				.addNode("Misc Icons", ShinyIcon.FOLDER_OPEN)
				.addNode("Star", ShinyIcon.STAR)
				.addNode("Heart", ShinyIcon.HEART)
				.addNode("Smiley", ShinyIcon.SMILEY);
		tree.print();
		System.out.println();
	}

	private static void testComplexTreeStructure() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Complex Tree Structure ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Project Structure");
		final var srcNode = tree.getRoot().addNode("src", ShinyIcon.FOLDER_CLOSED);
		srcNode.addNode("main", ShinyIcon.FOLDER_CLOSED)
				.addNode("java", ShinyIcon.FOLDER_CLOSED)
				.addNode("com.example.app", ShinyIcon.FOLDER_CLOSED)
				.addNode("Application.java", ShinyIcon.FILE);
		srcNode.addNode("resources", ShinyIcon.FOLDER_CLOSED)
				.addNode("application.properties", ShinyIcon.FILE);
		final var testNode = tree.getRoot().addNode("test", ShinyIcon.FOLDER_CLOSED);
		testNode.addNode("java", ShinyIcon.FOLDER_CLOSED)
				.addNode("com.example.app", ShinyIcon.FOLDER_CLOSED)
				.addNode("ApplicationTest.java", ShinyIcon.FILE);
		tree.getRoot().addNode("pom.xml", ShinyIcon.FILE);
		tree.getRoot().addNode("database", ShinyIcon.DB);
		tree.getRoot().addNode("admin", ShinyIcon.USER);

		tree.print();
		System.out.println();
	}
}
