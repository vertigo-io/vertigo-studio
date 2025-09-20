package io.vertigo.shiny.components.data.list;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;

public final class ShinyListRenderer {

	private ShinyListRenderer() {
		//private constructor
	}

	public static void render(final ShinyList shinyList, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyList);
		Assertion.check().isNotNull(writer);
		//---
		if (shinyList.title() != null) {
			writer.println(shinyList.title());
		}
		print(shinyList, writer, 0); // Start with no indentation
	}

	private static void print(final ShinyList shinyList, final ShinyWriter writer, final int indentLevel) {
		final String indent = "  ".repeat(indentLevel);
		int number = 1;
		for (final Object item : shinyList.items()) {
			if (item instanceof String s) {
				final String prefix = getPrefix(shinyList, number);
				writer.println(indent + shinyList.style().bulletColor().fg(prefix) + shinyList.style().itemColor().fg(s));
				if (shinyList.type() == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			} else if (item instanceof ShinyList list) {
				final String prefix = getPrefix(shinyList, number);
				writer.println(indent + shinyList.style().bulletColor().fg(prefix) + shinyList.style().itemColor().fg("Nested List:"));
				print(list, writer, indentLevel + 1); // Recursive call for nested lists
				if (shinyList.type() == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			}
		}
	}

	private static String getPrefix(final ShinyList shinyList, final int number) {
		return switch (shinyList.type()) {
			case UNORDERED -> "• ";
			case ORDERED -> number + ". ";
			case DASHED -> "- ";
		};
	}
}
