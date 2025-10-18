package io.vertigo.shiny.models.dataviz.chart;

import java.util.List;
import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyChart(
		UUID id,
		String title,
		ShinyChartType chartType,
		List<String> labels,
		List<ShinyChartSerie> series) implements ShinyModel {

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
