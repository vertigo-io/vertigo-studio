package io.vertigo.shiny.components.input.multiselection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyMultiSelection implements ShinyComponent {
	private final Shiny shiny;
	private String multiselectionTitle;
	private final List<String> multiselectionOptions;
	private Set<Integer> selectedIndices;
	private boolean strictMode = false; // Fallback for non-cursor consoles
	private int maxRetries = 3; // Nombre maximum de tentatives

	public ShinyMultiSelection(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
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

	public ShinyMultiSelection maxRetries(final int retries) {
		Assertion.check().isTrue(retries > 0, "maxRetries must be > 0 ");
		this.maxRetries = Math.max(1, retries);
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
		int attempts = 0;
		boolean success = false;

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (!success && attempts < maxRetries) {
			try {
				displayOptions();
				success = processUserInput(reader);
				if (!success) {
					attempts++;
					if (attempts < maxRetries) {
						shiny.getWriter().println(ShinyColors.RED.fg(
								String.format("Invalid input. Please try again. (%d/%d attempts)",
										attempts, maxRetries)));
						shiny.getWriter().println(); // Ligne vide pour la lisibilité
					}
				}
			} catch (final IOException e) {
				attempts++;
				if (attempts < maxRetries) {
					shiny.getWriter().println(ShinyColors.RED.fg(
							String.format("Error reading input. Please try again. (%d/%d attempts)",
									attempts, maxRetries)));
				} else {
					shiny.getWriter().println(ShinyColors.RED.fg("Maximum attempts reached. Selection cleared."));
					selectedIndices.clear();
				}
			}
		}

		if (!success && attempts >= maxRetries) {
			shiny.getWriter().println(ShinyColors.RED.fg("Maximum attempts reached. Selection cleared."));
			selectedIndices.clear();
		}
	}

	private void displayOptions() {
		shiny.getWriter().println("Please enter the numbers of the options you want to select, separated by commas (e.g., 1,3,5):");
		for (int i = 0; i < multiselectionOptions.size(); i++) {
			final String prefix = selectedIndices.contains(i) ? "[X] " : "[ ] ";
			shiny.getWriter().printf("%s%d. %s%n", prefix, (i + 1), multiselectionOptions.get(i));
		}
		shiny.getWriter().print("> ");
		shiny.getWriter().flush();
	}

	private boolean processUserInput(BufferedReader reader) throws IOException {
		final String input = reader.readLine();
		try {
			if (input == null) {
				return false; // EOF or null input
			}

			final String trimmedInput = input.trim();

			// Permettre une saisie vide pour ne rien sélectionner
			if (trimmedInput.isEmpty()) {
				selectedIndices.clear();
				return true;
			}

			// Parse et valide l'input
			final Set<Integer> newSelectedIndices = new HashSet<>();
			final String[] parts = trimmedInput.split(",");

			for (final String part : parts) {
				final String trimmedPart = part.trim();
				if (trimmedPart.isEmpty()) {
					continue; // Ignore les parties vides
				}

				try {
					final int num = Integer.parseInt(trimmedPart);
					if (num < 1 || num > multiselectionOptions.size()) {
						shiny.getWriter().println(ShinyColors.RED.fg(
								String.format("Number %d is out of range (1-%d)", num, multiselectionOptions.size())));
						return false;
					}
					newSelectedIndices.add(num - 1); // Convert to 0-based index
				} catch (final NumberFormatException e) {
					shiny.getWriter().println(ShinyColors.RED.fg(
							String.format("'%s' is not a valid number", trimmedPart)));
					return false;
				}
			}

			// Si on arrive ici, l'input est valide
			selectedIndices.clear();
			selectedIndices.addAll(newSelectedIndices);
			return true;

		} catch (

		final NumberFormatException e) {
			// Cette exception ne devrait plus arriver avec la logique ci-dessus
			return false;
		}
	}

	private void handleInteractiveMode() {
		shiny.getWriter().println(ShinyColors.YELLOW.fg("Interactive mode not fully implemented yet. Using strict mode fallback."));
		strictMode = true; // Fallback to strict mode
		handleStrictMode();
	}

	public List<String> getSelectedOptions() {
		return selectedIndices.stream()
				.sorted()
				.map(multiselectionOptions::get)
				.collect(Collectors.toList());
	}

	public Set<Integer> getSelectedIndices() {
		return new HashSet<>(selectedIndices);
	}
}
