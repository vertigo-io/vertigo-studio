package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.color.ShinyColors;

public class ShinyGaugeTest {

	public static void main(final String[] args) {
		testBasicGauges();
		testCustomRanges();
		testDifferentLengths();
		testEdgeCases();
	}

	private static void testBasicGauges() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Basic Gauges ---" + ShinyColors.RESET);
		Shiny.gauge()
				.title("CPU Usage")
				.value(50) // 50% of 100 (default max)
				.print();

		Shiny.gauge()
				.title("Memory Usage")
				.value(75)
				.color(ShinyColors.YELLOW)
				.print();

		Shiny.gauge()
				.title("Disk Usage")
				.value(90)
				.color(ShinyColors.RED)
				.print();
		System.out.println();
	}

	private static void testCustomRanges() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Gauges with Custom Ranges ---" + ShinyColors.RESET);
		Shiny.gauge()
				.title("Battery Level")
				.value(85)
				.max(100)
				.color(ShinyColors.GREEN)
				.print();

		Shiny.gauge()
				.title("Progress (0-200)")
				.value(120)
				.max(200)
				.color(ShinyColors.CYAN)
				.print();

		Shiny.gauge()
				.title("Temperature (°C)")
				.value(25)
				.max(40)
				.color(ShinyColors.MAGENTA)
				.print();
		System.out.println();
	}

	private static void testDifferentLengths() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Gauges with Different Lengths ---" + ShinyColors.RESET);
		Shiny.gauge()
				.title("Short Gauge")
				.value(60)
				.length(20) // Shorter bar
				.color(ShinyColors.BLUE)
				.print();

		Shiny.gauge()
				.title("Long Gauge")
				.value(40)
				.length(80) // Longer bar
				.color(ShinyColors.WHITE)
				.print();
		System.out.println();
	}

	private static void testEdgeCases() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Gauge Edge Cases ---" + ShinyColors.RESET);
		Shiny.gauge()
				.title("Zero Value")
				.value(0)
				.print(); // Should show empty bar

		Shiny.gauge()
				.title("Max Value")
				.value(100)
				.print(); // Should show full bar

		Shiny.gauge()
				.title("Value > Max")
				.value(120)
				.max(100)
				.color(ShinyColors.RED)
				.print(); // Should show full bar (capped at max)

		Shiny.gauge()
				.title("Negative Value")
				.value(-10)
				.print(); // Should show empty bar (capped at 0)
		System.out.println();
	}
}
