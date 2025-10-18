package io.vertigo.shiny.models.dataviz.chart;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record ShinyChartSerie(String name, List<Double> data) {
	public ShinyChartSerie {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(data);
	}
}
