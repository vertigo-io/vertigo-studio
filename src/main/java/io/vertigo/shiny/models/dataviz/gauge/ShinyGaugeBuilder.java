package io.vertigo.shiny.models.dataviz.gauge;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyGaugeBuilder implements Builder<ShinyGauge> {
	private UUID _id;
	private String _title;
	private double _value;
	private double _maxValue = 100;

	public ShinyGaugeBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyGaugeBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
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
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyGauge(_id, _title, _value, _maxValue);
	}
}
