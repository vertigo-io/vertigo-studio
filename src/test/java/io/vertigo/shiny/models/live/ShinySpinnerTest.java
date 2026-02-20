package io.vertigo.shiny.models.live;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.live.spinner.ShinySpinner;
import io.vertigo.shiny.style.ShinyColors;

public class ShinySpinnerTest {
	public static void main(final String[] args) throws Exception {
		final ShinyWriter writer = ShinyRenderer.writer();
		testBasicSpinner(writer);
		testSpinnerWithMultipleMessages(writer);
		testShortDurationSpinner(writer);
	}

	private static void testBasicSpinner(final ShinyWriter writer) throws Exception {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Spinner ---"));
		try (ShinySpinner spinner = Shiny.spinner().build().start()) {
			spinner.liveSend("Working...");
			Thread.sleep(3000); // Let it run for 3 seconds
		}
		writer.println("Spinner stopped.")
				.println();
	}

	private static void testSpinnerWithMultipleMessages(final ShinyWriter writer) throws Exception {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Spinner with Multiple Messages ---"));
		try (ShinySpinner spinner = Shiny.spinner().build().start()) {
			spinner.liveSend("Loading data...");
			Thread.sleep(2000);
			spinner.liveSend("Processing files...");
			Thread.sleep(2000);
			spinner.liveSend("Saving results...");
			Thread.sleep(2000);
		}
		writer.println("Spinner with multiple messages stopped.")
				.println();
	}

	private static void testShortDurationSpinner(final ShinyWriter writer) throws Exception {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Short Duration Spinner ---"));

		ShinyRenderer.theme().spinnerStyle()
				.withFrames("|", "/", "─", "\\");
		try (ShinySpinner spinner = Shiny.spinner().build().start()) {
			spinner.liveSend("Quick task...");
			Thread.sleep(1000); // Short duration
		}
		writer.println("Short duration spinner stopped.")
				.println();
	}
}
