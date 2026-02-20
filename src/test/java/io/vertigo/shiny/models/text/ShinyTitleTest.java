package io.vertigo.shiny.models.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;

public class ShinyTitleTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = ShinyRenderer.writer();

		testTitles(writer);
	}

	private static void testTitles(final ShinyWriter writer) {
		ShinyRenderer.render(
				Shiny.title()
						.withText("Level 1 Title")
						.withLevel(1)
						.build());

		ShinyRenderer.render(
				Shiny.title()
						.withText("Level 2 Title")
						.withLevel(2)
						.build());
		ShinyRenderer.render(
				Shiny.title()
						.withText("Level 3 Title")
						.withLevel(3)
						.build());
		ShinyRenderer.render(
				Shiny.title()
						.withText("Default Level Title")
						.build());
	}
}
