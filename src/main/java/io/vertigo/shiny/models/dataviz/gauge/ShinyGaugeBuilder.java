package io.vertigo.shiny.models.dataviz.gauge;

import io.vertigo.core.lang.Builder;

public final class ShinyGaugeBuilder implements Builder<ShinyGauge> {
	private String _title;
	private double _value;
	private double _maxValue = 100;

	public ShinyGaugeBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyGaugeBuilder withValue(final double value) {
		this._value = value;
		return this;
	}

	public ShinyGaugeBuilder withMaxValue(final double maxValue) {
		this._maxValue = maxValue;
		return this;
	}

	@Override
	public ShinyGauge build() {
		return new ShinyGauge(_title, _value, _maxValue);
	}
}
