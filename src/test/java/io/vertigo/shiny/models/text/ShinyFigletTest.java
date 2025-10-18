package io.vertigo.shiny.models.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.text.figlet.ShinyFigletFont;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyFigletTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();

		testSpecificFonts(writer);
		testAllFonts(writer);
	}

	private static void testSpecificFonts(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing Specific Figlet Fonts ---"));
		Shiny.render(Shiny.figlet()
				.withText("Vertigo")
				.build()); // Uses default font (STANDARD)

		Shiny.theme().figletStyle()
				.withFont(ShinyFigletFont.BIG) // Use BIG font
				.withColor(ShinyColors.BLUE);

		Shiny.render(
				Shiny.figlet()
						.withText("Hello")
						.build());

		Shiny.theme().figletStyle()
				.withFont(ShinyFigletFont.SLANT) // Use SLANT font
				.withColor(ShinyColors.RED);
		Shiny.render(
				Shiny.figlet()
						.withText("World")
						.build());

		Shiny.theme().figletStyle()
				.withFont(ShinyFigletFont.STANDARD) // Use STANDARD font
				.withColor(ShinyColors.GREEN);

		Shiny.render(
				Shiny.figlet()
						.withText("Figlet")
						.build());
		writer.println();
	}

	private static void testAllFonts(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing All Available Figlet Fonts ---"));

		for (final ShinyFigletFont font : ShinyFigletFont.values()) {
			writer.println(ShinyColors.CYAN.fg("Font: " + font.name()));

			Shiny.theme().figletStyle()
					.withFont(font)
					.withColor(ShinyColors.GREEN);

			Shiny.render(
					Shiny.figlet()
							.withText(font.name()) // Print the font name using the font itself
							.build());
			writer.println();
		}
	}
}
