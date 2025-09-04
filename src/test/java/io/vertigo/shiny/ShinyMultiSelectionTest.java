package io.vertigo.shiny;

import java.util.List;

import io.vertigo.shiny.input.multiselection.ShinyMultiSelection;

public class ShinyMultiSelectionTest {
	public static void main(final String[] args) {
		testBasicMultiSelectionStrict();
		testInitialSelectedMultiSelectionStrict();
	}

	private static void testBasicMultiSelectionStrict() {
		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite fruits:")
				.options("Apple", "Banana", "Cherry", "Date")
				.strict(); // Enable strict mode
		multiSelection.print();

		final List<String> selected = multiSelection.getSelectedOptions();
		System.out.println(selected);
	}

	private static void testInitialSelectedMultiSelectionStrict() {
		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite colors:")
				.options("Red", "Green", "Blue", "Yellow")
				.selected(List.of("Green", "Yellow"))
				.strict();
		multiSelection.print();

		final List<String> selected = multiSelection.getSelectedOptions();
		System.out.println(selected);
	}

}
