package io.vertigo.shiny.components.data.list;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyListRenderer implements ShinyComponentRenderer<ShinyList, ShinyListStyle> {
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyList;
	}

	@Override
	public void render(final ShinyList shinyList, final ShinyListStyle style, ShinyWriter writer) {
		Assertion.check()
				.isNotNull(shinyList)
				.isNotNull(style)
				.isNotNull(writer);
		//---
		if (shinyList.title() != null) {
			writer.println(shinyList.title());
		}
		print(shinyList, style, writer, 0); // Start with no indentation
	}

	private static void print(final ShinyList shinyList, final ShinyListStyle style, final ShinyWriter writer, final int indentLevel) {
		final String indent = "  ".repeat(indentLevel);
		int number = 1;
		for (final Object item : shinyList.items()) {
			if (item instanceof String s) {
				final String prefix = getPrefix(shinyList, number);
				writer.println(indent + style.bulletColor().fg(prefix) + style.itemColor().fg(s));
				if (shinyList.type() == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			} else if (item instanceof ShinyList list) {
				final String prefix = getPrefix(shinyList, number);
				writer.println(indent + style.bulletColor().fg(prefix) + style.itemColor().fg("Nested List:"));
				print(list, style, writer, indentLevel + 1); // Recursive call for nested lists
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
