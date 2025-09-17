package io.vertigo.shiny.components.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.text.figlet.ShinyFigletFont;
import io.vertigo.shiny.components.text.figlet.ShinyFigletStyle;
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
				.withText("Vertigo")
				.render(writer); // Uses default font (STANDARD)

		Shiny.figlet()
				.withText("Hello")
				.withStyle(new ShinyFigletStyle()
						.withFont(ShinyFigletFont.BIG) // Use BIG font
						.withColor(ShinyColors.BLUE))
				.render(writer);

		Shiny.figlet()
				.withText("World")
				.withStyle(new ShinyFigletStyle()
						.withFont(ShinyFigletFont.SLANT) // Use SLANT font
						.withColor(ShinyColors.RED))
				.render(writer);

		Shiny.figlet()
				.withText("Figlet")
				.withStyle(new ShinyFigletStyle()
						.withFont(ShinyFigletFont.STANDARD) // Use STANDARD font
						.withColor(ShinyColors.GREEN))
				.render(writer);
		writer.println();
	}

	private static void testAllFonts(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing All Available Figlet Fonts ---"));
		for (final ShinyFigletFont font : ShinyFigletFont.values()) {
			writer.println(ShinyColors.CYAN.fg("Font: " + font.name()));
			Shiny.figlet()
					.withText(font.name()) // Print the font name using the font itself
					.withStyle(new ShinyFigletStyle()
							.withFont(font)
							.withColor(ShinyColors.GREEN))
					.render(writer);
			writer.println();
		}
	}
}