package io.vertigo.shiny.components.dataviz.pie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public final class ShinyPieChartBuilder implements Builder<ShinyPieChart> {
	private String _title;
	private List<String> _labels = new ArrayList<>();
	private final List<ShinyChartSerie> _series = new ArrayList<>();

	public ShinyPieChartBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyPieChartBuilder withLabels(final String... labels) {
		this._labels.addAll(Arrays.asList(labels));
		return this;
	}

	public ShinyPieChartBuilder withLabels(final List<String> labels) {
		this._labels.addAll(labels);
		return this;
	}

	public ShinyPieChartBuilder addSerie(final String name, final Double... values) {
		this._series.add(new ShinyChartSerie(name, Arrays.asList(values)));
		return this;
	}

	public ShinyPieChartBuilder addSerie(final ShinyChartSerie serie) {
		this._series.add(serie);
		return this;
	}

	@Override
	public ShinyPieChart build() {
		return new ShinyPieChart(_title, _labels, _series);
	}
}
