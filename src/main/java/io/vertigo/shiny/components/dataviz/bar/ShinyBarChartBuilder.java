package io.vertigo.shiny.components.dataviz.bar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public final class ShinyBarChartBuilder implements Builder<ShinyBarChart> {
	private String _title;
	private List<String> _labels = new ArrayList<>();
	private final List<ShinyChartSerie> _series = new ArrayList<>();

	public ShinyBarChartBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyBarChartBuilder withLabels(final String... labels) {
		this._labels.addAll(Arrays.asList(labels));
		return this;
	}

	public ShinyBarChartBuilder withLabels(final List<String> labels) {
		this._labels.addAll(labels);
		return this;
	}

	public ShinyBarChartBuilder addSerie(final String name, final Double... values) {
		this._series.add(new ShinyChartSerie(name, Arrays.asList(values)));
		return this;
	}

	public ShinyBarChartBuilder addSerie(final ShinyChartSerie serie) {
		this._series.add(serie);
		return this;
	}

	@Override
	public ShinyBarChart build() {
		return new ShinyBarChart(_title, _labels, _series);
	}
}
