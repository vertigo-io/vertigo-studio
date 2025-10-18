package io.vertigo.shiny.renderers.dataviz;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.dataviz.gauge.ShinyGauge;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyGaugeRenderer implements ShinyComponentRenderer<ShinyGauge> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyGauge;
	}

	@Override
	public void render(final ShinyGauge shinyGauge) {
		Assertion.check()
				.isNotNull(shinyGauge);
		//---
		final ShinyGaugeStyle style = Shiny.theme().gaugeStyle();
		final ShinyWriter writer = Shiny.writer();
		final double percentage;
		if (shinyGauge.value() >= shinyGauge.maxValue()) {
			percentage = 1;
		} else if (shinyGauge.value() <= 0) {
			percentage = 0;
		} else {
			percentage = (shinyGauge.value() / shinyGauge.maxValue());
		}
		final int filledLength = (int) (style.maxLength() * percentage);
		final String gauge = new StringBuilder()
				.append(shinyGauge.title() != null ? shinyGauge.title() + " " : "")
				.append("[")
				.append(style.color().fg("█".repeat(filledLength) + "▒".repeat(style.maxLength() - filledLength)))
				.append("] ")
				.append(String.format("%.2f%%", percentage * 100))
				.toString();
		writer.println(gauge);
	}
}
