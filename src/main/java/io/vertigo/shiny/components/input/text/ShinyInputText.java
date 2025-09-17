package io.vertigo.shiny.components.input.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyInputText implements ShinyComponent {
	private final String inputTextLabel;
	private final boolean inputTextRequired;
	private final Pattern inputTextValidationPattern;
	private final List<String> inputTextSuggestions;
	private final String inputTextDefaultValue;
	private final boolean inputTextSecret;
	private String inputTextValue; // Stores the entered value (mutable)

	// Package-private constructor, only accessible by the Builder
	ShinyInputText(ShinyInputTextBuilder builder) {
		Assertion.check()
			.isNotNull(builder);
		//---
		this.inputTextLabel = builder.inputTextLabel;
		this.inputTextRequired = builder.inputTextRequired;
		this.inputTextValidationPattern = builder.inputTextValidationPattern;
		this.inputTextSuggestions = builder.inputTextSuggestions;
		this.inputTextDefaultValue = builder.inputTextDefaultValue;
		this.inputTextSecret = builder.inputTextSecret;
	}

	// Static factory method to get a new Builder instance
	public static ShinyInputTextBuilder builder() {
		return new ShinyInputTextBuilder();
	}

	public String getValue() {
		return inputTextValue;
	}

	@Override
	public void render(final ShinyWriter writer) {
		String prompt = inputTextLabel;
		if (inputTextRequired) {
			prompt += ShinyColors.RED.fg(" (required)");
		}
		if (inputTextSuggestions != null && !inputTextSuggestions.isEmpty()) {
			prompt += " " + ShinyColors.CYAN.fg(inputTextSuggestions.toString());
		}
		if (inputTextDefaultValue != null) {
			prompt += " " + ShinyColors.YELLOW.fg("[" + inputTextDefaultValue + "]");
		}
		prompt += ": ";

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean validInput = false;
		while (!validInput) {
			writer.print(prompt)
					.flush();

			try {
				String inputLine;
				if (inputTextSecret) {
					writer.println(ShinyColors.YELLOW.fg("(Input will be masked, but not truly hidden in this basic console)"));
					inputLine = reader.readLine();
				} else {
					inputLine = reader.readLine();
				}

				if (inputLine == null) {
					inputTextValue = null;
					validInput = true;
				} else {
					inputTextValue = inputLine.trim();

					if (inputTextValue.isEmpty() && inputTextDefaultValue != null) {
						inputTextValue = inputTextDefaultValue;
					}

					if (inputTextRequired && inputTextValue.isEmpty()) {
						writer.println(ShinyColors.RED.fg("Input is required."));
					} else if (inputTextValidationPattern != null && !inputTextValue.isEmpty()) {
						Matcher matcher = inputTextValidationPattern.matcher(inputTextValue);
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
				inputTextValue = null;
				validInput = true;
			}
		}
	}
}
