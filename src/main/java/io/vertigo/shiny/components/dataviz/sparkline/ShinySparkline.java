package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("sparkLine")
public record ShinySparkline(
		String title,
		List<Double> values) implements ShinyComponent {
}
