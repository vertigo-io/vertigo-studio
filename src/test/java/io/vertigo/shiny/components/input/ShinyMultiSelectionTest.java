package io.vertigo.shiny.components.input;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelection;

public class ShinyMultiSelectionTest {
	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicMultiSelectionStrict(writer);
		testInitialSelectedMultiSelectionStrict(writer);
	}

	private static void testBasicMultiSelectionStrict(final ShinyWriter writer) {
		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite fruits:")
				.options("Apple", "Banana", "Cherry", "Date")
				.strict(); // Enable strict mode
		multiSelection.render(writer);

		final List<String> selected = multiSelection.getSelectedOptions();
		System.out.println("You have selected " + selected);
	}

	private static void testInitialSelectedMultiSelectionStrict(final ShinyWriter writer) {
		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite colors:")
				.options("Red", "Green", "Blue", "Yellow")
				.selected(List.of("Green", "Yellow"))
				.strict();
		multiSelection.render(writer);

		final List<String> selected = multiSelection.getSelectedOptions();
		System.out.println(selected);
	}

}
