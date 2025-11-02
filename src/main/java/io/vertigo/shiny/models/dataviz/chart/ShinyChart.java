package io.vertigo.shiny.models.dataviz.chart;

import java.util.List;

import io.vertigo.shiny.models.ShinyBlock;

public record ShinyChart(
		String title,
		ShinyChartType chartType,
		List<String> labels,
		List<ShinyChartSerie> series) implements ShinyBlock {

	@Override
	public String shinyType() {
		return switch (chartType) {
			case bar -> "ShinyBarChart";
			case area -> "ShinyAreaChart";
			case line -> "ShinyLineChart";
			case radar -> "ShinyRadarChart";
			case donut -> "ShinyDonutChart";
			case pie -> "ShinyPieChart";
		};
	}
}
