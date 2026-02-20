package io.vertigo.shiny.models.dataviz;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyRenderer;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.text.status.ShinyStatusType;
import io.vertigo.shiny.renderers.dataviz.ShinyStatusShape;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyStatusTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = ShinyRenderer.writer();
		testMatchResults(writer);
		testServerStatus(writer);
		testBuildStatus(writer);
		testMixedStatusesAndShapes(writer);
	}

	private static void testMatchResults(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Last 5 Match Results ---"));
		ShinyRenderer.theme().statusStyle()
				.withShape(ShinyStatusShape.SQUARE);
		ShinyRenderer.render(
				Shiny.status()
						.withTitle("Last 5 Matches")
						.addAllTypes(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.NEUTRAL, ShinyStatusType.ERROR, ShinyStatusType.SUCCESS)
						.build());
		writer.println();
	}

	private static void testServerStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Server Status ---"));

		ShinyRenderer.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		ShinyRenderer.render(
				Shiny.status()
						.withTitle("Server (success)")
						.addAllTypes(ShinyStatusType.SUCCESS)
						.build());

		ShinyRenderer.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		ShinyRenderer.render(
				Shiny.status()
						.withTitle("Server (error)")
						.addAllTypes(List.of(ShinyStatusType.ERROR))
						.build());

		ShinyRenderer.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		ShinyRenderer.render(
				Shiny.status()
						.withTitle("Server (success, success, error, success, success")
						.addAllTypes(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.ERROR, ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS)
						.build());
		writer.println();
	}

	private static void testBuildStatus(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Build Status ---"));

		ShinyRenderer.theme().statusStyle()
				.withShape(ShinyStatusShape.SQUARE);
		ShinyRenderer.render(
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

		ShinyRenderer.render(
				Shiny.status()
						.withTitle("Daily Report")
						.addAllTypes(List.of(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.WARNING, ShinyStatusType.ERROR, ShinyStatusType.NEUTRAL))
						.build());

		ShinyRenderer.theme().statusStyle()
				.withShape(ShinyStatusShape.CIRCLE);
		ShinyRenderer.render(
				Shiny.status()
						.withTitle("System Health")
						.addAllTypes(ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.SUCCESS, ShinyStatusType.WARNING, ShinyStatusType.ERROR)
						.build());
		writer.println();
	}
}
