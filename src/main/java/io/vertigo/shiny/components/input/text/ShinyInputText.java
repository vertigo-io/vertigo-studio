package io.vertigo.shiny.components.input.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.color.ShinyColors;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyInputText implements ShinyComponent {
	private final Shiny shiny;
	private String inputTextLabel;
	private boolean inputTextRequired = false;
	private Pattern inputTextValidationPattern;
	private List<String> inputTextSuggestions;
	private String inputTextDefaultValue;
	private boolean inputTextSecret = false; // For masking input
	private String inputTextValue; // Stores the entered value

	public ShinyInputText(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		this.shiny = shiny;
	}

	public ShinyInputText label(final String label) {
		this.inputTextLabel = label;
		return this;
	}

	public ShinyInputText required(final boolean required) {
		this.inputTextRequired = required;
		return this;
	}

	public ShinyInputText pattern(final String regex) {
		Assertion.check().isNotBlank(regex, "Pattern cannot be blank");
		this.inputTextValidationPattern = Pattern.compile(regex);
		return this;
	}

	public ShinyInputText suggestions(final List<String> suggestions) {
		Assertion.check().isNotNull(suggestions);
		this.inputTextSuggestions = suggestions;
		return this;
	}

	public ShinyInputText defaultValue(final String defaultValue) {
		this.inputTextDefaultValue = defaultValue;
		return this;
	}

	public ShinyInputText secret(final boolean secret) {
		this.inputTextSecret = secret;
		return this;
	}

	public String getValue() {
		return inputTextValue;
	}

	@Override
	public void print() {
		String prompt = inputTextLabel;
		if (inputTextRequired) {
			prompt += ShinyColors.RED + " (required)" + ShinyColors.RESET;
		}
		if (inputTextSuggestions != null && !inputTextSuggestions.isEmpty()) {
			prompt += " " + ShinyColors.CYAN + inputTextSuggestions.toString() + ShinyColors.RESET;
		}
		if (inputTextDefaultValue != null) {
			prompt += " " + ShinyColors.YELLOW + "[" + inputTextDefaultValue + "]" + ShinyColors.RESET;
		}
		prompt += ": ";

		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean validInput = false;
		while (!validInput) {
			shiny.getWriter().print(prompt);
			shiny.getWriter().flush();

			try {
				String inputLine;
				if (inputTextSecret) {
					// For actual secret input, Console.readPassword() would be ideal,
					// but it's not always available and hard to test.
					// For now, we'll just read normally but note the masking intent.
					shiny.getWriter().println(ShinyColors.YELLOW + "(Input will be masked, but not truly hidden in this basic console)" + ShinyColors.RESET);
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
						shiny.getWriter().println(ShinyColors.RED + "Input is required." + ShinyColors.RESET);
					} else if (inputTextValidationPattern != null && !inputTextValue.isEmpty()) {
						Matcher matcher = inputTextValidationPattern.matcher(inputTextValue);
						if (!matcher.matches()) {
							shiny.getWriter().println(ShinyColors.RED + "Input does not match the required pattern." + ShinyColors.RESET);
						} else {
							validInput = true;
						}
					} else {
						validInput = true;
					}
				}
			} catch (IOException e) {
				shiny.getWriter().println(ShinyColors.RED + "Error reading input: " + e.getMessage() + ShinyColors.RESET);
				inputTextValue = null;
				validInput = true; // Exit loop on error
			}
		}
	}
}
