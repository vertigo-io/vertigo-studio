package io.vertigo.shiny;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import io.vertigo.shiny.data.calendar.ShinyCalendar;
import io.vertigo.shiny.data.json.ShinyJson;
import io.vertigo.shiny.data.list.ShinyList;
import io.vertigo.shiny.data.table.ShinyTable;
import io.vertigo.shiny.data.tree.ShinyTree;
import io.vertigo.shiny.dataviz.barchart.ShinyBarChart;
import io.vertigo.shiny.dataviz.gauge.ShinyGauge;
import io.vertigo.shiny.dataviz.rating.ShinyRating;
import io.vertigo.shiny.dataviz.sparkline.ShinySparkline;
import io.vertigo.shiny.dataviz.status.ShinyStatus;
import io.vertigo.shiny.input.multiselection.ShinyMultiSelection;
import io.vertigo.shiny.input.text.ShinyInputText;
import io.vertigo.shiny.live.progressbar.ShinyProgressBar;
import io.vertigo.shiny.live.spinner.ShinySpinner;
import io.vertigo.shiny.mermaid.ShinyMermaidServer;
import io.vertigo.shiny.text.figlet.ShinyFiglet;
import io.vertigo.shiny.text.textpath.ShinyTextPath;
import io.vertigo.shiny.text.title.ShinyTitle;
import io.vertigo.shiny.text.toggle.ShinyToggle;

public final class Shiny {
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

	public static ShinyInputText inputText() {
		return new ShinyInputText(INSTANCE);
	}

	public static ShinyTheme theme() {
		return INSTANCE.theme;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	//-----
	public static ShinyMermaidServer mermaid() {
		return new ShinyMermaidServer(INSTANCE, 5656);
	}
}
