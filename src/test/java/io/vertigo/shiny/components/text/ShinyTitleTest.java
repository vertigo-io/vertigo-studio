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
				.text("Level 1 Title")
				.level(1)
				.render(writer);
		Shiny.title()
				.text("Level 2 Title")
				.level(2)
				.render(writer);
		Shiny.title()
				.text("Level 3 Title")
				.level(3)
				.render(writer);
		Shiny.title().text("Default Level Title").render(writer); // Default, should be level 1
	}
}
