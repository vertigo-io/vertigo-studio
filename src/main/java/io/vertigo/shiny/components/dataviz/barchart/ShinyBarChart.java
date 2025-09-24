package io.vertigo.shiny.components.dataviz.barchart;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyBarChart(
		String title,
		String[] header,
		int[] values) implements ShinyComponent {
}
