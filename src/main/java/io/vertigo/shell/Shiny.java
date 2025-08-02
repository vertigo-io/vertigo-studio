package io.vertigo.shell;

import java.text.NumberFormat;

import io.vertigo.shell.shiny.ShinyBarChart;
import io.vertigo.shell.shiny.ShinyTable;

public final class Shiny {
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(ShellContext.LOCALE);

	public static ShinyTable table() {
		return new ShinyTable(NUMBER_FORMAT);
	}

	public static ShinyBarChart barChart() {
		return new ShinyBarChart();
	}

}
