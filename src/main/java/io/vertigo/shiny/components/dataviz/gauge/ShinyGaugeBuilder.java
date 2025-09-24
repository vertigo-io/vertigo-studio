package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Builder;

public final class ShinyGaugeBuilder implements Builder<ShinyGauge> {
	private String gaugeTitle;
	private double gaugeValue;
	private double gaugeMaxValue = 100;

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
		return new ShinyGauge(gaugeTitle, gaugeValue, gaugeMaxValue);
	}
}
