package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinySparkline implements ShinyComponent {
	private final String title;
	private final List<Double> data;
	private final ShinySparklineStyle sparklineStyle;

	// Package-private constructor, only accessible by the Builder
	ShinySparkline(ShinySparklineBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.title = builder.title;
		this.data = builder.data;
		this.sparklineStyle = builder.sparklineStyle;
	}

	// Static factory method to get a new Builder instance
	public static ShinySparklineBuilder builder() {
		return new ShinySparklineBuilder();
	}

	public void render(final ShinyWriter writer) {
		final String sparkline = data.stream()
				.map(this::getSparklineChar)
				.collect(Collectors.joining());

		writer.print(title != null ? title + " " : "")
				.println(sparklineStyle.color().fg(sparkline));
	}

	private String getSparklineChar(final double value) {
		final char[] chars = { ' ', '▂', '▃', '▄', '▅', '▆', '▇', '█' };
		final double min = data.stream().min(Double::compare).orElse(0.0);
		final double max = data.stream().max(Double::compare).orElse(0.0);
		final double range = max - min;
		if (range == 0) {
			return String.valueOf(chars[chars.length / 2]);
		}
		final int index = (int) Math.round(((value - min) / range) * (chars.length - 1));
		return String.valueOf(chars[index]);
	}
}
