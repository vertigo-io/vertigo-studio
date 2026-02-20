package io.vertigo.shiny.models.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyParagraphTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = ShinyRenderer.writer();
		testParagraph(writer);
	}

	private static void testParagraph(final ShinyWriter writer) {
		ShinyRenderer.render(
				Shiny.paragraph()
						.withText("This is a paragraph.")
						.build());

		ShinyRenderer.render(
				Shiny.paragraph()
						.withText("This is a paragraph with " + ShinyColors.BLUE.fg("blue fg") + " and " + ShinyColors.GREEN.bg("green bg"))
						.build());
	}

}
