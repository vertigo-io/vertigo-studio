package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyParagraphTest {

	public static void main(final String[] args) {
		testParagraph();
	}

	private static void testParagraph() {
		Shiny.paragraph("This is a paragraph.").print();
		Shiny.paragraph("This is a paragraph with " + ShinyColors.BLUE.fg("blue fg") + " and " + ShinyColors.GREEN.bg("green bg")).print();
	}

}
