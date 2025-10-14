package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinySparkline(
		String title,
		List<Double> values) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinySparkline";
	}

}
