package io.vertigo.shiny.components.dataviz.area;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public record ShinyAreaChart(
		String title,
		List<String> labels,
		List<ShinyChartSerie> series) implements ShinyComponent {

	public ShinyAreaChart {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(labels)
				.isNotNull(series);
	}
}
