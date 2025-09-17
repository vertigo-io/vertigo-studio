package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyTitle implements ShinyComponent {
	private final String title;
	private final int level;

	// Package-private constructor, only accessible by the Builder
	ShinyTitle(ShinyTitleBuilder builder) {
		Assertion.check()
			.isNotNull(builder);
		//---
		this.title = builder.title;
		this.level = builder.level;
	}

	// Static factory method to get a new Builder instance
	public static ShinyTitleBuilder builder() {
		return new ShinyTitleBuilder();
	}

	public void render(ShinyWriter writer) {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
		//---
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
