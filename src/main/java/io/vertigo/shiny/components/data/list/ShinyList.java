package io.vertigo.shiny.components.data.list;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyList(
		String title,
		List<Object> items,
		ShinyListType type,
		@JsonIgnore ShinyListStyle style) implements ShinyComponent {

	public ShinyList {
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
		for (final Object item : items) {
			if (item instanceof String s) {
				final String prefix = getPrefix(number);
				writer.println(indent + style.bulletColor().fg(prefix) + style.itemColor().fg(s));
				if (type == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			} else if (item instanceof ShinyList list) {
				final String prefix = getPrefix(number);
				writer.println(indent + style.bulletColor().fg(prefix) + style.itemColor().fg("Nested List:"));
				list.print(writer, indentLevel + 1); // Recursive call for nested lists
				if (type == ShinyListType.ORDERED) { // Changed from NUMBERED
					number++;
				}
			}
		}
	}

	private String getPrefix(final int number) {
		return switch (type) {
			case UNORDERED -> "• ";
			case ORDERED -> number + ". ";
			case DASHED -> "- ";
		};
	}
}
