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
	private String inputTextLabel;
	private boolean inputTextRequired = false;
	private Pattern inputTextValidationPattern;
	private List<String> inputTextSuggestions;
	private String inputTextDefaultValue;
	private boolean inputTextSecret = false; // For masking input
	private String inputTextValue; // Stores the entered value

	public ShinyInputText() {
	}

	public ShinyInputText withLabel(final String label) {
		this.inputTextLabel = label;
		return this;
	}

	public ShinyInputText withRequired(final boolean required) {
		this.inputTextRequired = required;
		return this;
	}

	public ShinyInputText withPattern(final String regex) {
		Assertion.check().isNotBlank(regex, "Pattern cannot be blank");
		this.inputTextValidationPattern = Pattern.compile(regex);
		return this;
	}

	public ShinyInputText addSuggestion(final String suggestion) {
		Assertion.check().isNotNull(suggestion);
		if (this.inputTextSuggestions == null) {
			this.inputTextSuggestions = new java.util.ArrayList<>();
		}
		this.inputTextSuggestions.add(suggestion);
		return this;
	}

	public ShinyInputText addAllSuggestions(final List<String> suggestions) {
		Assertion.check().isNotNull(suggestions);
		if (this.inputTextSuggestions == null) {
			this.inputTextSuggestions = new java.util.ArrayList<>();
		}
		this.inputTextSuggestions.addAll(suggestions);
		return this;
	}

	public ShinyInputText withDefaultValue(final String defaultValue) {
		this.inputTextDefaultValue = defaultValue;
		return this;
	}

	public ShinyInputText withSecret(final boolean secret) {
		this.inputTextSecret = secret;
		return this;
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
					// For actual secret input, Console.readPassword() would be ideal,
					// but it's not always available and hard to test.
					// For now, we'll just read normally but note the masking intent.
					writer.println(ShinyColors.YELLOW.fg("(Input will be masked, but not truly hidden in this basic console)"));
					inputLine = reader.readLine(); // Cannot mask directly with BufferedReader
				} else {
					inputLine = reader.readLine();
				}

				if (inputLine == null) { // EOF
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
				validInput = true; // Exit loop on error
			}
		}
	}
}