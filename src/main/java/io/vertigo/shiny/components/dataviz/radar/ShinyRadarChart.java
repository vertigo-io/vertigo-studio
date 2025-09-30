package io.vertigo.shiny.components.dataviz.radar;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyRadarChart(String title, List<String> labels, List<ShinyRadarSeries> series) implements ShinyComponent {

	public ShinyRadarChart {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(labels)
				.isNotNull(series);
	}

}
