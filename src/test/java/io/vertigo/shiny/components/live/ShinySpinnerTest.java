package io.vertigo.shiny.components.live;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.live.spinner.ShinySpinner;
import io.vertigo.shiny.components.live.spinner.ShinySpinnerStyle;
import io.vertigo.shiny.style.ShinyColors;

public class ShinySpinnerTest {
	public static void main(final String[] args) throws Exception {
		final ShinyWriter writer = Shiny.writer();
		testBasicSpinner(writer);
		testSpinnerWithMultipleMessages(writer);
		testShortDurationSpinner(writer);
	}

	private static void testBasicSpinner(final ShinyWriter writer) throws Exception {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Spinner ---"));
		try (ShinySpinner spinner = Shiny.spinner().start(writer)) {
			spinner.liveSend("Working...");
			Thread.sleep(3000); // Let it run for 3 seconds
		}
		writer.println("Spinner stopped.")
				.println();
	}

	private static void testSpinnerWithMultipleMessages(final ShinyWriter writer) throws Exception {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Spinner with Multiple Messages ---"));
		try (ShinySpinner spinner = Shiny.spinner().start(writer)) {
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

		try (ShinySpinner spinner = Shiny.spinner().withStyle(new ShinySpinnerStyle().withFrames("|", "/", "─", "\\")).start(writer)) {
			spinner.liveSend("Quick task...");
			Thread.sleep(1000); // Short duration
		}
		writer.println("Short duration spinner stopped.")
				.println();
	}
}