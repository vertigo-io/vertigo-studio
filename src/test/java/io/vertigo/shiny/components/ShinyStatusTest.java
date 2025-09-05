package io.vertigo.shiny.components;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusShape;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusType;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyStatusTest {

	public static void main(final String[] args) {
		testMatchResults();
		testServerStatus();
		testBuildStatus();
		testMixedStatusesAndShapes();
	}

	private static void testMatchResults() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Last 5 Match Results ---"));
		Shiny.status()
				.title("Last 5 Matches")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.NEUTRAL, StatusType.ERROR, StatusType.SUCCESS))
				.shape(StatusShape.SQUARE)
				.print();
		System.out.println();
	}

	private static void testServerStatus() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Server Status ---"));
		Shiny.status()
				.title("Server 1")
				.statuses(List.of(StatusType.SUCCESS))
				.shape(StatusShape.CIRCLE)
				.print();
		Shiny.status()
				.title("Server 2")
				.statuses(List.of(StatusType.ERROR))
				.shape(StatusShape.CIRCLE)
				.print();
		Shiny.status()
				.title("Server 3")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.ERROR, StatusType.SUCCESS, StatusType.SUCCESS))
				.shape(StatusShape.CIRCLE)
				.print();
		System.out.println();
	}

	private static void testBuildStatus() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Build Status ---"));
		Shiny.status()
				.title("Build Pipeline")
				.statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO, StatusType.NEUTRAL, StatusType.SUCCESS))
				.shape(StatusShape.SQUARE)
				.print();
		System.out.println();
	}

	private static void testMixedStatusesAndShapes() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Mixed Statuses and Shapes ---"));
		Shiny.status()
				.title("Daily Report")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.NEUTRAL))
				.shape(StatusShape.SQUARE)
				.print();
		Shiny.status()
				.title("System Health")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR))
				.shape(StatusShape.CIRCLE)
				.print();
		System.out.println();
	}
}
