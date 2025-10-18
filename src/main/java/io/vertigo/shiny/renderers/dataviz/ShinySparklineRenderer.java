package io.vertigo.shiny.renderers.dataviz;

import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.dataviz.sparkline.ShinySparkline;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinySparklineRenderer implements ShinyComponentRenderer<ShinySparkline> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinySparkline;
	}

	@Override
	public void render(final ShinySparkline shinySparkline) {
		Assertion.check()
				.isNotNull(shinySparkline);
		//---
		final ShinySparklineStyle style = Shiny.theme().sparklineStyle();
		final ShinyWriter writer = Shiny.writer();

		final String sparkline = shinySparkline.values().stream()
				.map(value -> getSparklineChar(shinySparkline, value))
				.collect(Collectors.joining());

		writer.print(shinySparkline.title() != null ? shinySparkline.title() + " " : "")
				.println(style.color().fg(sparkline));
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
