package io.vertigo.shiny.components.dataviz.radar;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record ShinyRadarSeries(String name, List<Double> data) {
	public ShinyRadarSeries {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(data);
	}
}
