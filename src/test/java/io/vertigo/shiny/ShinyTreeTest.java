package io.vertigo.shiny;

import static io.vertigo.shiny.data.tree.ShinyIcon.ARROW_DOWN;
import static io.vertigo.shiny.data.tree.ShinyIcon.ARROW_LEFT;
import static io.vertigo.shiny.data.tree.ShinyIcon.ARROW_RIGHT;
import static io.vertigo.shiny.data.tree.ShinyIcon.ARROW_UP;
import static io.vertigo.shiny.data.tree.ShinyIcon.CLOCK;
import static io.vertigo.shiny.data.tree.ShinyIcon.DB;
import static io.vertigo.shiny.data.tree.ShinyIcon.ERROR;
import static io.vertigo.shiny.data.tree.ShinyIcon.FILE;
import static io.vertigo.shiny.data.tree.ShinyIcon.FOLDER_CLOSED;
import static io.vertigo.shiny.data.tree.ShinyIcon.FOLDER_OPEN;
import static io.vertigo.shiny.data.tree.ShinyIcon.HEART;
import static io.vertigo.shiny.data.tree.ShinyIcon.INFO;
import static io.vertigo.shiny.data.tree.ShinyIcon.QUESTION;
import static io.vertigo.shiny.data.tree.ShinyIcon.SMILEY;
import static io.vertigo.shiny.data.tree.ShinyIcon.STAR;
import static io.vertigo.shiny.data.tree.ShinyIcon.SUCCESS;
import static io.vertigo.shiny.data.tree.ShinyIcon.USER;
import static io.vertigo.shiny.data.tree.ShinyIcon.WARNING;

import io.vertigo.shiny.color.ShinyColors;

public class ShinyTreeTest {
	public static void main(final String[] args) {
		testVideoGamesTree();
		testIconShowcase();
		testComplexTreeStructure();
	}

	private static void testVideoGamesTree() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Video Games Tree ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Video Games");
		tree.getRoot()
				.addChild("RPG", FOLDER_OPEN)
				.addChild("Final Fantasy", FILE).up()
				.addChild("The Elder Scrolls", FILE);
		tree.getRoot()
				.addChild("FPS", FOLDER_OPEN)
				.addChild("Halo", FILE).up()
				.addChild("Call of Duty", FILE);
		tree.getRoot()
				.addChild("Adventure", FOLDER_OPEN)
				.addChild("The Legend of Zelda", FILE).up()
				.addChild("Uncharted", FILE);

		tree.print();
		System.out.println();
	}

	private static void testIconShowcase() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Icon Showcase ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Icons");
		tree.getRoot()
				.addChild("Status Icons", FOLDER_OPEN)
				.addChild("Success", SUCCESS).up()
				.addChild("Error", ERROR).up()
				.addChild("Warning", WARNING).up()
				.addChild("Info", INFO).up()
				.addChild("Question", QUESTION).up();
		tree.getRoot()
				.addChild("Time & Arrows", FOLDER_OPEN)
				.addChild("Clock", CLOCK).up()
				.addChild("Up", ARROW_UP).up()
				.addChild("Down", ARROW_DOWN).up()
				.addChild("Left", ARROW_LEFT).up()
				.addChild("Right", ARROW_RIGHT).up();
		tree.getRoot()
				.addChild("Misc Icons", FOLDER_OPEN)
				.addChild("Star", STAR).up()
				.addChild("Heart", HEART).up()
				.addChild("Smiley", SMILEY).up();
		tree.print();
		System.out.println();
	}

	private static void testComplexTreeStructure() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Complex Tree Structure ---" + ShinyColors.RESET);
		final var tree = Shiny.tree("Project Structure");
		final var srcNode = tree.getRoot().addChild("src", FOLDER_CLOSED);
		final var main = srcNode.addChild("main", FOLDER_CLOSED);
		final var java = main.addChild("java", FOLDER_CLOSED);
		java.addChild("com.example.app", FOLDER_CLOSED);
		java.addChild("Application.java", FILE);
		final var resources = srcNode.addChild("resources", FOLDER_CLOSED);
		resources.addChild("application.properties", FILE);
		final var testNode = tree.getRoot().addChild("test", FOLDER_CLOSED);
		final var javaTest = testNode.addChild("java", FOLDER_CLOSED);
		javaTest.addChild("com.example.app", FOLDER_CLOSED);
		javaTest.addChild("ApplicationTest.java", FILE);

		tree.getRoot().addChild("pom.xml", FILE);
		tree.getRoot().addChild("database", DB);
		tree.getRoot().addChild("admin", USER);

		tree.print();
		System.out.println();
	}
}
