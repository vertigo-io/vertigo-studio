package io.vertigo.shiny.models.text.toggle;

import io.vertigo.core.lang.Builder;

public final class ShinyToggleBuilder implements Builder<ShinyToggle> {
	private String _label;
	private boolean _value;
	private ShinyToggleType _toggleType = ShinyToggleType.TOGGLE;
	private String _onText = "ON";
	private String _offText = "OFF";
	private boolean _showText = true;

	public ShinyToggleBuilder withLabel(final String label) {
		this._label = label;
		return this;
	}

	public ShinyToggleBuilder withValue(final boolean value) {
		this._value = value;
		return this;
	}

	public ShinyToggleBuilder withType(final ShinyToggleType toggleType) {
		this._toggleType = toggleType;
		return this;
	}

	public ShinyToggleBuilder withOnText(final String text) {
		this._onText = text;
		return this;
	}

	public ShinyToggleBuilder withOffText(final String text) {
		this._offText = text;
		return this;
	}

	public ShinyToggleBuilder withShowText(final boolean text) {
		this._showText = text;
		return this;
	}

	@Override
	public ShinyToggle build() {
		return new ShinyToggle(
				_label,
				_value,
				_toggleType,
				_onText,
				_offText,
				_showText);
	}
}
