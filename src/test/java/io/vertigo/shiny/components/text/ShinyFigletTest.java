package io.vertigo.shiny.components.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.text.figlet.ShinyFigletFonts;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyFigletTest {

	public static void main(final String[] args) {
		testSpecificFonts();
		testAllFonts();
	}

	private static void testSpecificFonts() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing Specific Figlet Fonts ---"));
		Shiny.figlet()
				.text("Vertigo")
				.print(); // Uses default font (STANDARD)

		Shiny.figlet()
				.text("Hello")
				.font(ShinyFigletFonts.BIG) // Use BIG font
				.color(ShinyColors.BLUE)
				.print();

		Shiny.figlet()
				.text("World")
				.font(ShinyFigletFonts.SLANT) // Use SLANT font
				.color(ShinyColors.RED)
				.print();

		Shiny.figlet()
				.text("Figlet")
				.font(ShinyFigletFonts.STANDARD) // Use STANDARD font
				.color(ShinyColors.GREEN)
				.print();
		System.out.println();
	}

	private static void testAllFonts() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing All Available Figlet Fonts ---"));
		for (final ShinyFigletFonts font : ShinyFigletFonts.values()) {
			System.out.println(ShinyColors.CYAN + "Font: " + font.name() + ShinyColors.RESET);
			Shiny.figlet()
					.text(font.name()) // Print the font name using the font itself
					.font(font)
					.color(ShinyColors.GREEN)
					.print();
			System.out.println();
		}
	}
}
