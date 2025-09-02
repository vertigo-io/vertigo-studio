package io.vertigo.shell.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Locale;

import io.vertigo.shell.ShellContext;
import io.vertigo.shell.shiny.barchart.ShinyBarChart;
import io.vertigo.shell.shiny.calendar.ShinyCalendar;
import io.vertigo.shell.shiny.figlet.ShinyFiglet;
import io.vertigo.shell.shiny.gauge.ShinyGauge;
import io.vertigo.shell.shiny.json.ShinyJson;
import io.vertigo.shell.shiny.list.ShinyList;
import io.vertigo.shell.shiny.progressbar.ShinyProgressBar;
import io.vertigo.shell.shiny.rating.ShinyRating;
import io.vertigo.shell.shiny.sparkline.ShinySparkline;
import io.vertigo.shell.shiny.spinner.ShinySpinner;
import io.vertigo.shell.shiny.status.ShinyStatus;
import io.vertigo.shell.shiny.table.ShinyTable;
import io.vertigo.shell.shiny.textpath.ShinyTextPath;
import io.vertigo.shell.shiny.title.ShinyTitle;
import io.vertigo.shell.shiny.toggle.ShinyToggle;
import io.vertigo.shell.shiny.tree.ShinyTree;
import io.vertigo.shell.shiny.multiselection.ShinyMultiSelection;

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
