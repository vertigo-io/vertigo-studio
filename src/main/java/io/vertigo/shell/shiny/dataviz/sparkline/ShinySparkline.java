package io.vertigo.shell.shiny.dataviz.sparkline;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.ShinyComponent;
import io.vertigo.shell.shiny.color.ShinyColor;
import io.vertigo.shell.shiny.color.ShinyColors;

public final class ShinySparkline implements ShinyComponent {
	private final Shiny shiny;
	private String title;
	private List<Double> data;
	private ShinyColor sparklineColor = ShinyColors.BLUE; // Default color

	public ShinySparkline(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
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

	public void print() {
		final String sparkline = data.stream()
				.map(this::getSparklineChar)
				.collect(Collectors.joining());

		shiny.getWriter().println((title != null ? title + " " : "")
				+ sparklineColor + sparkline + ShinyColors.RESET);
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
