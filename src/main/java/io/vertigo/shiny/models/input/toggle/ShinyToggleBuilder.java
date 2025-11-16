package io.vertigo.shiny.models.input.toggle;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyToggleBuilder implements Builder<ShinyToggle> {
	private UUID _id;
	private String _label;
	private boolean _value;
	private ShinyToggleType _toggleType = ShinyToggleType.TOGGLE;
	private String _onText = "ON";
	private String _offText = "OFF";
	private boolean _showText = true;

	public ShinyToggleBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyToggleBuilder withLabel(final String label) {
		Assertion.check().isNotBlank(label);
		this._label = label;
		return this;
	}

	public ShinyToggleBuilder withValue(final boolean value) {
		this._value = value;
		return this;
	}

	public ShinyToggleBuilder withType(final ShinyToggleType toggleType) {
		Assertion.check().isNotNull(toggleType);
		this._toggleType = toggleType;
		return this;
	}

	public ShinyToggleBuilder withOnText(final String text) {
		Assertion.check().isNotBlank(text);
		this._onText = text;
		return this;
	}

	public ShinyToggleBuilder withOffText(final String text) {
		Assertion.check().isNotBlank(text);
		this._offText = text;
		return this;
	}

	public ShinyToggleBuilder withShowText(final boolean text) {
		this._showText = text;
		return this;
	}

	@Override
	public ShinyToggle build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyToggle(
				_id,
				_label,
				_value,
				_toggleType,
				_onText,
				_offText,
				_showText);
	}
}
