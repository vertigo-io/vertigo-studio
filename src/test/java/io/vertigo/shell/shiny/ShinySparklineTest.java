package io.vertigo.shell.shiny;

import java.util.List;

import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinySparklineTest {

	public static void main(final String[] args) {
		test();
	}

	private static void test() {
		Shiny.sparkline()
				.title("Temperature")
				.data(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
				.color(ShinyColors.GREEN)
				.print();

		Shiny.sparkline()
				.title("Stock Price")
				.data(List.of(100.0, 102.0, 105.0, 103.0, 101.0, 100.0, 99.0, 100.0, 102.0, 104.0, 106.0, 105.0))
				.color(ShinyColors.BLUE)
				.print();

		Shiny.sparkline()
				.title("CPU Load")
				.data(List.of(0.1, 0.2, 0.5, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0))
				.color(ShinyColors.RED)
				.print();
	}
}
