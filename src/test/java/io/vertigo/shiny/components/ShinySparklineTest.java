package io.vertigo.shiny.components;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.style.ShinyColors;

public class ShinySparklineTest {

	public static void main(final String[] args) {
		testTemperatureSparkline();
		testStockPriceSparkline();
		testCPULoadSparkline();
		testConstantValueSparkline();
		testEmptyDataSparkline();
	}

	private static void testTemperatureSparkline() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Temperature Sparkline ---"));
		Shiny.sparkline()
				.title("Temperature")
				.data(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
				.color(ShinyColors.GREEN)
				.print();
		System.out.println();
	}

	private static void testStockPriceSparkline() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Stock Price Sparkline ---"));
		Shiny.sparkline()
				.title("Stock Price")
				.data(List.of(100.0, 102.0, 105.0, 103.0, 101.0, 100.0, 99.0, 100.0, 102.0, 104.0, 106.0, 105.0))
				.color(ShinyColors.BLUE)
				.print();
		System.out.println();
	}

	private static void testCPULoadSparkline() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- CPU Load Sparkline ---"));
		Shiny.sparkline()
				.title("CPU Load")
				.data(List.of(0.1, 0.2, 0.5, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0))
				.color(ShinyColors.RED)
				.print();
		System.out.println();
	}

	private static void testConstantValueSparkline() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Constant Value Sparkline ---"));
		Shiny.sparkline()
				.title("Constant")
				.data(List.of(5.0, 5.0, 5.0, 5.0, 5.0))
				.color(ShinyColors.YELLOW)
				.print();
		System.out.println();
	}

	private static void testEmptyDataSparkline() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Empty Data Sparkline ---"));
		Shiny.sparkline()
				.title("Empty")
				.data(List.of()) // Empty list
				.color(ShinyColors.MAGENTA)
				.print();
		System.out.println();
	}
}
