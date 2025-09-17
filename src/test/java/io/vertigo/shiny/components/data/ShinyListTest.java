package io.vertigo.shiny.components.data;

import static io.vertigo.shiny.style.ShinyColors.GREEN;
import static io.vertigo.shiny.style.ShinyColors.RED;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.data.list.ShinyList;
import io.vertigo.shiny.components.data.list.ShinyListStyle;
import io.vertigo.shiny.components.data.list.ShinyListType;
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
				.withType(ShinyListType.UNORDERED)
				.addItem("Item 1")
				.addItem("Item 2")
				.addItem("Item 3")
				.render(writer);
		writer.println();
	}

	private static void testOrderedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Ordered List ---"));
		Shiny.list()
				.withType(ShinyListType.ORDERED)
				.addItem("First item")
				.addItem("Second item")
				.addItem("Third item")
				.render(writer);
		writer.println();
	}

	private static void testDashedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Dashed List ---"));
		Shiny.list()
				.withType(ShinyListType.DASHED)
				.addItem("Task A")
				.addItem("Task B")
				.addItem("Task C")
				.render(writer);
		writer.println();
	}

	private static void testNestedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Nested List ---"));
		final ShinyList nested = Shiny.list()
				.withType(ShinyListType.UNORDERED)
				.addItem("Sub-item 1")
				.addItem("Sub-item 2");

		Shiny.list()
				.withType(ShinyListType.ORDERED)
				.addItem("Main Item 1")
				.addList(nested)
				.addItem("Main Item 2")
				.render(writer);
		writer.println();
	}

	private static void testColoredList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Colored List ---"));
		Shiny.list()
				.withType(ShinyListType.DASHED)
				.withStyle(new ShinyListStyle()
						// Color the bullets/numbers/dashes
						.withItemColor(GREEN)
						.withBulletColor(RED))
				.addItem("Uranus")
				.addItem("Saturn")
				.addItem("Venus")
				.render(writer);
		writer.println();
	}
}