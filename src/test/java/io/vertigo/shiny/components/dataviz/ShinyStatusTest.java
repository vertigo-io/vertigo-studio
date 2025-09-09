package io.vertigo.shiny.components.dataviz;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusShape;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusStyle;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusType;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyStatusTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testMatchResults(writer);
		testServerStatus(writer);
		testBuildStatus(writer);
		testMixedStatusesAndShapes(writer);
	}

	private static void testMatchResults(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Last 5 Match Results ---"));
		Shiny.status()
				.title("Last 5 Matches")
				.types(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.NEUTRAL, ShinyStatusType.ERROR, ShinyStatusType.SUCCESS)
				.style(new ShinyStatusStyle()
						.shape(ShinyStatusShape.SQUARE))
				.render(writer);
		writer.println();
	}

	private static void testServerStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Server Status ---"));
		Shiny.status()
				.title("Server (success)")
				.types(ShinyStatusType.SUCCESS)
				.style(new ShinyStatusStyle()
						.shape(ShinyStatusShape.CIRCLE))
				.render(writer);
		Shiny.status()
				.title("Server (error)")
				.types(List.of(ShinyStatusType.ERROR))
				.style(new ShinyStatusStyle()
						.shape(ShinyStatusShape.CIRCLE))
				.render(writer);
		Shiny.status()
				.title("Server (success, success, error, success, success")
				.types(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.ERROR, ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS)
				.style(new ShinyStatusStyle()
						.shape(ShinyStatusShape.CIRCLE))
				.render(writer);
		writer.println();
	}

	private static void testBuildStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Build Status ---"));
		Shiny.status()
				.title("Build Pipeline")
				.types(
						ShinyStatusType.SUCCESS,
						ShinyStatusType.WARNING,
						ShinyStatusType.ERROR,
						ShinyStatusType.INFO,
						ShinyStatusType.NEUTRAL,
						ShinyStatusType.SUCCESS)
				.style(new ShinyStatusStyle()
						.shape(ShinyStatusShape.SQUARE))
				.render(writer);
		writer.println();
	}

	private static void testMixedStatusesAndShapes(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Mixed Statuses and Shapes ---"));
		Shiny.status()
				.title("Daily Report")
				.types(List.of(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.WARNING, ShinyStatusType.ERROR, ShinyStatusType.NEUTRAL))
				.render(writer);
		Shiny.status()
				.title("System Health")
				.types(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.WARNING, ShinyStatusType.ERROR)
				.style(new ShinyStatusStyle()
						.shape(ShinyStatusShape.CIRCLE))
				.render(writer);
		writer.println();
	}
}
