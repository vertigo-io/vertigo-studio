package io.vertigo.shiny.models.dataviz.chart;

import java.util.List;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public record ShinyChartSerie(@Nonnull String name, @Nonnull List<Double> data) {
	public ShinyChartSerie {
		Assertion.check()
				.isNotBlank(name)
				.isNotNull(data);
	}
}
