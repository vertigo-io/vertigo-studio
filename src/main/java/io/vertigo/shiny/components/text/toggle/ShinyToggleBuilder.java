package io.vertigo.shiny.components.text.toggle;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyToggleBuilder implements Builder<ShinyToggle> {
	private String toggleLabel;
	private boolean toggleValue;
	private ShinyToggleType toggleType = ShinyToggleType.TOGGLE;
	private String onText = "ON";
	private String offText = "OFF";
	private boolean showText = true;
	private ShinyToggleStyle toggleStyle;

	// No public constructor, use ShinyToggle.builder()
	ShinyToggleBuilder() {
		// Package-private constructor
		toggleStyle = Shiny.theme().toggleStyle(); // Initialize default style
	}

	public ShinyToggleBuilder withStyle(final ShinyToggleStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.toggleStyle = style;
		return this;
	}

	public ShinyToggleBuilder withLabel(final String label) {
		this.toggleLabel = label;
		return this;
	}

	public ShinyToggleBuilder withValue(final boolean value) {
		this.toggleValue = value;
		return this;
	}

	public ShinyToggleBuilder withType(final ShinyToggleType type) {
		this.toggleType = type;
		return this;
	}

	public ShinyToggleBuilder withOnText(final String text) {
		this.onText = text;
		return this;
	}

	public ShinyToggleBuilder withOffText(final String text) {
		this.offText = text;
		return this;
	}

	public ShinyToggleBuilder withShowText(final boolean text) {
		this.showText = text;
		return this;
	}

	@Override
	public ShinyToggle build() {
		return new ShinyToggle(
				toggleLabel,
				toggleValue,
				toggleType,
				onText,
				offText,
				showText,
				toggleStyle);
	}
}
