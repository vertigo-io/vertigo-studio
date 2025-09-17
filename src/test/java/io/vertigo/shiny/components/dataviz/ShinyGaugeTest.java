package io.vertigo.shiny.components.dataviz;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGaugeStyle;
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
		Shiny.gauge()
				.withTitle("CPU Usage")
				.withValue(50) // 50% of 100 (default max)
				.render(writer);

		Shiny.gauge()
				.withTitle("Memory Usage")
				.withValue(75)
				.withStyle(new ShinyGaugeStyle()
						.withColor(ShinyColors.YELLOW))
				.render(writer);

		Shiny.gauge()
				.withTitle("Disk Usage")
				.withValue(90)
				.withStyle(new ShinyGaugeStyle()
						.withColor(ShinyColors.RED))
				.render(writer);
		writer.println();
	}

	private static void testCustomRanges(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauges with Custom Ranges ---"));
		Shiny.gauge()
				.withTitle("Battery Level")
				.withValue(85)
				.withMax(100)
				.withStyle(new ShinyGaugeStyle()
						.withColor(ShinyColors.GREEN))
				.render(writer);

		Shiny.gauge()
				.withTitle("Progress (0-200)")
				.withValue(120)
				.withMax(200)
				.withStyle(new ShinyGaugeStyle()
						.withColor(ShinyColors.CYAN))
				.render(writer);

		Shiny.gauge()
				.withTitle("Temperature (°C)")
				.withValue(25)
				.withMax(40)
				.withStyle(new ShinyGaugeStyle()
						.withColor(ShinyColors.MAGENTA))
				.render(writer);
		writer.println();
	}

	private static void testDifferentLengths(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauges with Different Lengths ---"));
		Shiny.gauge()
				.withTitle("Short Gauge")
				.withValue(60)
				.withStyle(new ShinyGaugeStyle()
						.withMaxLength(20) // Shorter bar
						.withColor(ShinyColors.BLUE))
				.render(writer);

		Shiny.gauge()
				.withTitle("Long Gauge")
				.withValue(40)
				.withStyle(new ShinyGaugeStyle()
						.withMaxLength(80)
						.withColor(ShinyColors.WHITE))
				.render(writer);
		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauge Edge Cases ---"));
		Shiny.gauge()
				.withTitle("Zero Value")
				.withValue(0)
				.render(writer); // Should show empty bar

		Shiny.gauge()
				.withTitle("Max Value")
				.withValue(100)
				.render(writer); // Should show full bar

		Shiny.gauge()
				.withTitle("Value > Max")
				.withValue(120)
				.withMax(100)
				.withStyle(new ShinyGaugeStyle()
						.withColor(ShinyColors.RED))
				.render(writer); // Should show full bar (capped at max)

		Shiny.gauge()
				.withTitle("50 %")
				.withValue(120)
				.withMax(240)
				.withStyle(new ShinyGaugeStyle()
						.withColor(ShinyColors.RED))
				.render(writer); // Should show full bar (capped at max)

		Shiny.gauge()
				.withTitle("Negative Value")
				.withValue(-10)
				.render(writer); // Should show empty bar (capped at 0)
		writer.println();
	}
}