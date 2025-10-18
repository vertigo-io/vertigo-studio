package io.vertigo.shiny.models.dataviz.sparkline;

import java.util.List;

import io.vertigo.shiny.models.ShinyModel;

public record ShinySparkline(
		String title,
		List<Double> values) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinySparkline";
	}

}
