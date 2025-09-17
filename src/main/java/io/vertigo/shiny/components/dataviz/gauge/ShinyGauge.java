package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyGauge implements ShinyComponent {
	private final String title;
	private final double value;
	private final double max;
	private final ShinyGaugeStyle gaugeStyle;

	// Package-private constructor, only accessible by the Builder
	ShinyGauge(ShinyGaugeBuilder builder) {
		Assertion.check()
				.isNotNull(builder);
		//---
		this.title = builder.title;
		this.value = builder.value;
		this.max = builder.max;
		this.gaugeStyle = builder.gaugeStyle;
	}

	// Static factory method to get a new Builder instance
	public static ShinyGaugeBuilder builder() {
		return new ShinyGaugeBuilder();
	}

	public void render(ShinyWriter writer) {
		final double percentage;
		if (value >= max) {
			percentage = 1;
		} else if (value <= 0) {
			percentage = 0;
		} else {
			percentage = (value / max);
		}
		final int filledLength = (int) (this.gaugeStyle.maxLength() * percentage);
		final String gauge = new StringBuilder()
				.append(title != null ? title + " " : "")
				.append("[")
				.append(gaugeStyle.color().fg("█".repeat(filledLength) + "▒".repeat(this.gaugeStyle.maxLength() - filledLength)))
				.append("] ")
				.append(String.format("%.2f%%", percentage * 100))
				.toString();
		writer.println(gauge);
	}
}
