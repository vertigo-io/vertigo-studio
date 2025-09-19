package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinySparklineBuilder implements Builder<ShinySparkline> {
	private String sparkLineTitle;
	private List<Double> sparkLineValues;
	private ShinySparklineStyle sparklineStyle;

	// No public constructor, use ShinySparkline.builder()
	ShinySparklineBuilder() {
		// Package-private constructor
		this.sparklineStyle = Shiny.theme().sparklineStyle(); // Initialize default style
	}

	public ShinySparklineBuilder withStyle(final ShinySparklineStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.sparklineStyle = style;
		return this;
	}

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
		return new ShinySparkline(sparkLineTitle, sparkLineValues, sparklineStyle);
	}
}
