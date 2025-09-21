package io.vertigo.shiny.components.input.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.ShinyMagicBox;

public final class ShinyInputTextBuilder implements Builder<ShinyInputText> {
	private String inputTextLabel;
	private boolean inputTextRequired = false;
	private Pattern inputTextValidationPattern;
	private List<String> inputTextSuggestions = new ArrayList<>();
	private String inputTextDefaultValue;
	private boolean inputTextSecret = false;

	public ShinyInputTextBuilder withLabel(final String label) {
		this.inputTextLabel = label;
		return this;
	}

	public ShinyInputTextBuilder withRequired(final boolean required) {
		this.inputTextRequired = required;
		return this;
	}

	public ShinyInputTextBuilder withPattern(final String regex) {
		Assertion.check().isNotBlank(regex, "Pattern cannot be blank");
		this.inputTextValidationPattern = Pattern.compile(regex);
		return this;
	}

	public ShinyInputTextBuilder addSuggestion(final String suggestion) {
		Assertion.check().isNotNull(suggestion);
		this.inputTextSuggestions.add(suggestion);
		return this;
	}

	public ShinyInputTextBuilder addAllSuggestions(final List<String> suggestions) {
		Assertion.check().isNotNull(suggestions);
		this.inputTextSuggestions.addAll(suggestions);
		return this;
	}

	public ShinyInputTextBuilder withDefaultValue(final String defaultValue) {
		this.inputTextDefaultValue = defaultValue;
		return this;
	}

	public ShinyInputTextBuilder withSecret(final boolean secret) {
		this.inputTextSecret = secret;
		return this;
	}

	// Getters for the fields, needed by the record constructor
	String inputTextLabel() {
		return inputTextLabel;
	}

	boolean inputTextRequired() {
		return inputTextRequired;
	}

	Pattern inputTextValidationPattern() {
		return inputTextValidationPattern;
	}

	List<String> inputTextSuggestions() {
		return inputTextSuggestions;
	}

	String inputTextDefaultValue() {
		return inputTextDefaultValue;
	}

	boolean inputTextSecret() {
		return inputTextSecret;
	}

	@Override
	public ShinyInputText build() {
		Assertion.check()
				.when(inputTextRequired, () -> Assertion.check().isNotBlank(inputTextLabel, "Label is required when input is required."));
		//---
		return new ShinyInputText(
				inputTextLabel,
				inputTextRequired,
				inputTextValidationPattern,
				inputTextSuggestions,
				inputTextDefaultValue,
				new ShinyMagicBox<>());
	}
}
