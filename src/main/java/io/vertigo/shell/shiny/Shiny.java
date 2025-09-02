package io.vertigo.shell.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Locale;

import io.vertigo.shell.ShellContext;
import io.vertigo.shell.shiny.data.calendar.ShinyCalendar;
import io.vertigo.shell.shiny.data.json.ShinyJson;
import io.vertigo.shell.shiny.data.list.ShinyList;
import io.vertigo.shell.shiny.data.table.ShinyTable;
import io.vertigo.shell.shiny.data.tree.ShinyTree;
import io.vertigo.shell.shiny.dataviz.barchart.ShinyBarChart;
import io.vertigo.shell.shiny.dataviz.gauge.ShinyGauge;
import io.vertigo.shell.shiny.dataviz.rating.ShinyRating;
import io.vertigo.shell.shiny.dataviz.sparkline.ShinySparkline;
import io.vertigo.shell.shiny.dataviz.status.ShinyStatus;
import io.vertigo.shell.shiny.input.multiselection.ShinyMultiSelection;
import io.vertigo.shell.shiny.live.progressbar.ShinyProgressBar;
import io.vertigo.shell.shiny.live.spinner.ShinySpinner;
import io.vertigo.shell.shiny.text.figlet.ShinyFiglet;
import io.vertigo.shell.shiny.text.textpath.ShinyTextPath;
import io.vertigo.shell.shiny.text.title.ShinyTitle;
import io.vertigo.shell.shiny.text.toggle.ShinyToggle;

public final class Shiny {
	//Values by default
	public final NumberFormat numberFormat = NumberFormat.getNumberInstance(ShellContext.LOCALE);
	private Locale locale = ShellContext.LOCALE;
	private PrintWriter writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);

	private static final Shiny INSTANCE = new Shiny();

	private final ShinyTheme theme = new ShinyTheme();

	public static void printWriter(PrintWriter printWriter) {
		INSTANCE.writer = printWriter;
	}

	private Shiny() {
		writer = new PrintWriter(System.out, true, StandardCharsets.UTF_8);
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

	public static ShinyCalendar calendar() {
		return new ShinyCalendar(INSTANCE);
	}

	public static ShinyTextPath textPath() {
		return new ShinyTextPath(INSTANCE);
	}

	public static ShinyJson json() {
		return new ShinyJson(INSTANCE);
	}

	public static ShinyList list() {
		return new ShinyList(INSTANCE);
	}

	public static ShinyTitle title() {
		return new ShinyTitle(INSTANCE);
	}

	public static ShinyToggle toggle() {
		return new ShinyToggle(INSTANCE);
	}

	public static ShinyRating rating() {
		return new ShinyRating(INSTANCE);
	}

	public static ShinyMultiSelection multiSelection() {
		return new ShinyMultiSelection(INSTANCE);
	}

	public NumberFormat getNumberFormat() {
		return numberFormat;
	}

	public Locale getLocale() {
		return locale;
	}

	public static ShinyTheme theme() {
		return INSTANCE.theme;
	}

	public PrintWriter getWriter() {
		return writer;
	}
}
