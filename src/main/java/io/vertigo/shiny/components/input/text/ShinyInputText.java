package io.vertigo.shiny.components.input.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.vertigo.shiny.ShinyMagicBox;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public record ShinyInputText(
		String label,
		boolean required,
		Pattern validationPattern,
		List<String> suggestions,
		String defaultValue,
		ShinyMagicBox<String> value) implements ShinyComponent {

	// Compact constructor to initialize the mutable field
	public ShinyInputText {
	}

	// Static factory method to get a new Builder instance
	public static ShinyInputTextBuilder builder() {
		return new ShinyInputTextBuilder();
	}

	@Override
	public void render(final ShinyWriter writer) {
		String prompt = label;
		if (required) {
			prompt += ShinyColors.RED.fg(" (required)");
		}
		if (suggestions != null && !suggestions.isEmpty()) {
			prompt += " " + ShinyColors.CYAN.fg(suggestions.toString());
		}
		if (defaultValue != null) {
			prompt += " " + ShinyColors.YELLOW.fg("[" + defaultValue + "]");
		}
		prompt += ": ";

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean validInput = false;
		while (!validInput) {
			writer.print(prompt)
					.flush();

			try {
				String inputLine = reader.readLine();

				if (inputLine == null) {
					value().set(null);
					validInput = true;
				} else {
					value().set(inputLine.trim());

					if (value().get().isEmpty() && defaultValue != null) {
						value().set(defaultValue);
					}

					if (required && value.get().isEmpty()) {
						writer.println(ShinyColors.RED.fg("Input is required."));
					} else if (validationPattern != null && !value.get().isEmpty()) {
						Matcher matcher = validationPattern.matcher(value.get());
						if (!matcher.matches()) {
							writer.println(ShinyColors.RED.fg("Input does not match the required pattern."));
						} else {
							validInput = true;
						}
					} else {
						validInput = true;
					}
				}
			} catch (IOException e) {
				writer.println(ShinyColors.RED.fg("Error reading input: " + e.getMessage()));
				value.set(null);
				validInput = true;
			}
		}
	}
}
