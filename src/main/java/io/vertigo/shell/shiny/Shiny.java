package io.vertigo.shell.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;

import io.vertigo.shell.ShellContext;
import io.vertigo.shell.shiny.barchart.ShinyBarChart;
import io.vertigo.shell.shiny.figlet.ShinyFiglet;
import io.vertigo.shell.shiny.gauge.ShinyGauge;
import io.vertigo.shell.shiny.progressbar.ShinyProgressBar;
import io.vertigo.shell.shiny.sparkline.ShinySparkline;
import io.vertigo.shell.shiny.spinner.ShinySpinner;
import io.vertigo.shell.shiny.status.ShinyStatus;
import io.vertigo.shell.shiny.table.ShinyTable;
import io.vertigo.shell.shiny.tree.ShinyTree;

public final class Shiny {
	public final NumberFormat numberFormat = NumberFormat.getNumberInstance(ShellContext.LOCALE);

	private static final Shiny INSTANCE = new Shiny();

	//	private Terminal terminal;
	private PrintWriter writer;

	private Shiny() {
		writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
		//		try {
		//			terminal = TerminalBuilder.builder()
		//					.system(true)
		//					.build();
		//			writer = terminal.writer();
		//		} catch (final IOException e) {
		//			throw new RuntimeException("Failed to initialize terminal", e);
		//		}
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

	public static ShinySpinner spinner() {
		return new ShinySpinner(INSTANCE);
	}

	public static ShinyTree tree(final String label) {
		return new ShinyTree(INSTANCE, label);
	}

	public static ShinyGauge gauge() {
		return new ShinyGauge(INSTANCE);
	}

	public static ShinySparkline sparkline() {
		return new ShinySparkline(INSTANCE);
	}

	public static ShinyStatus status() {
		return new ShinyStatus(INSTANCE);
	}

	public static ShinyFiglet figlet() {
		return new ShinyFiglet(INSTANCE);
	}

	public NumberFormat getNumberFormat() {
		return numberFormat;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	//	public Terminal getTerminal() {
	//		return terminal;
	//	}
}
