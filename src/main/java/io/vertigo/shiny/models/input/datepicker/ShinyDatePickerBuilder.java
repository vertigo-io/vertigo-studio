package io.vertigo.shiny.models.input.datepicker;

import io.vertigo.core.lang.Assertion;

public final class ShinyDatePickerBuilder {
	private String _label;
	private String _value;
	private boolean _isRequired;

	public ShinyDatePickerBuilder withLabel(final String label) {
		Assertion.check().isNotBlank(label);
		//---
		_label = label;
		return this;
	}

	public ShinyDatePickerBuilder withValue(final String value) {
		_value = value;
		return this;
	}

	public ShinyDatePickerBuilder isRequired() {
		_isRequired = true;
		return this;
	}

	public ShinyDatePicker build() {
		return new ShinyDatePicker(_label, _value, _isRequired);
	}
}
