package io.vertigo.shiny.components.dataviz.barchart;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyBarChartBuilder implements Builder<ShinyBarChart> {
	String barChartTitle;
	String[] barChartHeader;
	int[] barChartValues;
	ShinySortMode sortMode = ShinySortMode.NO;
	ShinyBarChartStyle barChartStyle;

	// No public constructor, use ShinyBarChart.builder()
	ShinyBarChartBuilder() {
		// Package-private constructor
		barChartStyle = Shiny.theme().barChartStyle(); // Initialize default style
	}

	public ShinyBarChartBuilder withStyle(final ShinyBarChartStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.barChartStyle = style;
		return this;
	}

	public ShinyBarChartBuilder withTitle(final String title) {
		this.barChartTitle = title;
		return this;
	}

	public ShinyBarChartBuilder withHeader(final String... header) {
		this.barChartHeader = header;
		return this;
	}

	public ShinyBarChartBuilder withHeader(final List<String> header) {
		this.barChartHeader = header.toArray(new String[0]);
		return this;
	}

	public ShinyBarChartBuilder withValues(final int... values) {
		this.barChartValues = values;
		return this;
	}

	public ShinyBarChartBuilder withValues(final List<Integer> values) {
		this.barChartValues = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			this.barChartValues[i] = values.get(i);
		}
		return this;
	}

	public ShinyBarChartBuilder withSort(final ShinySortMode mode) {
		this.sortMode = mode;
		return this;
	}

	@Override
	public ShinyBarChart build() {
		// Perform any final validations here before building the object
		//---
		return new ShinyBarChart(this);
	}
}
