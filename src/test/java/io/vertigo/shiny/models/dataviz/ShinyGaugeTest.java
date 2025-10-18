package io.vertigo.shiny.models.dataviz;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyGaugeTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicGauges(writer);
		testCustomRanges(writer);
		testDifferentLengths(writer);
		testEdgeCases(writer);
	}

	private static void testBasicGauges(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Gauges ---"));
		Shiny.render(
				Shiny.gauge()
						.withTitle("CPU Usage")
						.withValue(50) // 50% of 100 (default maxValue)
						.build());

		Shiny.theme().gaugeStyle()
				.withColor(ShinyColors.YELLOW);
		Shiny.render(
				Shiny.gauge()
						.withTitle("Memory Usage")
						.withValue(75)
						.build());

		Shiny.theme().gaugeStyle()
				.withColor(ShinyColors.RED);
		Shiny.render(
				Shiny.gauge()
						.withTitle("Disk Usage")
						.withValue(90)
						.build());
		writer.println();
	}

	private static void testCustomRanges(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauges with Custom Ranges ---"));

		Shiny.theme().gaugeStyle()
				.withColor(ShinyColors.GREEN);
		Shiny.render(
				Shiny.gauge()
						.withTitle("Battery Level")
						.withValue(85)
						.withMaxValue(100)
						.build());

		Shiny.theme().gaugeStyle()
				.withColor(ShinyColors.CYAN);
		Shiny.render(
				Shiny.gauge()
						.withTitle("Progress (0-200)")
						.withValue(120)
						.withMaxValue(200)
						.build());

		Shiny.theme().gaugeStyle()
				.withColor(ShinyColors.MAGENTA);
		;
		Shiny.render(
				Shiny.gauge()
						.withTitle("Temperature (°C)")
						.withValue(25)
						.withMaxValue(40)
						.build());
		writer.println();
	}

	private static void testDifferentLengths(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauges with Different Lengths ---"));
		Shiny.theme().gaugeStyle()
				.withMaxLength(20) // Shorter bar
				.withColor(ShinyColors.BLUE);
		Shiny.render(
				Shiny.gauge()
						.withTitle("Short Gauge")
						.withValue(60)
						.build());

		Shiny.theme().gaugeStyle()
				.withMaxLength(80)
				.withColor(ShinyColors.WHITE);
		Shiny.render(
				Shiny.gauge()
						.withTitle("Long Gauge")
						.withValue(40)
						.build());
		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauge Edge Cases ---"));
		Shiny.render(
				Shiny.gauge()
						.withTitle("Zero Value")
						.withValue(0)
						.build());

		Shiny.render(
				Shiny.gauge()
						.withTitle("Max Value")
						.withValue(100)
						.build());

		Shiny.theme().gaugeStyle()
				.withColor(ShinyColors.RED);
		Shiny.render(
				Shiny.gauge()
						.withTitle("Value > Max")
						.withValue(120)
						.withMaxValue(100)
						.build());
		// Should show full bar (capped at maxValue)

		Shiny.theme().gaugeStyle()
				.withColor(ShinyColors.RED);
		Shiny.render(
				Shiny.gauge()
						.withTitle("50 %")
						.withValue(120)
						.withMaxValue(240)
						.build());
		// Should show full bar (capped at maxValue)

		Shiny.render(
				Shiny.gauge()
						.withTitle("Negative Value")
						.withValue(-10)
						.build());// Should show empty bar (capped at 0)
		writer.println();
	}
}
