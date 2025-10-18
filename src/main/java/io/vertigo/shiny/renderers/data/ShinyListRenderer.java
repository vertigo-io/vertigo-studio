package io.vertigo.shiny.renderers.data;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.data.list.ShinyList;
import io.vertigo.shiny.models.data.list.ShinyListType;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyListRenderer implements ShinyComponentRenderer<ShinyList> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyList;
	}

	@Override
	public void render(final ShinyList shinyList) {
		Assertion.check()
				.isNotNull(shinyList);
		//---
		final ShinyListStyle style = Shiny.theme().listStyle();
		ShinyWriter writer = Shiny.writer();

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
				if (shinyList.listType() == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			} else if (item instanceof ShinyList list) {
				final String prefix = getPrefix(shinyList, number);
				writer.println(indent + style.bulletColor().fg(prefix) + style.itemColor().fg("Nested List:"));
				print(list, style, writer, indentLevel + 1); // Recursive call for nested lists
				if (shinyList.listType() == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			}
		}
	}

	private static String getPrefix(final ShinyList shinyList, final int number) {
		return switch (shinyList.listType()) {
			case UNORDERED -> "• ";
			case ORDERED -> number + ". ";
			case DASHED -> "- ";
		};
	}
}
