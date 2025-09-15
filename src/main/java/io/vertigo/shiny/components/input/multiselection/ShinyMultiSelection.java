package io.vertigo.shiny.components.input.multiselection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyMultiSelection implements ShinyComponent {
	private String multiselectionTitle;
	private final List<String> multiselectionOptions;
	private Set<Integer> selectedIndices;

	public ShinyMultiSelection() {
		this.multiselectionOptions = new ArrayList<>();
		this.selectedIndices = new HashSet<>();
	}

	public ShinyMultiSelection title(final String title) {
		this.multiselectionTitle = title;
		return this;
	}

	public ShinyMultiSelection options(final List<String> options) {
		Assertion.check().isNotNull(options);
		this.multiselectionOptions.clear();
		this.multiselectionOptions.addAll(options);
		return this;
	}

	public ShinyMultiSelection options(final String... options) {
		Assertion.check().isNotNull(options);
		return options(Arrays.asList(options));
	}

	public ShinyMultiSelection selected(final List<String> initialSelected) {
		Assertion.check().isNotNull(initialSelected);
		this.selectedIndices.clear();
		for (final String selectedOption : initialSelected) {
			final int index = multiselectionOptions.indexOf(selectedOption);
			if (index != -1) {
				selectedIndices.add(index);
			}
		}
		return this;
	}

	@Override
	public void render(ShinyWriter writer) {
		if (multiselectionTitle != null) {
			writer.println(multiselectionTitle);
		}
		writer.println("Please enter the numbers of the options you want to select, separated by commas (e.g., 1,3,5):");
		for (int i = 0; i < multiselectionOptions.size(); i++) {
			final String prefix = selectedIndices.contains(i) ? "[X] " : "[ ] ";
			writer.printf("%s%d. %s%n", prefix, (i + 1), multiselectionOptions.get(i));
		}
		writer.print("> ");
		writer.flush();

		final var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		try {
			final String input = reader.readLine();
			if (input != null && !input.trim().isEmpty()) {
				selectedIndices.clear();
				Arrays.stream(input.split(","))
						.map(String::trim)
						.filter(s -> !s.isEmpty())
						.map(Integer::parseInt)
						.forEach(num -> {
							if (num > 0 && num <= multiselectionOptions.size()) {
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
				.map(multiselectionOptions::get)
				.collect(Collectors.toList());
	}
}
