package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinySparklineBuilder implements Builder<ShinySparkline> {
	private String sparkLineTitle;
	private List<Double> sparkLineValues;

	public ShinySparklineBuilder withTitle(final String title) {
		this.sparkLineTitle = title;
		return this;
	}

	public ShinySparklineBuilder withValues(final List<Double> values) {
		Assertion.check().isNotNull(values);
		//---
		this.sparkLineValues = values;
		return this;
	}

	public ShinySparklineBuilder withValues(final double... values) {
		Assertion.check().isNotNull(values);
		//---
		this.sparkLineValues = Arrays.stream(values).boxed().collect(Collectors.toList());
		return this;
	}

	@Override
	public ShinySparkline build() {
		return new ShinySparkline(sparkLineTitle, sparkLineValues);
	}
}
