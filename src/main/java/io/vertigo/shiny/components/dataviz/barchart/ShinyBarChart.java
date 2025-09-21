package io.vertigo.shiny.components.dataviz.barchart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyBarChart(
		String title,
		String[] header,
		int[] values,
		@JsonIgnore ShinyBarChartStyle style) implements ShinyComponent {
}
