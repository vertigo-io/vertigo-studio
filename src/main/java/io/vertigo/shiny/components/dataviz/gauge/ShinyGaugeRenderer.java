package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyComponentRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import

public final class ShinyGaugeRenderer implements ShinyComponentRenderer<ShinyGauge> { // Implements interface
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyGauge;
	}

	@Override
	public void render(final ShinyGauge shinyGauge, final ShinyWriter writer) { // Not static
		Assertion.check().isNotNull(shinyGauge);
		Assertion.check().isNotNull(writer);
		//---
		final double percentage;
		if (shinyGauge.value() >= shinyGauge.maxValue()) {
			percentage = 1;
		} else if (shinyGauge.value() <= 0) {
			percentage = 0;
		} else {
			percentage = (shinyGauge.value() / shinyGauge.maxValue());
		}
		final int filledLength = (int) (shinyGauge.style().maxLength() * percentage);
		final String gauge = new StringBuilder()
				.append(shinyGauge.title() != null ? shinyGauge.title() + " " : "")
				.append("[")
				.append(shinyGauge.style().color().fg("█".repeat(filledLength) + "▒".repeat(shinyGauge.style().maxLength() - filledLength)))
				.append("] ")
				.append(String.format("%.2f%%", percentage * 100))
				.toString();
		writer.println(gauge);
	}
}
