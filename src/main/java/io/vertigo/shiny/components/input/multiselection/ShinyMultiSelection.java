package io.vertigo.shiny.components.input.multiselection;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public record ShinyMultiSelection(
		String title,
		List<String> options,
		Set<Integer> selectedIndices) implements ShinyComponent {

	public ShinyMultiSelection {
	}

	// Static factory method to get a new Builder instance
	public static ShinyMultiSelectionBuilder builder() {
		return new ShinyMultiSelectionBuilder();
	}

	@Override
	public void render(ShinyWriter writer) {
		if (title != null) {
			writer.println(title);
		}
		writer.println("Please enter the numbers of the options you want to select, separated by commas (e.g., 1,3,5):");
		for (int i = 0; i < options.size(); i++) {
			final String prefix = selectedIndices.contains(i) ? "[X] " : "[ ] ";
			writer.printf("%s%d. %s%n", prefix, (i + 1), options.get(i));
		}
		writer.print("> ");
		writer.flush();

		final var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		try {
			final String input = reader.readLine();
			if (input != null && !input.trim().isEmpty()) {
				selectedIndices.clear(); // This is a mutable operation on a record component.
				// This is generally discouraged for records.
				// If selectedIndices needs to be mutable, it should be handled outside the record's canonical state.
				// However, the current implementation modifies it directly.
				// For now, I will keep it as is, but it's a potential design flaw for a record.
				Arrays.stream(input.split(","))
						.map(String::trim)
						.filter(s -> !s.isEmpty())
						.map(Integer::parseInt)
						.forEach(num -> {
							if (num > 0 && num <= options.size()) {
								selectedIndices.add(num - 1); // Convert to 0-based index
							}
						});
			}
		} catch (final IOException | NumberFormatException e) {
			writer.println(ShinyColors.RED.fg("Invalid input. Please try again."));
			selectedIndices.clear(); // Clear selection on error
		}
	}

	public List<String> getSelectedOptions() {
		return selectedIndices.stream()
				.sorted()
				.map(options::get)
				.collect(Collectors.toList());
	}
}
