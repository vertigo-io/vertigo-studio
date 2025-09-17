package io.vertigo.shiny.components.input.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyInputTextBuilder implements Builder<ShinyInputText> {
	String inputTextLabel;
	boolean inputTextRequired = false;
	Pattern inputTextValidationPattern;
	List<String> inputTextSuggestions = new ArrayList<>();
	String inputTextDefaultValue;
	boolean inputTextSecret = false;

	// No public constructor, use ShinyInputText.builder()
	ShinyInputTextBuilder() {
		// Package-private constructor
	}

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

	@Override
	public ShinyInputText build() {
		Assertion.check()
			.when(inputTextRequired, () -> Assertion.check().isNotBlank(inputTextLabel, "Label is required when input is required."));
		//---
		return new ShinyInputText(this);
	}
}
