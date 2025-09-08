package io.vertigo.shiny.components.dataviz;

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
		Shiny.gauge()
				.title("CPU Usage")
				.value(50) // 50% of 100 (default max)
				.render(writer);

		Shiny.gauge()
				.title("Memory Usage")
				.value(75)
				.color(ShinyColors.YELLOW)
				.render(writer);

		Shiny.gauge()
				.title("Disk Usage")
				.value(90)
				.color(ShinyColors.RED)
				.render(writer);
		writer.println();
	}

	private static void testCustomRanges(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauges with Custom Ranges ---"));
		Shiny.gauge()
				.title("Battery Level")
				.value(85)
				.max(100)
				.color(ShinyColors.GREEN)
				.render(writer);

		Shiny.gauge()
				.title("Progress (0-200)")
				.value(120)
				.max(200)
				.color(ShinyColors.CYAN)
				.render(writer);

		Shiny.gauge()
				.title("Temperature (°C)")
				.value(25)
				.max(40)
				.color(ShinyColors.MAGENTA)
				.render(writer);
		writer.println();
	}

	private static void testDifferentLengths(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauges with Different Lengths ---"));
		Shiny.gauge()
				.title("Short Gauge")
				.value(60)
				.length(20) // Shorter bar
				.color(ShinyColors.BLUE)
				.render(writer);

		Shiny.gauge()
				.title("Long Gauge")
				.value(40)
				.length(80) // Longer bar
				.color(ShinyColors.WHITE)
				.render(writer);
		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Gauge Edge Cases ---"));
		Shiny.gauge()
				.title("Zero Value")
				.value(0)
				.render(writer); // Should show empty bar

		Shiny.gauge()
				.title("Max Value")
				.value(100)
				.render(writer); // Should show full bar

		Shiny.gauge()
				.title("Value > Max")
				.value(120)
				.max(100)
				.color(ShinyColors.RED)
				.render(writer); // Should show full bar (capped at max)

		Shiny.gauge()
				.title("Negative Value")
				.value(-10)
				.render(writer); // Should show empty bar (capped at 0)
		writer.println();
	}
}
