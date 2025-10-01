package io.vertigo.shiny.components.dataviz.radar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public final class ShinyRadarChartBuilder {
	private String _title;
	private List<String> _labels = new ArrayList<>();
	private List<ShinyChartSerie> _series = new ArrayList<>();

	public ShinyRadarChartBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyRadarChartBuilder withLabels(final String... labels) {
		Assertion.check().isNotNull(labels);
		//---
		this._labels.addAll(Arrays.asList(labels));
		return this;
	}

	public ShinyRadarChartBuilder addSerie(final String name, final List<Double> data) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(data);
		//---
		this._series.add(new ShinyChartSerie(name, data));
		return this;
	}

	public ShinyRadarChartBuilder addSerie(final String name, final Double... data) {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(data);
		//---
		this._series.add(new ShinyChartSerie(name, Arrays.asList(data)));
		return this;
	}

	public ShinyRadarChart build() {
		return new ShinyRadarChart(_title, _labels, _series);
	}
}
