package io.vertigo.shell.shiny;

import java.util.List;

import io.vertigo.shell.shiny.status.ShinyStatus.StatusShape;
import io.vertigo.shell.shiny.status.ShinyStatus.StatusType;

public class ShinyStatusTest {

	public static void main(final String[] args) {
		test();
	}

	private static void test() {
		Shiny.status()
				.title("Last 5 Matches")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.NEUTRAL, StatusType.ERROR, StatusType.SUCCESS))
				.shape(StatusShape.SQUARE)
				.print();

		Shiny.status()
				.title("Server Status")
				.statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.ERROR, StatusType.SUCCESS, StatusType.SUCCESS))
				.shape(StatusShape.CIRCLE)
				.print();

		Shiny.status()
				.title("Build Status")
				.statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO))
				.shape(StatusShape.SQUARE)
				.print();
	}
}
