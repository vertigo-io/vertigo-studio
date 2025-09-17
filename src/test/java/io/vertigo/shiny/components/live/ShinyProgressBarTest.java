package io.vertigo.shiny.components.live;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.live.progressbar.ShinyProgressBar;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyProgressBarTest {
	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicProgressBar(writer);
		testFastProgressBar(writer);
		testSlowProgressBar(writer);
		testMultipleProgressBars(writer);
	}

	private static void wait(final int duration) {
		try {
			Thread.sleep(duration);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

	private static void testBasicProgressBar(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Progress Bar ---"));

		try (final ShinyProgressBar progressBar = Shiny.progressBar()
				.withTotal(100)
				.build()
				.start(writer)) {
			for (int i = 0; i < 100; i++) {
				progressBar.liveUpdate(i + 1);
				wait(30); // Simulate work
			}
		}
		writer.println();
	}

	private static void testFastProgressBar(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Fast Progress Bar ---"));
		try (final ShinyProgressBar progressBar = Shiny.progressBar().withTotal(500).build().start(writer)) {
			for (int i = 0; i < 500; i++) {
				progressBar.liveUpdate(i + 1);
				wait(5);// Faster simulation
			}
		}
		writer.println();
	}

	private static void testSlowProgressBar(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Slow Progress Bar ---"));
		try (final ShinyProgressBar progressBar = Shiny.progressBar().withTotal(50).build().start(writer)) {
			for (int i = 0; i < 50; i++) {
				progressBar.liveUpdate(i + 1);
				wait(100);// Slower simulation
			}
		}
		writer.println();
	}

	private static void testMultipleProgressBars(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Multiple Progress Bars (Sequential) ---"));
		for (int j = 0; j < 3; j++) {
			writer.println("Task " + (j + 1) + ":");

			try (final ShinyProgressBar progressBar = Shiny.progressBar().withTotal(100).build().start(writer)) {
				writer.println();
				for (int i = 0; i < 100; i++) {
					progressBar.liveUpdate(i + 1);
					wait(20);
				}
			}
			writer.println();
		}
	}
}
