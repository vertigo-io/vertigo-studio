package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyGauge implements ShinyComponent {
	private String title;
	private double value;
	private double max = 100;
	private ShinyGaugeStyle gaugeStyle;

	public ShinyGauge() {
		this.gaugeStyle = Shiny.theme().gaugeStyle();
	}

	public ShinyGauge style(final ShinyGaugeStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.gaugeStyle = style;
		return this;
	}

	public ShinyGauge title(final String text) {
		this.title = text;
		return this;
	}

	public ShinyGauge value(final double currentValue) {
		this.value = currentValue;
		return this;
	}

	public ShinyGauge max(final double maxValue) {
		this.max = maxValue;
		return this;
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
