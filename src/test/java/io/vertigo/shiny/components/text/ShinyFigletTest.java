package io.vertigo.shiny.components.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.text.figlet.ShinyFigletFonts;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyFigletTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testSpecificFonts(writer);
		testAllFonts(writer);
	}

	private static void testSpecificFonts(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing Specific Figlet Fonts ---"));
		Shiny.figlet()
				.text("Vertigo")
				.render(writer); // Uses default font (STANDARD)

		Shiny.figlet()
				.text("Hello")
				.font(ShinyFigletFonts.BIG) // Use BIG font
				.color(ShinyColors.BLUE)
				.render(writer);

		Shiny.figlet()
				.text("World")
				.font(ShinyFigletFonts.SLANT) // Use SLANT font
				.color(ShinyColors.RED)
				.render(writer);

		Shiny.figlet()
				.text("Figlet")
				.font(ShinyFigletFonts.STANDARD) // Use STANDARD font
				.color(ShinyColors.GREEN)
				.render(writer);
		writer.println();
	}

	private static void testAllFonts(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing All Available Figlet Fonts ---"));
		for (final ShinyFigletFonts font : ShinyFigletFonts.values()) {
			writer.println(ShinyColors.CYAN.fg("Font: " + font.name()));
			Shiny.figlet()
					.text(font.name()) // Print the font name using the font itself
					.font(font)
					.color(ShinyColors.GREEN)
					.render(writer);
			writer.println();
		}
	}
}
