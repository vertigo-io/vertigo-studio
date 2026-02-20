package io.vertigo.shiny.models.data;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyTreeTest {
	public static void main(final String[] args) {
		testVideoGamesTree();
	}

	private static void testVideoGamesTree() {
		ShinyRenderer.writer().println(ShinyColors.BLUE_BRIGHT.fg("--- Video Games Tree ---"));
		final var treeBuilder = Shiny.tree().withLabel("Video Games");
		treeBuilder.addTree("RPG")
				.addLeaf("Final Fantasy")
				.addLeaf("The Elder Scrolls");
		treeBuilder.addTree("FPS")
				.addLeaf("Halo")
				.addLeaf("Call of Duty");
		treeBuilder.addTree("Adventure")
				.addLeaf("The Legend of Zelda")
				.addLeaf("Uncharted");

		ShinyRenderer.render(treeBuilder.build());
		ShinyRenderer.writer().println();
	}
}
