package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.color.ShinyColor;
import io.vertigo.shiny.color.ShinyColors;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyGauge implements ShinyComponent {
	private final Shiny shiny;
	private String title;
	private double value;
	private double max = 100;
	private int barLength = 50;
	private ShinyColor gaugeColor = ShinyColors.GREEN;

	public ShinyGauge(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
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

	public ShinyGauge length(final int length) {
		this.barLength = length;
		return this;
	}

	public ShinyGauge color(final ShinyColor color) {
		this.gaugeColor = color;
		return this;
	}

	public void print() {
		final double percentage;
		if (value >= max) {
			percentage = 1;
		} else if (value <= 0) {
			percentage = 0;
		} else {
			percentage = (value / max);
		}
		final int filledLength = (int) (barLength * percentage);
		final String gauge = new StringBuilder()
				.append(title != null ? title + " " : "")
				.append("[")
				.append(gaugeColor)
				.append("█".repeat(filledLength))
				.append("▒".repeat(barLength - filledLength))
				.append(ShinyColors.RESET)
				.append("] ")
				.append(String.format("%.2f%%", percentage * 100))
				.toString();
		shiny.getWriter().println(gauge);
	}
}
