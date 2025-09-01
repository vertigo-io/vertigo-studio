package io.vertigo.shell.shiny;

import java.util.List;

import io.vertigo.shell.shiny.status.ShinyStatus.StatusShape;
import io.vertigo.shell.shiny.status.ShinyStatus.StatusType;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyStatusTest {

	public static void main(final String[] args) {
		testMatchResults();
		testServerStatus();
		testBuildStatus();
		testMixedStatusesAndShapes();
	}

	private static void testMatchResults() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Last 5 Match Results ---" + ShinyColors.RESET);
		Shiny.status()
				.title("Last 5 Matches")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.NEUTRAL, StatusType.ERROR, StatusType.SUCCESS))
				.shape(StatusShape.SQUARE)
				.print();
		System.out.println();
	}

	private static void testServerStatus() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Server Status ---" + ShinyColors.RESET);
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
		System.out.println(ShinyColors.BLUE.bright() + "--- Build Status ---" + ShinyColors.RESET);
		Shiny.status()
				.title("Build Pipeline")
				.statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO, StatusType.NEUTRAL, StatusType.SUCCESS))
				.shape(StatusShape.SQUARE)
				.print();
		System.out.println();
	}

	private static void testMixedStatusesAndShapes() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Mixed Statuses and Shapes ---" + ShinyColors.RESET);
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
