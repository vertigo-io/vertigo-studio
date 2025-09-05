package io.vertigo.shiny.components.live;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.live.spinner.ShinySpinner;
import io.vertigo.shiny.style.ShinyColors;

public class ShinySpinnerTest {
	public static void main(final String[] args) throws Exception {
		testBasicSpinner();
		testSpinnerWithMultipleMessages();
		testShortDurationSpinner();
	}

	private static void testBasicSpinner() throws Exception {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Spinner ---"));
		try (ShinySpinner spinner = Shiny.spinner().start()) {
			spinner.liveSend("Working...");
			Thread.sleep(3000); // Let it run for 3 seconds
		}
		System.out.println("Spinner stopped.");
		System.out.println();
	}

	private static void testSpinnerWithMultipleMessages() throws Exception {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Spinner with Multiple Messages ---"));
		try (ShinySpinner spinner = Shiny.spinner().start()) {
			spinner.liveSend("Loading data...");
			Thread.sleep(2000);
			spinner.liveSend("Processing files...");
			Thread.sleep(2000);
			spinner.liveSend("Saving results...");
			Thread.sleep(2000);
		}
		System.out.println("Spinner with multiple messages stopped.");
		System.out.println();
	}

	private static void testShortDurationSpinner() throws Exception {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Short Duration Spinner ---"));
		try (ShinySpinner spinner = Shiny.spinner().start()) {
			spinner.liveSend("Quick task...");
			Thread.sleep(1000); // Short duration
		}
		System.out.println("Short duration spinner stopped.");
		System.out.println();
	}
}
