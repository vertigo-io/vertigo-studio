package io.vertigo.shiny.components.dataviz.sparkline;

import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;

public final class ShinySparklineRenderer {

	private ShinySparklineRenderer() {
		//private constructor
	}

	public static void render(final ShinySparkline shinySparkline, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinySparkline);
		Assertion.check().isNotNull(writer);
		//---
		final String sparkline = shinySparkline.values().stream()
				.map(value -> getSparklineChar(shinySparkline, value))
				.collect(Collectors.joining());

		writer.print(shinySparkline.title() != null ? shinySparkline.title() + " " : "")
				.println(shinySparkline.style().color().fg(sparkline));
	}

	private static String getSparklineChar(final ShinySparkline shinySparkline, final double value) {
		final char[] chars = { ' ', '▂', '▃', '▄', '▅', '▆', '▇', '█' };
		final double min = shinySparkline.values().stream().min(Double::compare).orElse(0.0);
		final double max = shinySparkline.values().stream().max(Double::compare).orElse(0.0);
		final double range = max - min;
		if (range == 0) {
			return String.valueOf(chars[chars.length / 2]);
		}
		final int index = (int) Math.round(((value - min) / range) * (chars.length - 1));
		return String.valueOf(chars[index]);
	}
}
