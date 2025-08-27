package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyGaugeTest {

	public static void main(final String[] args) {
		test();
	}

	private static void test() {
		Shiny.gauge()
				.title("CPU Usage")
				.value(50)
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

		Shiny.gauge()
				.title("Download")
				.value(25)
				.length(20)
				.color(ShinyColors.BLUE)
				.print();
	}
}
