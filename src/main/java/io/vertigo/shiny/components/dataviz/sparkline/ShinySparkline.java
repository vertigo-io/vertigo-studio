package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinySparkline implements ShinyComponent {
	private String title;
	private List<Double> data;
	private ShinySparklineStyle sparklineStyle;

	public ShinySparkline() {
		this.sparklineStyle = Shiny.theme().sparklineStyle();
	}

	public ShinySparkline style(final ShinySparklineStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.sparklineStyle = style;
		return this;
	}

	public ShinySparkline title(final String text) {
		this.title = text;
		return this;
	}

	public ShinySparkline data(final List<Double> values) {
		Assertion.check().isNotNull(values);
		//---
		this.data = values;
		return this;
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
