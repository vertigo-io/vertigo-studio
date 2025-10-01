package io.vertigo.shiny.components.dataviz.area;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public final class ShinyAreaChartBuilder implements Builder<ShinyAreaChart> {
	private String _title;
	private List<String> _labels = new ArrayList<>();
	private final List<ShinyChartSerie> _series = new ArrayList<>();

	public ShinyAreaChartBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyAreaChartBuilder withLabels(final String... labels) {
		this._labels.addAll(Arrays.asList(labels));
		return this;
	}

	public ShinyAreaChartBuilder withLabels(final List<String> labels) {
		this._labels.addAll(labels);
		return this;
	}

	public ShinyAreaChartBuilder addSerie(final String name, final Double... values) {
		this._series.add(new ShinyChartSerie(name, Arrays.asList(values)));
		return this;
	}

	public ShinyAreaChartBuilder addSerie(final ShinyChartSerie serie) {
		this._series.add(serie);
		return this;
	}

	@Override
	public ShinyAreaChart build() {
		return new ShinyAreaChart(_title, _labels, _series);
	}
}
