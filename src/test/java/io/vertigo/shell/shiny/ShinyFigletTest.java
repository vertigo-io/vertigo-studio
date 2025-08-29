package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.figlet.ShinyFigletFonts;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyFigletTest {

	public static void main(final String[] args) {
		testSpecificFonts();
		testAllFonts();
	}

	private static void testSpecificFonts() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Testing Specific Figlet Fonts ---" + ShinyColors.RESET);
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
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Testing All Available Figlet Fonts ---" + ShinyColors.RESET);
		for (ShinyFigletFonts font : ShinyFigletFonts.values()) {
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
