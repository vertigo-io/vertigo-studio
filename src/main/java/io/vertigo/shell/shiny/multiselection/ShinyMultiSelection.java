package io.vertigo.shell.shiny.multiselection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.ShinyComponent;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyMultiSelection implements ShinyComponent {
	private final Shiny shiny;
	private String multiselectionTitle;
	private List<String> multiselectionOptions;
	private Set<Integer> selectedIndices;
	private boolean strictMode = false; // Fallback for non-cursor consoles

	public ShinyMultiSelection(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		this.shiny = shiny;
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

	public ShinyMultiSelection strict() {
		this.strictMode = true;
		return this;
	}

	@Override
	public void print() {
		if (multiselectionTitle != null) {
			shiny.getWriter().println(multiselectionTitle);
		}
		if (strictMode) {
			handleStrictMode();
		} else {
			handleInteractiveMode();
		}
	}

	private void handleStrictMode() {
		shiny.getWriter().println("Please enter the numbers of the options you want to select, separated by commas (e.g., 1,3,5):");
		for (int i = 0; i < multiselectionOptions.size(); i++) {
			final String prefix = selectedIndices.contains(i) ? "[X] " : "[ ] ";
			shiny.getWriter().printf("%s%d. %s%n", prefix, (i + 1), multiselectionOptions.get(i));
		}
		shiny.getWriter().print("> ");
		shiny.getWriter().flush();

		try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in))) {
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
			shiny.getWriter().println(ShinyColors.RED + "Invalid input. Please try again." + ShinyColors.RESET);
			selectedIndices.clear(); // Clear selection on error
		}
	}

	private void handleInteractiveMode() {
		shiny.getWriter().println(ShinyColors.YELLOW + "Interactive mode not fully implemented yet. Using strict mode fallback." + ShinyColors.RESET);
		strictMode = true; // Fallback to strict mode
		handleStrictMode();
	}

	public List<String> getSelectedOptions() {
		return selectedIndices.stream()
				.sorted()
				.map(multiselectionOptions::get)
				.collect(Collectors.toList());
	}
}
