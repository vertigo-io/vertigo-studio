package io.vertigo.shiny.components.dataviz.bar;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public record ShinyBarChart(
		String title,
		List<String> labels,
		List<ShinyChartSerie> series) implements ShinyComponent {

	public ShinyBarChart {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(labels)
				.isNotNull(series);
	}
}
