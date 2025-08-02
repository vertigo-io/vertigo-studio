package io.vertigo.shell.shiny;

import java.text.NumberFormat;

import io.vertigo.shell.ShellContext;

public final class Shiny {
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(ShellContext.LOCALE);

	public static ShinyTable table() {
		return new ShinyTable(NUMBER_FORMAT);
	}

	public static ShinyBarChart barChart() {
		return new ShinyBarChart();
	}

	public static ShinyTree tree(String label) {
		return new ShinyTree(label);
	}
}
