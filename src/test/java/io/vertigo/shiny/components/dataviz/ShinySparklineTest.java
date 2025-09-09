package io.vertigo.shiny.components.dataviz;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.sparkline.ShinySparklineStyle;
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
		Shiny.sparkline()
				.title("Temperature")
				.data(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
				.style(new ShinySparklineStyle()
						.color(ShinyColors.GREEN))
				.render(writer);
		writer.println();
	}

	private static void testStockPriceSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Stock Price Sparkline ---"));
		Shiny.sparkline()
				.title("Stock Price")
				.data(List.of(100.0, 102.0, 105.0, 103.0, 101.0, 100.0, 99.0, 100.0, 102.0, 104.0, 106.0, 105.0))
				.style(new ShinySparklineStyle()
						.color(ShinyColors.BLUE))
				.render(writer);
		writer.println();
	}

	private static void testCPULoadSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- CPU Load Sparkline ---"));
		Shiny.sparkline()
				.title("CPU Load")
				.data(List.of(0.1, 0.2, 0.5, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0))
				.style(new ShinySparklineStyle()
						.color(ShinyColors.RED))
				.render(writer);
		writer.println();
	}

	private static void testConstantValueSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Constant Value Sparkline ---"));
		Shiny.sparkline()
				.title("Constant")
				.data(List.of(5.0, 5.0, 5.0, 5.0, 5.0))
				.style(new ShinySparklineStyle()
						.color(ShinyColors.YELLOW))
				.render(writer);
		writer.println();
	}

	private static void testEmptyDataSparkline(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Empty Data Sparkline ---"));
		Shiny.sparkline()
				.title("Empty")
				.data(List.of()) // Empty list
				.style(new ShinySparklineStyle()
						.color(ShinyColors.MAGENTA))
				.render(writer);
		writer.println();
	}
}
