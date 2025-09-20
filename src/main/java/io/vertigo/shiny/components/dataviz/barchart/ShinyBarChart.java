package io.vertigo.shiny.components.dataviz.barchart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyBarChart(
		String title,
		String[] header,
		int[] values,
		@JsonIgnore ShinyBarChartStyle style) implements ShinyComponent {

	public ShinyBarChart {
	}

	// Static factory method to get a new Builder instance
	public static ShinyBarChartBuilder builder() {
		return new ShinyBarChartBuilder();
	}

	public void render(final ShinyWriter writer) {
		ShinyBarChartRenderer.render(this, writer);
	}
}
