package io.vertigo.shiny.models.dataviz;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.style.ShinyColors;

public class ShinySparklineTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testTemperatureSparkline(writer);
		testStockPriceSparkline(writer);
		testCPULoadSparkline(writer);
		testConstantValueSparkline(writer);
		testEmptyDataSparkline(writer);
	}

	private static void testTemperatureSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Temperature Sparkline ---"));

		Shiny.theme().sparklineStyle()
				.withColor(ShinyColors.GREEN);
		Shiny.render(
				Shiny.sparkline()
						.withTitle("Temperature")
						.withValues(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
						.build());
		writer.println();
	}

	private static void testStockPriceSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Stock Price Sparkline ---"));
		Shiny.theme().sparklineStyle()
				.withColor(ShinyColors.BLUE);
		Shiny.render(
				Shiny.sparkline()
						.withTitle("Stock Price")
						.withValues(List.of(100.0, 102.0, 105.0, 103.0, 101.0, 100.0, 99.0, 100.0, 102.0, 104.0, 106.0, 105.0))
						.build());
		writer.println();
	}

	private static void testCPULoadSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- CPU Load Sparkline ---"));
		Shiny.theme().sparklineStyle()
				.withColor(ShinyColors.RED);
		Shiny.render(
				Shiny.sparkline()
						.withTitle("CPU Load")
						.withValues(List.of(0.1, 0.2, 0.5, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0))
						.build());
		writer.println();
	}

	private static void testConstantValueSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Constant Value Sparkline ---"));
		Shiny.theme().sparklineStyle()
				.withColor(ShinyColors.YELLOW);
		Shiny.render(
				Shiny.sparkline()
						.withTitle("Constant")
						.withValues(List.of(5.0, 5.0, 5.0, 5.0, 5.0))
						.build());
		writer.println();
	}

	private static void testEmptyDataSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Empty Data Sparkline ---"));
		Shiny.theme().sparklineStyle()
				.withColor(ShinyColors.MAGENTA);
		Shiny.render(
				Shiny.sparkline()
						.withTitle("Empty")
						.withValues(List.of()) // Empty list
						.build());
		writer.println();
	}
}
