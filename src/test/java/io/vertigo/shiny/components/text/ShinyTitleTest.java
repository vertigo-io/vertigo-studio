package io.vertigo.shiny.components.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;

public class ShinyTitleTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();

		testTitles(writer);
	}

	private static void testTitles(final ShinyWriter writer) {
		Shiny.title()
				.withText("Level 1 Title")
				.withLevel(1)
				.build()
				.render(writer);
		Shiny.title()
				.withText("Level 2 Title")
				.withLevel(2)
				.build()
				.render(writer);
		Shiny.title()
				.withText("Level 3 Title")
				.withLevel(3)
				.build()
				.render(writer);
		Shiny.title()
				.withText("Default Level Title")
				.build()
				.render(writer); // Default, should be level 1
	}
}
