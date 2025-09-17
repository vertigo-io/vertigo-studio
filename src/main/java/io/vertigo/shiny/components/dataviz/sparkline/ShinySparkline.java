package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinySparkline(
		String title,
		List<Double> data,
		ShinySparklineStyle style) implements ShinyComponent {

	public ShinySparkline {
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
				.println(style.color().fg(sparkline));
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
