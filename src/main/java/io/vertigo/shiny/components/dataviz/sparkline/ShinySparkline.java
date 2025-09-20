package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinySparkline(
		String title,
		List<Double> values,
		@JsonIgnore ShinySparklineStyle style) implements ShinyComponent {

	public ShinySparkline {
	}

	// Static factory method to get a new Builder instance
	public static ShinySparklineBuilder builder() {
		return new ShinySparklineBuilder();
	}

	public void render(final ShinyWriter writer) {
		new ShinySparklineRenderer().render(this, writer);
	}
}
