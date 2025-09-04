package io.vertigo.shiny;

import io.vertigo.shiny.color.ShinyColors;
import io.vertigo.shiny.live.progressbar.ShinyProgressBar;

public class ShinyProgressBarTest {
	public static void main(final String[] args) {
		testBasicProgressBar();
		testFastProgressBar();
		testSlowProgressBar();
		testMultipleProgressBars();
	}

	private static void wait(final int duration) {
		try {
			Thread.sleep(duration);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	private static void testBasicProgressBar() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Basic Progress Bar ---" + ShinyColors.RESET);

		try (final ShinyProgressBar progressBar = Shiny.progressBar().total(100).start()) {
			for (int i = 0; i < 100; i++) {
				progressBar.liveUpdate(i + 1);
				wait(30); // Simulate work
			}
		}
		System.out.println();
	}

	private static void testFastProgressBar() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Fast Progress Bar ---" + ShinyColors.RESET);
		try (final ShinyProgressBar progressBar = Shiny.progressBar().total(500).start()) {
			for (int i = 0; i < 500; i++) {
				progressBar.liveUpdate(i + 1);
				wait(5);// Faster simulation
			}
		}
		System.out.println();
	}

	private static void testSlowProgressBar() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Slow Progress Bar ---" + ShinyColors.RESET);
		try (final ShinyProgressBar progressBar = Shiny.progressBar().total(50).start()) {
			for (int i = 0; i < 50; i++) {
				progressBar.liveUpdate(i + 1);
				wait(100);// Slower simulation
			}
		}
		System.out.println();
	}

	private static void testMultipleProgressBars() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Multiple Progress Bars (Sequential) ---" + ShinyColors.RESET);
		for (int j = 0; j < 3; j++) {
			System.out.println("Task " + (j + 1) + ":");
			try (final ShinyProgressBar progressBar = Shiny.progressBar().total(100).start()) {
				System.out.println();
				for (int i = 0; i < 100; i++) {
					progressBar.liveUpdate(i + 1);
					wait(20);
				}
			}
			System.out.println();
		}
	}
}
