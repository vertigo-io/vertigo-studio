package io.vertigo.shiny.models.dataviz.sparkline;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinySparklineBuilder implements Builder<ShinySparkline> {
	private String _title;
	private List<Double> _values;

	public ShinySparklineBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinySparklineBuilder withValues(final List<Double> values) {
		Assertion.check().isNotNull(values);
		//---
		this._values = values;
		return this;
	}

	public ShinySparklineBuilder withValues(final double... values) {
		Assertion.check().isNotNull(values);
		//---
		this._values = Arrays.stream(values).boxed().collect(Collectors.toList());
		return this;
	}

	@Override
	public ShinySparkline build() {
		return new ShinySparkline(_title, _values);
	}
}
