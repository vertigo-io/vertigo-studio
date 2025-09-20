package io.vertigo.shiny.components.input.multiselection;

import java.io.IOException;
import java.util.Arrays;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyComponentRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyMultiSelectionRenderer implements ShinyComponentRenderer<ShinyMultiSelection> { // Implements interface

	public ShinyMultiSelectionRenderer() { // Public no-arg constructor
		//private constructor
	}

	@Override // Override annotation
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyMultiSelection;
	}

	@Override // Override annotation
	public void render(final ShinyMultiSelection shinyMultiSelection, final ShinyWriter writer) { // Not static
		Assertion.check().isNotNull(shinyMultiSelection);
		Assertion.check().isNotNull(writer);
		//---
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
