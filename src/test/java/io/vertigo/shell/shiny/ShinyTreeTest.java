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
				.addChild("RPG", ShinyIcon.FOLDER_OPEN)
				.addChild("Final Fantasy", ShinyIcon.FILE).up()
				.addChild("The Elder Scrolls", ShinyIcon.FILE);
		tree.getRoot()
				.addChild("FPS", ShinyIcon.FOLDER_OPEN)
				.addChild("Halo", ShinyIcon.FILE).up()
				.addChild("Call of Duty", ShinyIcon.FILE);
		tree.getRoot()
				.addChild("Adventure", ShinyIcon.FOLDER_OPEN)
				.addChild("The Legend of Zelda", ShinyIcon.FILE).up()
				.addChild("Uncharted", ShinyIcon.FILE);

		tree.print();
		System.out.println();
	}

	private static void testIconShowcase() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Icon Showcase ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Icons");
		tree.getRoot()
				.addChild("Status Icons", ShinyIcon.FOLDER_OPEN)
				.addChild("Success", ShinyIcon.SUCCESS).up()
				.addChild("Error", ShinyIcon.ERROR).up()
				.addChild("Warning", ShinyIcon.WARNING).up()
				.addChild("Info", ShinyIcon.INFO).up()
				.addChild("Question", ShinyIcon.QUESTION).up();
		tree.getRoot()
				.addChild("Time & Arrows", ShinyIcon.FOLDER_OPEN)
				.addChild("Clock", ShinyIcon.CLOCK).up()
				.addChild("Up", ShinyIcon.ARROW_UP).up()
				.addChild("Down", ShinyIcon.ARROW_DOWN).up()
				.addChild("Left", ShinyIcon.ARROW_LEFT).up()
				.addChild("Right", ShinyIcon.ARROW_RIGHT).up();
		tree.getRoot()
				.addChild("Misc Icons", ShinyIcon.FOLDER_OPEN)
				.addChild("Star", ShinyIcon.STAR).up()
				.addChild("Heart", ShinyIcon.HEART).up()
				.addChild("Smiley", ShinyIcon.SMILEY).up();
		tree.print();
		System.out.println();
	}

	private static void testComplexTreeStructure() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Complex Tree Structure ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Project Structure");
		final var srcNode = tree.getRoot().addChild("src", ShinyIcon.FOLDER_CLOSED);
		var main = srcNode.addChild("main", ShinyIcon.FOLDER_CLOSED);
		var java = main.addChild("java", ShinyIcon.FOLDER_CLOSED);
		java.addChild("com.example.app", ShinyIcon.FOLDER_CLOSED);
		java.addChild("Application.java", ShinyIcon.FILE);
		var resources = srcNode.addChild("resources", ShinyIcon.FOLDER_CLOSED);
		resources.addChild("application.properties", ShinyIcon.FILE);
		final var testNode = tree.getRoot().addChild("test", ShinyIcon.FOLDER_CLOSED);
		var javaTest = testNode.addChild("java", ShinyIcon.FOLDER_CLOSED);
		javaTest.addChild("com.example.app", ShinyIcon.FOLDER_CLOSED);
		javaTest.addChild("ApplicationTest.java", ShinyIcon.FILE);

		tree.getRoot().addChild("pom.xml", ShinyIcon.FILE);
		tree.getRoot().addChild("database", ShinyIcon.DB);
		tree.getRoot().addChild("admin", ShinyIcon.USER);

		tree.print();
		System.out.println();
	}
}
