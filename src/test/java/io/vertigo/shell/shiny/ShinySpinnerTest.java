package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.spinner.ShinySpinner;

public class ShinySpinnerTest {
	public static void main(final String[] args) throws Exception {
		test();
	}

	public static void test() throws Exception {
		try (ShinySpinner spinner = Shiny.spinner()) {
			spinner.send("Loading...");
			Thread.sleep(2000); // Let it run for 2 seconds
			spinner.send("Cleaning...");
			Thread.sleep(2000); // Run with new message
			spinner.send("Processing...");
			Thread.sleep(2000); // Run with new message
		}
		System.out.println("Spinner stopped.");
	}

}
