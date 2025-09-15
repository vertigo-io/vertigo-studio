package io.vertigo.shiny.components.data.list;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyList implements ShinyComponent {
	private String title;
	private final List<Object> listItems = new ArrayList<>();
	private ShinyListType listType = ShinyListType.UNORDERED;
	private ShinyListStyle listStyle;

	public ShinyList() {
		this.listStyle = Shiny.theme().listStyle();
	}

	public ShinyList style(final ShinyListStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.listStyle = style;
		return this;
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

	public ShinyList type(final ShinyListType type) {
		this.listType = type;
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
				writer.println(indent + listStyle.bulletColor().fg(prefix) + listStyle.itemColor().fg(s));
				if (listType == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			} else if (item instanceof ShinyList list) {
				final String prefix = getPrefix(number);
				writer.println(indent + listStyle.bulletColor().fg(prefix) + listStyle.itemColor().fg("Nested List:"));
				list.print(writer, indentLevel + 1); // Recursive call for nested lists
				if (listType == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			}
		}
	}

	private String getPrefix(final int number) {
		return switch (listType) {
			case UNORDERED -> "• ";
			case ORDERED -> number + ". ";
			case DASHED -> "- ";
		};
	}
}
