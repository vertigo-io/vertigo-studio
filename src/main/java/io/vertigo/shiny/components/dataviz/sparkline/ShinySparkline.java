package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinySparkline(
		String title,
		List<Double> values,
		@JsonIgnore ShinySparklineStyle style) implements ShinyComponent {
}
