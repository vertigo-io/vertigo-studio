package io.vertigo.shiny.models.input;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.input.multiselection.ShinyMultiSelection;

public class ShinyMultiSelectionTest {
	public static void main(final String[] args) {
		final ShinyWriter writer = ShinyRenderer.writer();
		testBasicMultiSelectionStrict(writer);
		testInitialSelectedMultiSelectionStrict(writer);
	}

	private static void testBasicMultiSelectionStrict(final ShinyWriter writer) {
		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.withTitle("Choose your favorite fruits:")
				.withOptions("Apple", "Banana", "Cherry", "Date")
				.build();
		ShinyRenderer.render(multiSelection);

		final List<String> selected = multiSelection.getSelectedOptions();
		writer.println("You have selected " + selected);
	}

	private static void testInitialSelectedMultiSelectionStrict(final ShinyWriter writer) {
		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.withTitle("Choose your favorite colors:")
				.withOptions("Red", "Green", "Blue", "Yellow")
				.withSelected(List.of("Green", "Yellow"))
				.build();
		ShinyRenderer.render(multiSelection);

		final List<String> selected = multiSelection.getSelectedOptions();
		writer.println(selected.toString());
	}

}
