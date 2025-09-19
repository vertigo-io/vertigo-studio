package io.vertigo.shiny.components.dataviz.gauge;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyGauge(
		String title,
		double value,
		double maxValue,
		@JsonIgnore ShinyGaugeStyle style) implements ShinyComponent {

	public ShinyGauge {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}

	// Static factory method to get a new Builder instance
	public static ShinyGaugeBuilder builder() {
		return new ShinyGaugeBuilder();
	}

	public void render(ShinyWriter writer) {
		final double percentage;
		if (value >= maxValue) {
			percentage = 1;
		} else if (value <= 0) {
			percentage = 0;
		} else {
			percentage = (value / maxValue);
		}
		final int filledLength = (int) (style.maxLength() * percentage);
		final String gauge = new StringBuilder()
				.append(title != null ? title + " " : "")
				.append("[")
				.append(style.color().fg("█".repeat(filledLength) + "▒".repeat(style.maxLength() - filledLength)))
				.append("] ")
				.append(String.format("%.2f%%", percentage * 100))
				.toString();
		writer.println(gauge);
	}
}
