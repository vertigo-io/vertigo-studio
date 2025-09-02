package io.vertigo.shell.shiny;

import static io.vertigo.shell.shiny.color.ShinyColors.GREEN;
import static io.vertigo.shell.shiny.color.ShinyColors.RED;
import static io.vertigo.shell.shiny.color.ShinyColors.RESET;

import io.vertigo.shell.shiny.color.ShinyColors;
import io.vertigo.shell.shiny.data.list.ShinyList;
import io.vertigo.shell.shiny.data.list.ShinyListStyle;

public class ShinyListTest {

	public static void main(final String[] args) {
		testUnorderedList();
		testOrderedList();
		testDashedList();
		testNestedList();
		testColoredList();
	}

	private static void testUnorderedList() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Unordered List ---" + RESET);
		Shiny.list()
				.style(ShinyListStyle.UNORDERED)
				.addItem("Item 1")
				.addItem("Item 2")
				.addItem("Item 3")
				.print();
		System.out.println();
	}

	private static void testOrderedList() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Ordered List ---" + RESET);
		Shiny.list()
				.style(ShinyListStyle.ORDERED)
				.addItem("First item")
				.addItem("Second item")
				.addItem("Third item")
				.print();
		System.out.println();
	}

	private static void testDashedList() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Dashed List ---" + RESET);
		Shiny.list()
				.style(ShinyListStyle.DASHED)
				.addItem("Task A")
				.addItem("Task B")
				.addItem("Task C")
				.print();
		System.out.println();
	}

	private static void testNestedList() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Nested List ---" + RESET);
		final ShinyList nested = Shiny.list()
				.style(ShinyListStyle.UNORDERED)
				.addItem("Sub-item 1")
				.addItem("Sub-item 2");

		Shiny.list()
				.style(ShinyListStyle.ORDERED)
				.addItem("Main Item 1")
				.addList(nested)
				.addItem("Main Item 2")
				.print();
		System.out.println();
	}

	private static void testColoredList() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Colored List ---" + RESET);
		Shiny.list()
				.style(ShinyListStyle.DASHED)
				.itemColor(GREEN)
				.addItem("Uranus")
				.addItem("Saturn")
				.addItem("Venus")
				.bulletColor(RED) // Color the bullets/numbers/dashes
				.print();
		System.out.println();
	}
}
