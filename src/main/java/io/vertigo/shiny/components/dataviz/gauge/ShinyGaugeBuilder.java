package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyGaugeBuilder implements Builder<ShinyGauge> {
	private String gaugeTitle;
	private double gaugeValue;
	private double gaugeMaxValue = 100;
	private ShinyGaugeStyle gaugeStyle;

	public ShinyGaugeBuilder() {
		this.gaugeStyle = Shiny.theme().gaugeStyle(); // Initialize default style
	}

	public ShinyGaugeBuilder withStyle(final ShinyGaugeStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.gaugeStyle = style;
		return this;
	}

	public ShinyGaugeBuilder withTitle(final String title) {
		this.gaugeTitle = title;
		return this;
	}

	public ShinyGaugeBuilder withValue(final double value) {
		this.gaugeValue = value;
		return this;
	}

	public ShinyGaugeBuilder withMaxValue(final double maxValue) {
		this.gaugeMaxValue = maxValue;
		return this;
	}

	@Override
	public ShinyGauge build() {
		return new ShinyGauge(gaugeTitle, gaugeValue, gaugeMaxValue, gaugeStyle);
	}
}
