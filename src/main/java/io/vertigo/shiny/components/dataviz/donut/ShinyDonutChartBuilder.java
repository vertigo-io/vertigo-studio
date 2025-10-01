package io.vertigo.shiny.components.dataviz.donut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public final class ShinyDonutChartBuilder implements Builder<ShinyDonutChart> {
	private String _title;
	private List<String> _labels = new ArrayList<>();
	private final List<ShinyChartSerie> _series = new ArrayList<>();

	public ShinyDonutChartBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyDonutChartBuilder withLabels(final String... labels) {
		this._labels.addAll(Arrays.asList(labels));
		return this;
	}

	public ShinyDonutChartBuilder withLabels(final List<String> labels) {
		this._labels.addAll(labels);
		return this;
	}

	public ShinyDonutChartBuilder addSerie(final String name, final Double... values) {
		this._series.add(new ShinyChartSerie(name, Arrays.asList(values)));
		return this;
	}

	public ShinyDonutChartBuilder addSerie(final ShinyChartSerie serie) {
		Assertion.check().isNotNull(serie);
		//---
		this._series.add(serie);
		return this;
	}

	public ShinyDonutChartBuilder addAllSeries(final List<ShinyChartSerie> series) {
		Assertion.check().isNotNull(series);
		//---
		this._series.addAll(series);
		return this;
	}

	@Override
	public ShinyDonutChart build() {
		return new ShinyDonutChart(_title, _labels, _series);
	}
}
