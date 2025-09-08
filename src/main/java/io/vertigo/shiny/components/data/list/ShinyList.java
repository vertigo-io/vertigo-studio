package io.vertigo.shiny.components.data.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyList implements ShinyComponent {
	private String title;
	private final List<Object> listItems = new ArrayList<>();
	private ShinyListStyle style = ShinyListStyle.UNORDERED; // Changed default
	private ShinyColor itemColor = ShinyColors.BLUE_BRIGHT;
	private ShinyColor bulletColor = ShinyColors.CYAN;

	public ShinyList() {
	}

	public ShinyList title(final String text) {
		this.title = text;
		return this;
	}

	public ShinyList addItem(final String item) {
		listItems.add(item);
		return this;
	}

	public ShinyList items(final List<String> items) {
		this.listItems.addAll(items);
		return this;
	}

	public ShinyList addList(final ShinyList nestedList) {
		listItems.add(nestedList);
		return this;
	}

	public ShinyList style(final ShinyListStyle listStyle) {
		this.style = listStyle;
		return this;
	}

	public ShinyList ordered() {
		this.style = ShinyListStyle.ORDERED;
		return this;
	}

	public ShinyList itemColor(final ShinyColor color) {
		this.itemColor = color;
		return this;
	}

	public ShinyList bulletColor(final ShinyColor color) {
		this.bulletColor = color;
		return this;
	}

	public void render(ShinyWriter writer) {
		if (title != null) {
			writer.println(title);
		}
		print(writer, 0); // Start with no indentation
	}

	private void print(final ShinyWriter writer, final int indentLevel) {
		final String indent = "  ".repeat(indentLevel);
		int number = 1;
		for (final Object item : listItems) {
			if (item instanceof String s) {
				final String prefix = getPrefix(number);
				writer.println(indent + bulletColor.fg(prefix) + itemColor.fg(s));
				if (style == ShinyListStyle.ORDERED) { // Changed from NUMBERED
					number++;
				}
			} else if (item instanceof ShinyList list) {
				final String prefix = getPrefix(number);
				writer.println(indent + bulletColor.fg(prefix) + itemColor.fg("Nested List:"));
				list.print(writer, indentLevel + 1); // Recursive call for nested lists
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
