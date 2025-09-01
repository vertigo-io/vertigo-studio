package io.vertigo.shell.shiny.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyList {
	private final Shiny shiny;
	private final List<Object> items = new ArrayList<>();
	private ShinyListStyle style = ShinyListStyle.UNORDERED; // Changed default
	private String itemColor = ShinyColors.WHITE;
	private String bulletColor = ShinyColors.CYAN;

	public ShinyList(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyList addItem(final String item) {
		items.add(item);
		return this;
	}

	public ShinyList addList(final ShinyList nestedList) {
		items.add(nestedList);
		return this;
	}

	public ShinyList style(final ShinyListStyle listStyle) {
		this.style = listStyle;
		return this;
	}

	public ShinyList itemColor(final String color) {
		this.itemColor = color;
		return this;
	}

	public ShinyList bulletColor(final String color) {
		this.bulletColor = color;
		return this;
	}

	public void print() {
		print(0); // Start with no indentation
	}

	private void print(final int indentLevel) {
		final String indent = "  ".repeat(indentLevel);
		int number = 1;
		for (final Object item : items) {
			if (item instanceof String) {
				final String prefix = getPrefix(number);
				shiny.getWriter().println(indent + bulletColor + prefix + ShinyColors.RESET + itemColor + item + ShinyColors.RESET);
				if (style == ShinyListStyle.ORDERED) { // Changed from NUMBERED
					number++;
				}
			} else if (item instanceof ShinyList) {
				final String prefix = getPrefix(number);
				shiny.getWriter().println(indent + bulletColor + prefix + ShinyColors.RESET + itemColor + "Nested List:" + ShinyColors.RESET);
				((ShinyList) item).print(indentLevel + 1); // Recursive call for nested lists
				if (style == ShinyListStyle.ORDERED) { // Changed from NUMBERED
					number++;
				}
			}
		}
	}

	private String getPrefix(final int number) {
		return switch (style) {
			case UNORDERED -> "• ";
			case ORDERED -> number + ". ";
			case DASHED -> "- ";
		};
	}

}
