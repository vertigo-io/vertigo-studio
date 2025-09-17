package io.vertigo.shiny.components.data.list;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyList implements ShinyComponent {
	private final String title;
	private final List<Object> listItems;
	private final ShinyListType listType;
	private final ShinyListStyle listStyle;

	// Package-private constructor, only accessible by the Builder
	ShinyList(ShinyListBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.title = builder.title;
		this.listItems = builder.listItems;
		this.listType = builder.listType;
		this.listStyle = builder.listStyle;
	}

	// Static factory method to get a new Builder instance
	public static ShinyListBuilder builder() {
		return new ShinyListBuilder();
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
