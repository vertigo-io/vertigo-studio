package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.figlet.ShinyFigletFonts;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyFigletTest {

	public static void main(final String[] args) {
		test();
	}

	private static void test() {
		Shiny.figlet()
				.text("Vertigo")
				.print();

		Shiny.figlet()
				.text("Hello")
				.font(ShinyFigletFonts.BIG)
				.color(ShinyColors.BLUE)
				.print();

		Shiny.figlet()
				.text("World")
				.font(ShinyFigletFonts.SLANT)
				.color(ShinyColors.RED)
				.print();

		Shiny.figlet()
				.text("Figlet")
				.font(ShinyFigletFonts.STANDARD)
				.color(ShinyColors.GREEN)
				.print();

		for (ShinyFigletFonts font : ShinyFigletFonts.values()) {
			Shiny.figlet()
					.text(font.name())
					.font(font)
					.color(ShinyColors.GREEN)
					.print();
		}
	}
}
