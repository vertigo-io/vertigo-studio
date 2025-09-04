package io.vertigo.shiny.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyComponent;
import io.vertigo.shiny.color.ShinyColors;

public final class ShinyTitle implements ShinyComponent {
	private final Shiny shiny;
	private String title;
	private int level = 1; // Default to Level 1

	public ShinyTitle(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyTitle text(final String text) {
		this.title = text;
		return this;
	}

	public ShinyTitle level(final int titleLevel) {
		Assertion.check()
				.isTrue(titleLevel >= 1 && titleLevel <= 3, "Title level must be between 1 and 3");
		this.level = titleLevel;
		return this;
	}

	public void print() {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
		//---
		switch (level) {
			case 1:
				printLevel1();
				break;
			case 2:
				printLevel2();
				break;
			case 3:
				printLevel3();
				break;
			default:
				// Should not happen due to Assertion
				printLevel1();
		}
	}

	private void printLevel1() {
		final String line = "=".repeat(title.length() + 4);
		shiny.getWriter().print(ShinyColors.BLUE);
		shiny.getWriter().println(line);
		shiny.getWriter().println("= " + title + " =");
		shiny.getWriter().println(line);
		shiny.getWriter().print(ShinyColors.RESET);
		shiny.getWriter().println();
	}

	private void printLevel2() {
		final String line = "-".repeat(title.length() + 4);
		shiny.getWriter().print(ShinyColors.BLUE.bright());
		shiny.getWriter().println(line);
		shiny.getWriter().println("  " + title + "  ");
		shiny.getWriter().println(line);
		shiny.getWriter().print(ShinyColors.RESET);
		shiny.getWriter().println();
	}

	private void printLevel3() {
		shiny.getWriter().print(ShinyColors.CYAN);
		shiny.getWriter().println(">> " + title + " <<");
		shiny.getWriter().print(ShinyColors.RESET);
		shiny.getWriter().println();
	}
}
