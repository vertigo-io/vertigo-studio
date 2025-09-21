package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.renderers.ShinyComponentRenderer;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyTitleRenderer implements ShinyComponentRenderer<ShinyTitle> {
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyTitle;
	}

	@Override
	public void render(final ShinyTitle shinyTitle, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyTitle);
		Assertion.check().isNotNull(writer);
		//---
		switch (shinyTitle.level()) {
			case 1:
				printLevel1(shinyTitle, writer);
				break;
			case 2:
				printLevel2(shinyTitle, writer);
				break;
			case 3:
				printLevel3(shinyTitle, writer);
				break;
			default:
				// Should not happen due to Assertion
				printLevel1(shinyTitle, writer);
		}
	}

	private final String CRLF = "\r\n"; // Not static

	private void printLevel1(final ShinyTitle shinyTitle, final ShinyWriter writer) { // Not static
		final String line = "=".repeat(shinyTitle.title().length() + 4);
		writer.println(ShinyColors.BLUE.fg(
				line + CRLF
						+ "= " + shinyTitle.title() + " =" + CRLF
						+ line));
	}

	private void printLevel2(final ShinyTitle shinyTitle, final ShinyWriter writer) { // Not static
		final String line = "-".repeat(shinyTitle.title().length() + 4);
		writer.println(ShinyColors.BLUE_BRIGHT.fg(
				line + CRLF
						+ "  " + shinyTitle.title() + "  " + CRLF
						+ line));
	}

	private void printLevel3(final ShinyTitle shinyTitle, final ShinyWriter writer) { // Not static
		writer.println(ShinyColors.CYAN.fg(">> " + shinyTitle.title() + " <<"));
	}
}
