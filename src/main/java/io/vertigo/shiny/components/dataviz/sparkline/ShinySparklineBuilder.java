package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinySparklineBuilder implements Builder<ShinySparkline> {
	String title;
	List<Double> data;
	ShinySparklineStyle sparklineStyle;

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

	public ShinySparklineBuilder withTitle(final String text) {
		this.title = text;
		return this;
	}

	public ShinySparklineBuilder withData(final List<Double> values) {
		Assertion.check().isNotNull(values);
		//---
		this.data = values;
		return this;
	}

	@Override
	public ShinySparkline build() {
		// Perform any final validations here before building the object
		//---
		return new ShinySparkline(this);
	}
}
