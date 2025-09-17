package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public record ShinyTitle(
		String title,
		int level) implements ShinyComponent {

	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}

	// Static factory method to get a new Builder instance
	public static ShinyTitleBuilder builder() {
		return new ShinyTitleBuilder();
	}

	public void render(ShinyWriter writer) {
		switch (level) {
			case 1:
				printLevel1(writer);
				break;
			case 2:
				printLevel2(writer);
				break;
			case 3:
				printLevel3(writer);
				break;
			default:
				// Should not happen due to Assertion
				printLevel1(writer);
		}
	}

	private static String CRLF = "\r\n";

	private void printLevel1(ShinyWriter writer) {
		final String line = "=".repeat(title.length() + 4);
		writer.println(ShinyColors.BLUE.fg(
				line + CRLF
						+ "= " + title + " =" + CRLF
						+ line));
	}

	private void printLevel2(ShinyWriter writer) {
		final String line = "-".repeat(title.length() + 4);
		writer.println(ShinyColors.BLUE_BRIGHT.fg(
				line + CRLF
						+ "  " + title + "  " + CRLF
						+ line));
	}

	private void printLevel3(ShinyWriter writer) {
		writer.println(ShinyColors.CYAN.fg(">> " + title + " <<"));
	}
}
