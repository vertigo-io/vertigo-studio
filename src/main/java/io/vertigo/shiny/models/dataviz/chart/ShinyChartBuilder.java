package io.vertigo.shiny.models.dataviz.chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyChartBuilder implements Builder<ShinyChart> {

	private final ShinyChartType _chartType;
	private String _title;
	private List<String> _labels = new ArrayList<>();
	private final List<ShinyChartSerie> _series = new ArrayList<>();

	public ShinyChartBuilder(@Nonnull final ShinyChartType chartType) {
		Assertion.check().isNotNull(chartType);
		//---
		this._chartType = chartType;
	}

	public ShinyChartBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyChartBuilder withLabels(final String... labels) {
		this._labels.addAll(Arrays.asList(labels));
		return this;
	}

	public ShinyChartBuilder withLabels(final List<String> labels) {
		this._labels.addAll(labels);
		return this;
	}

	public ShinyChartBuilder addSerie(final String name, final Double... values) {
		this._series.add(new ShinyChartSerie(name, Arrays.asList(values)));
		return this;
	}

	public ShinyChartBuilder addSerie(@Nonnull final ShinyChartSerie serie) {
		Assertion.check().isNotNull(serie);
		//---
		this._series.add(serie);
		return this;
	}

	public ShinyChartBuilder addAllSeries(@Nonnull final List<ShinyChartSerie> series) {
		Assertion.check().isNotNull(series);
		//---
		this._series.addAll(series);
		return this;
	}

	@Override
	public ShinyChart build() {

		return new ShinyChart(_title, _chartType, _labels, _series);
	}
}
