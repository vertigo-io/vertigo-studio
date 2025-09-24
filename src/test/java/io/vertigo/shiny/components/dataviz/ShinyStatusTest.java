package io.vertigo.shiny.components.dataviz;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusType;
import io.vertigo.shiny.renderers.dataviz.ShinyStatusShape;
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
		Shiny.theme().statusStyle()
				.withShape(ShinyStatusShape.SQUARE);
		Shiny.render(
				Shiny.status()
						.withTitle("Last 5 Matches")
						.addAllTypes(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.NEUTRAL, ShinyStatusType.ERROR, ShinyStatusType.SUCCESS)
						.build());
		writer.println();
	}

	private static void testServerStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Server Status ---"));

		Shiny.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		Shiny.render(
				Shiny.status()
						.withTitle("Server (success)")
						.addAllTypes(ShinyStatusType.SUCCESS)
						.build());

		Shiny.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		Shiny.render(
				Shiny.status()
						.withTitle("Server (error)")
						.addAllTypes(List.of(ShinyStatusType.ERROR))
						.build());

		Shiny.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		Shiny.render(
				Shiny.status()
						.withTitle("Server (success, success, error, success, success")
						.addAllTypes(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.ERROR, ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS)
						.build());
		writer.println();
	}

	private static void testBuildStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Build Status ---"));

		Shiny.theme().statusStyle()
				.withShape(ShinyStatusShape.SQUARE);
		Shiny.render(
				Shiny.status()
						.withTitle("Build Pipeline")
						.addAllTypes(
								ShinyStatusType.SUCCESS,
								ShinyStatusType.WARNING,
								ShinyStatusType.ERROR,
								ShinyStatusType.INFO,
								ShinyStatusType.NEUTRAL,
								ShinyStatusType.SUCCESS)
						.build());
		writer.println();
	}

	private static void testMixedStatusesAndShapes(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Mixed Statuses and Shapes ---"));

		Shiny.render(
				Shiny.status()
						.withTitle("Daily Report")
						.addAllTypes(List.of(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.WARNING, ShinyStatusType.ERROR, ShinyStatusType.NEUTRAL))
						.build());

		Shiny.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		Shiny.render(
				Shiny.status()
						.withTitle("System Health")
						.addAllTypes(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.WARNING, ShinyStatusType.ERROR)
						.build());
		writer.println();
	}
}
