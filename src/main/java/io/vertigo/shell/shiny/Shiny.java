package io.vertigo.shell.shiny;

import java.text.NumberFormat;

import io.vertigo.shell.ShellContext;
import io.vertigo.shell.shiny.barchart.ShinyBarChart;
import io.vertigo.shell.shiny.progressbar.ShinyProgressBar;
import io.vertigo.shell.shiny.table.ShinyTable;
import io.vertigo.shell.shiny.tree.ShinyTree;

public final class Shiny {
	public final NumberFormat numberFormat = NumberFormat.getNumberInstance(ShellContext.LOCALE);

	private static final Shiny INSTANCE = new Shiny();

	private Shiny() {
		//To avoid
	}

	public static ShinyTable table() {
		return new ShinyTable(INSTANCE);
	}

	public static ShinyBarChart barChart() {
		return new ShinyBarChart(INSTANCE);
	}

	public static ShinyProgressBar progressBar() {
		return new ShinyProgressBar(INSTANCE);
	}

	public static ShinyTree tree(String label) {
		return new ShinyTree(label);
	}

	public NumberFormat getNumberFormat() {
		return numberFormat;
	}
}
