package io.vertigo.shiny.components.dataviz;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusShape;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusType;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusStyle;
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
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.NEUTRAL, StatusType.ERROR, StatusType.SUCCESS))
				.style(new ShinyStatusStyle()
						.shape(StatusShape.SQUARE))
				.render(writer);
		writer.println();
	}

	private static void testServerStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Server Status ---"));
		Shiny.status()
				.title("Server 1")
				.statuses(List.of(StatusType.SUCCESS))
				.style(new ShinyStatusStyle()
						.shape(StatusShape.CIRCLE))
				.render(writer);
		Shiny.status()
				.title("Server 2")
				.statuses(List.of(StatusType.ERROR))
				.style(new ShinyStatusStyle()
						.shape(StatusShape.CIRCLE))
				.render(writer);
		Shiny.status()
				.title("Server 3")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.ERROR, StatusType.SUCCESS, StatusType.SUCCESS))
				.style(new ShinyStatusStyle()
						.shape(StatusShape.CIRCLE))
				.render(writer);
		writer.println();
	}

	private static void testBuildStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Build Status ---"));
		Shiny.status()
				.title("Build Pipeline")
				.statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO, StatusType.NEUTRAL, StatusType.SUCCESS))
				.style(new ShinyStatusStyle()
						.shape(StatusShape.SQUARE))
				.render(writer);
		writer.println();
	}

	private static void testMixedStatusesAndShapes(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Mixed Statuses and Shapes ---"));
		Shiny.status()
				.title("Daily Report")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.NEUTRAL))
				.render(writer);
		Shiny.status()
				.title("System Health")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR))
				.style(new ShinyStatusStyle()
						.shape(StatusShape.CIRCLE))
				.render(writer);
		writer.println();
	}
}
