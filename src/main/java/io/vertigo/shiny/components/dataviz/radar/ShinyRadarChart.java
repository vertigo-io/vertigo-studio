package io.vertigo.shiny.components.dataviz.radar;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public record ShinyRadarChart(
		String title,
		List<String> labels,
		List<ShinyChartSerie> series) implements ShinyComponent {

	public ShinyRadarChart {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(labels)
				.isNotNull(series);
	}

}
