package io.vertigo.shiny.models.input.autocomplete;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyAutocompleteBuilder {
	private String _label;
	private final List<String> _options = new ArrayList<>();
	private String _value;
	private String _placeholder;

	public ShinyAutocompleteBuilder withLabel(final String label) {
		Assertion.check().isNotBlank(label);
		//---
		_label = label;
		return this;
	}

	public ShinyAutocompleteBuilder addOption(final String option) {
		Assertion.check().isNotBlank(option);
		//---
		_options.add(option);
		return this;
	}

	public ShinyAutocompleteBuilder withValue(final String value) {
		_value = value;
		return this;
	}

	public ShinyAutocompleteBuilder withPlaceholder(final String placeholder) {
		_placeholder = placeholder;
		return this;
	}

	public ShinyAutocomplete build() {
		return new ShinyAutocomplete(_label, _options, _value, _placeholder);
	}
}
