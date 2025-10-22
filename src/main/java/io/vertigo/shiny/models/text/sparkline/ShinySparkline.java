package io.vertigo.shiny.models.text.sparkline;

import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinySparkline(
		UUID id,
		String title,
		List<Double> values) implements ShinyModel {

	public ShinySparkline {

	}

}
