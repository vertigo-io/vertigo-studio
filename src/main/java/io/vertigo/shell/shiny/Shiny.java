package io.vertigo.shell.shiny;

import java.text.NumberFormat;

import io.vertigo.shell.ShellContext;
import io.vertigo.shell.shiny.table.ShinyTable;
import io.vertigo.shell.shiny.tree.ShinyTree;

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
