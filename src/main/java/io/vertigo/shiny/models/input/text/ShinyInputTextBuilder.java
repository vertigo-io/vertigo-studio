package io.vertigo.shiny.models.input.text;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.ShinyMagicBox;

public final class ShinyInputTextBuilder implements Builder<ShinyInputText> {
	private UUID _id;
	private String _label;
	private boolean _required = false;
	private Pattern _validationPattern;
	private List<String> _suggestions = new ArrayList<>();
	private String _defaultValue;
	private boolean _secret = false;

	public ShinyInputTextBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyInputTextBuilder withLabel(final String label) {
		Assertion.check().isNotBlank(label);
		this._label = label;
		return this;
	}

	public ShinyInputTextBuilder withRequired(final boolean required) {
		this._required = required;
		return this;
	}

	public ShinyInputTextBuilder withPattern(final String regex) {
		Assertion.check().isNotBlank(regex, "Pattern cannot be blank");
		this._validationPattern = Pattern.compile(regex);
		return this;
	}

	public ShinyInputTextBuilder addSuggestion(final String suggestion) {
		Assertion.check().isNotNull(suggestion);
		this._suggestions.add(suggestion);
		return this;
	}

	public ShinyInputTextBuilder addAllSuggestions(final List<String> suggestions) {
		Assertion.check().isNotNull(suggestions);
		this._suggestions.addAll(suggestions);
		return this;
	}

	public ShinyInputTextBuilder withDefaultValue(final String defaultValue) {
		this._defaultValue = defaultValue;
		return this;
	}

	public ShinyInputTextBuilder withSecret(final boolean secret) {
		this._secret = secret;
		return this;
	}

	// Getters for the fields, needed by the record constructor
	String inputTextLabel() {
		return _label;
	}

	boolean inputTextRequired() {
		return _required;
	}

	Pattern inputTextValidationPattern() {
		return _validationPattern;
	}

	List<String> inputTextSuggestions() {
		return _suggestions;
	}

	String inputTextDefaultValue() {
		return _defaultValue;
	}

	boolean inputTextSecret() {
		return _secret;
	}

	@Override
	public ShinyInputText build() {
		Assertion.check()
				.when(_required, () -> Assertion.check().isNotBlank(_label, "Label is required when input is required."));
		//---
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyInputText(
				_id,
				_label,
				_required,
				_validationPattern,
				_suggestions,
				_defaultValue,
				new ShinyMagicBox<>());
	}
}
