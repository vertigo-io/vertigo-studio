package io.vertigo.shiny.renderers.input;

import java.io.IOException;
import java.util.Arrays;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.input.multiselection.ShinyMultiSelection;
import io.vertigo.shiny.renderers.ShinyModelRenderer;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyMultiSelectionRenderer implements ShinyModelRenderer<ShinyMultiSelection> {

	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyMultiSelection;
	}

	@Override
	public void render(final ShinyMultiSelection shinyMultiSelection) {
		Assertion.check()
				.isNotNull(shinyMultiSelection);
		//---
		//		final ShinyMultiSelectionStyle style = Shiny.theme().multiSelectionStyle();
		final ShinyWriter writer = ShinyRenderer.writer();
		if (shinyMultiSelection.title() != null) {
			writer.println(shinyMultiSelection.title());
		}
		writer.println("Please enter the numbers of the options you want to select, separated by commas (e.g., 1,3,5):");
		for (int i = 0; i < shinyMultiSelection.options().size(); i++) {
			final String prefix = shinyMultiSelection.selectedIndices().contains(i) ? "[X] " : "[ ] ";
			writer.printf("%s%d. %s%n", prefix, (i + 1), shinyMultiSelection.options().get(i));
		}
		writer.print("> ");
		writer.flush();

		final var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		try {
			final String input = reader.readLine();
			if (input != null && !input.trim().isEmpty()) {
				shinyMultiSelection.selectedIndices().clear(); // This is a mutable operation on a record component.
				// This is generally discouraged for records.
				// If selectedIndices needs to be mutable, it should be handled outside the record's canonical state.
				// However, the current implementation modifies it directly.
				// For now, I will keep it as is, but it's a potential design flaw for a record.
				Arrays.stream(input.split(","))
						.map(String::trim)
						.filter(s -> !s.isEmpty())
						.map(Integer::parseInt)
						.forEach(num -> {
							if (num > 0 && num <= shinyMultiSelection.options().size()) {
								shinyMultiSelection.selectedIndices().add(num - 1); // Convert to 0-based index
							}
						});
			}
		} catch (final IOException | NumberFormatException e) {
			writer.println(ShinyColors.RED.fg("Invalid input. Please try again."));
			shinyMultiSelection.selectedIndices().clear(); // Clear selection on error
		}
	}
}
