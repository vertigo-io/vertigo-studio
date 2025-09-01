package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.spinner.ShinySpinner;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinySpinnerTest {
	public static void main(final String[] args) throws Exception {
		testBasicSpinner();
		testSpinnerWithMultipleMessages();
		testShortDurationSpinner();
	}

	private static void testBasicSpinner() throws Exception {
		System.out.println(ShinyColors.BLUE.bright() + "--- Basic Spinner ---" + ShinyColors.RESET);
		try (ShinySpinner spinner = Shiny.spinner()) {
			spinner.send("Working...");
			Thread.sleep(3000); // Let it run for 3 seconds
		}
		System.out.println("Spinner stopped.");
		System.out.println();
	}

	private static void testSpinnerWithMultipleMessages() throws Exception {
		System.out.println(ShinyColors.BLUE.bright() + "--- Spinner with Multiple Messages ---" + ShinyColors.RESET);
		try (ShinySpinner spinner = Shiny.spinner()) {
			spinner.send("Loading data...");
			Thread.sleep(2000);
			spinner.send("Processing files...");
			Thread.sleep(2000);
			spinner.send("Saving results...");
			Thread.sleep(2000);
		}
		System.out.println("Spinner with multiple messages stopped.");
		System.out.println();
	}

	private static void testShortDurationSpinner() throws Exception {
		System.out.println(ShinyColors.BLUE.bright() + "--- Short Duration Spinner ---" + ShinyColors.RESET);
		try (ShinySpinner spinner = Shiny.spinner()) {
			spinner.send("Quick task...");
			Thread.sleep(1000); // Short duration
		}
		System.out.println("Short duration spinner stopped.");
		System.out.println();
	}
}
