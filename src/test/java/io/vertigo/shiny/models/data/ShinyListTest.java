package io.vertigo.shiny.models.data;

import static io.vertigo.shiny.style.ShinyColors.GREEN;
import static io.vertigo.shiny.style.ShinyColors.RED;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.data.list.ShinyList;
import io.vertigo.shiny.models.data.list.ShinyListType;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyListTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = ShinyRenderer.writer();
		testUnorderedList(writer);
		testOrderedList(writer);
		testDashedList(writer);
		testNestedList(writer);
		testColoredList(writer);
	}

	private static void testUnorderedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Unordered List ---"));
		ShinyRenderer.render(
				Shiny.list()
						.withType(ShinyListType.UNORDERED)
						.addItem("Item 1")
						.addItem("Item 2")
						.addItem("Item 3")
						.build());
		writer.println();
	}

	private static void testOrderedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Ordered List ---"));
		ShinyRenderer.render(
				Shiny.list()
						.withType(ShinyListType.ORDERED)
						.addItem("First item")
						.addItem("Second item")
						.addItem("Third item")
						.build());
		writer.println();
	}

	private static void testDashedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Dashed List ---"));
		ShinyRenderer.render(
				Shiny.list()
						.withType(ShinyListType.DASHED)
						.addItem("Task A")
						.addItem("Task B")
						.addItem("Task C")
						.build());
		writer.println();
	}

	private static void testNestedList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Nested List ---"));
		final ShinyList nested = Shiny.list()
				.withType(ShinyListType.UNORDERED)
				.addItem("Sub-item 1")
				.addItem("Sub-item 2")
				.build();

		ShinyRenderer.render(
				Shiny.list()
						.withType(ShinyListType.ORDERED)
						.addItem("Main Item 1")
						.addList(nested)
						.addItem("Main Item 2")
						.build());
		writer.println();
	}

	private static void testColoredList(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Colored List ---"));

		ShinyRenderer.theme().listStyle()
				// Color the bullets/numbers/dashes
				.withItemColor(GREEN)
				.withBulletColor(RED);

		ShinyRenderer.render(
				Shiny.list()
						.withType(ShinyListType.DASHED)
						.addItem("Uranus")
						.addItem("Saturn")
						.addItem("Venus")
						.build());

		writer.println();
	}
}
