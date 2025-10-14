package io.vertigo.shiny.components.dataviz.chart;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyChart(
		String title,
		ShinyChartType chartType,
		List<String> labels,
		List<ShinyChartSerie> series) implements ShinyComponent {

	@Override
	public String type() {
		return switch (chartType) {
			case bar -> "ShinyBarChart";
			case area -> "ShinyAreaChart";
			case line -> "ShinyLineChart";
			case radar -> "ShinyRadarChart";
			case donut -> "ShinyDonutChart";
			case pie -> "ShinyPieChart";
		};
	}

	public ShinyChart {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(chartType)
				.isNotNull(labels)
				.isNotNull(series);
	}
}
