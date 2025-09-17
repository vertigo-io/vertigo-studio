package io.vertigo.shiny.components.dataviz.barchart;

import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyBarChartBuilder implements Builder<ShinyBarChart> {
	private String barChartTitle;
	private String[] barChartHeader;
	private int[] barChartValues;
	private ShinySortMode sortMode = ShinySortMode.NO;
	private ShinyBarChartStyle barChartStyle;

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

	private void sort() {
		final Integer[] indices = new Integer[barChartHeader.length];
		for (int i = 0; i < barChartHeader.length; i++) {
			indices[i] = i;
		}

		switch (sortMode) {
			case NO:
				break;
			case VALUE_ASC:
				Arrays.sort(indices, (i1, i2) -> Integer.compare(barChartValues[i1], barChartValues[i2]));
				break;
			case VALUE_DESC:
				Arrays.sort(indices, (i1, i2) -> Integer.compare(barChartValues[i2], barChartValues[i1]));
				break;
			case HEADER_ASC:
				Arrays.sort(indices, (i1, i2) -> barChartHeader[i1].compareToIgnoreCase(barChartHeader[i2]));
				break;
			case HEADER_DESC:
				Arrays.sort(indices, (i1, i2) -> barChartHeader[i2].compareToIgnoreCase(barChartHeader[i1]));
				break;
		}
		// Réappliquer le tri aux deux tableaux
		reorder(indices);
	}

	private void reorder(final Integer[] indices) {
		final String[] newHeader = new String[barChartHeader.length];
		final int[] newValues = new int[barChartValues.length];
		for (int i = 0; i < indices.length; i++) {
			newHeader[i] = barChartHeader[indices[i]];
			newValues[i] = barChartValues[indices[i]];
		}
		this.barChartHeader = newHeader;
		this.barChartValues = newValues;
	}

	@Override
	public ShinyBarChart build() {
		this.sort();
		return new ShinyBarChart(barChartTitle, barChartHeader, barChartValues, barChartStyle);
	}
}
