package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyGaugeBuilder implements Builder<ShinyGauge> {
	String title;
	double value;
	double max = 100;
	ShinyGaugeStyle gaugeStyle;

	// No public constructor, use ShinyGauge.builder()
	ShinyGaugeBuilder() {
		// Package-private constructor
		this.gaugeStyle = Shiny.theme().gaugeStyle(); // Initialize default style
	}

	public ShinyGaugeBuilder withStyle(final ShinyGaugeStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.gaugeStyle = style;
		return this;
	}

	public ShinyGaugeBuilder withTitle(final String text) {
		this.title = text;
		return this;
	}

	public ShinyGaugeBuilder withValue(final double currentValue) {
		this.value = currentValue;
		return this;
	}

	public ShinyGaugeBuilder withMax(final double maxValue) {
		this.max = maxValue;
		return this;
	}

	@Override
	public ShinyGauge build() {
		// Perform any final validations here before building the object
		//---
		return new ShinyGauge(this);
	}
}
