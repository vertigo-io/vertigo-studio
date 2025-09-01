package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.progressbar.ShinyProgressBar;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyProgressBarTest {
	public static void main(final String[] args) {
		testBasicProgressBar();
		testFastProgressBar();
		testSlowProgressBar();
		testMultipleProgressBars();
	}

	private static void testBasicProgressBar() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Basic Progress Bar ---" + ShinyColors.RESET);
		final ShinyProgressBar progressBar = Shiny.progressBar().total(100);
		System.out.println(); // New line to ensure progress bar starts on a clean line
		for (int i = 0; i < 100; i++) {
			progressBar.setProgress(i + 1);
			progressBar.print();
			try {
				Thread.sleep(30); // Simulate work
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		progressBar.finish();
		System.out.println();
	}

	private static void testFastProgressBar() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Fast Progress Bar ---" + ShinyColors.RESET);
		final ShinyProgressBar progressBar = Shiny.progressBar().total(500);
		System.out.println();
		for (int i = 0; i < 500; i++) {
			progressBar.setProgress(i + 1);
			progressBar.print();
			try {
				Thread.sleep(5); // Faster simulation
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		progressBar.finish();
		System.out.println();
	}

	private static void testSlowProgressBar() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Slow Progress Bar ---" + ShinyColors.RESET);
		final ShinyProgressBar progressBar = Shiny.progressBar().total(50);
		System.out.println();
		for (int i = 0; i < 50; i++) {
			progressBar.setProgress(i + 1);
			progressBar.print();
			try {
				Thread.sleep(100); // Slower simulation
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		progressBar.finish();
		System.out.println();
	}

	private static void testMultipleProgressBars() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Multiple Progress Bars (Sequential) ---" + ShinyColors.RESET);
		for (int j = 0; j < 3; j++) {
			System.out.println("Task " + (j + 1) + ":");
			final ShinyProgressBar progressBar = Shiny.progressBar().total(100);
			System.out.println();
			for (int i = 0; i < 100; i++) {
				progressBar.setProgress(i + 1);
				progressBar.print();
				try {
					Thread.sleep(20);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			progressBar.finish();
			System.out.println();
		}
	}
}
