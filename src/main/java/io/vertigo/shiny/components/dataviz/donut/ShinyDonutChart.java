package io.vertigo.shiny.components.dataviz.donut;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public record ShinyDonutChart(
		String title,
		List<String> labels,
		List<ShinyChartSerie> series) implements ShinyComponent {

	public ShinyDonutChart {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(labels)
				.isNotNull(series);
	}
}
