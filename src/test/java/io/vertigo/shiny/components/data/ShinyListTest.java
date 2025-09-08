package io.vertigo.shiny.components.data;

import static io.vertigo.shiny.style.ShinyColors.GREEN;
import static io.vertigo.shiny.style.ShinyColors.RED;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.data.list.ShinyList;
import io.vertigo.shiny.components.data.list.ShinyListStyle;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyListTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testUnorderedList(writer);
		testOrderedList(writer);
		testDashedList(writer);
		testNestedList(writer);
		testColoredList(writer);
	}

	private static void testUnorderedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Unordered List ---"));
		Shiny.list()
				.style(ShinyListStyle.UNORDERED)
				.addItem("Item 1")
				.addItem("Item 2")
				.addItem("Item 3")
				.render(writer);
		writer.println();
	}

	private static void testOrderedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Ordered List ---"));
		Shiny.list()
				.style(ShinyListStyle.ORDERED)
				.addItem("First item")
				.addItem("Second item")
				.addItem("Third item")
				.render(writer);
		writer.println();
	}

	private static void testDashedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Dashed List ---"));
		Shiny.list()
				.style(ShinyListStyle.DASHED)
				.addItem("Task A")
				.addItem("Task B")
				.addItem("Task C")
				.render(writer);
		writer.println();
	}

	private static void testNestedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Nested List ---"));
		final ShinyList nested = Shiny.list()
				.style(ShinyListStyle.UNORDERED)
				.addItem("Sub-item 1")
				.addItem("Sub-item 2");

		Shiny.list()
				.style(ShinyListStyle.ORDERED)
				.addItem("Main Item 1")
				.addList(nested)
				.addItem("Main Item 2")
				.render(writer);
		writer.println();
	}

	private static void testColoredList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Colored List ---"));
		Shiny.list()
				.style(ShinyListStyle.DASHED)
				.itemColor(GREEN)
				.addItem("Uranus")
				.addItem("Saturn")
				.addItem("Venus")
				.bulletColor(RED) // Color the bullets/numbers/dashes
				.render(writer);
		writer.println();
	}
}
