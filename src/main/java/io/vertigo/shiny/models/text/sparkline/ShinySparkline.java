package io.vertigo.shiny.models.text.sparkline;

import java.util.List;

import io.vertigo.shiny.models.ShinyElement;

public record ShinySparkline(
		String title,
		List<Double> values) implements ShinyElement {

	public ShinySparkline {
	}

}
