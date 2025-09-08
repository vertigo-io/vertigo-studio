package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinySparkline implements ShinyComponent {
	private String title;
	private List<Double> data;
	private ShinyColor sparklineColor = ShinyColors.BLUE; // Default color

	public ShinySparkline() {
	}

	public ShinySparkline title(final String text) {
		this.title = text;
		return this;
	}

	public ShinySparkline data(final List<Double> values) {
		this.data = values;
		return this;
	}

	public ShinySparkline color(final ShinyColor color) {
		this.sparklineColor = color;
		return this;
	}

	public void render(final ShinyWriter writer) {
		final String sparkline = data.stream()
				.map(this::getSparklineChar)
				.collect(Collectors.joining());

		writer.println((title != null ? title + " " : "")
				+ sparklineColor.fg(sparkline));
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
