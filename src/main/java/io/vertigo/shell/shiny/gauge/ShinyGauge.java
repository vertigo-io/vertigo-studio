package io.vertigo.shell.shiny.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColor;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyGauge {
	private final Shiny shiny;
	private String title;
	private double value;
	private double max = 100;
	private int length = 50;
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

	public ShinyGauge length(final int barLength) {
		this.length = barLength;
		return this;
	}

	public ShinyGauge color(final ShinyColor color) {
		this.gaugeColor = color;
		return this;
	}

	public void print() {
		final double percentage = value / max;
		final int filledLength = (int) (length * percentage);
		final String gauge = new StringBuilder()
				.append(title != null ? title + " " : "")
				.append("[")
				.append(gaugeColor)
				.append("█".repeat(filledLength))
				.append("▒".repeat(length - filledLength))
				.append(ShinyColors.RESET)
				.append("] ")
				.append(String.format("%.2f%%", percentage * 100))
				.toString();
		shiny.getWriter().println(gauge);
	}
}
